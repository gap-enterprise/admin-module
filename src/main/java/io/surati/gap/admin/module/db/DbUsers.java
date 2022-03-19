/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
	
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.module.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.module.api.User;
import io.surati.gap.admin.module.api.Users;
import io.surati.gap.admin.module.exceptions.DatabaseException;
import io.surati.gap.admin.module.secure.EncryptedWordImpl;
import io.surati.gap.admin.module.secure.GeneratedSalt;
import io.surati.gap.admin.module.secure.Salt;
import org.apache.commons.lang3.StringUtils;
import org.cactoos.text.Joined;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Users in Database.
 * 
 * @since 3.0
 */
public final class DbUsers implements Users {

	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbUsers(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public boolean authenticate(String login, String password) {
		if(this.has(login)) {
			final User user = this.get(login);
			final String userpwd = user.password();
			final String encryptpwd = new EncryptedWordImpl(password, user.salt()).value();
			if(userpwd.equals(encryptpwd)) {
				if(user.blocked()) {
					throw new IllegalArgumentException("Vous avez été bloqué ! Veuillez SVP contacter l'administrateur.");
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean authenticatePwdEncrypted(String login, String password) {
		if(this.has(login)) {
			final User user = this.get(login);
			if(user.password().equals(password)) {
				if(user.blocked()) {
					throw new IllegalArgumentException("Vous avez été bloqué ! Veuillez SVP contacter l'administrateur.");
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public User get(String login) {
		
		try(
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT id FROM app_user WHERE login=?");
		){
			pstmt.setString(1, login);
		
			try(final ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Long id = rs.getLong(1);
					return new DbUser(source, id);
				} else {
					throw new IllegalArgumentException(String.format("User with login %s not found !", login));
				}			
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public User get(Long id) {
		try(
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM app_user WHERE id=?");
		){
			pstmt.setLong(1, id);
		
			try(final ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return new DbUser(source, id);
				} else {
					throw new IllegalArgumentException(String.format("User with ID %s not found !", id));
				}			
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public User register(String name, String login, String password) {
		
		if(StringUtils.isBlank(name)) 
			throw new IllegalArgumentException("Le nom  ne peut être vide !");
		
		if(StringUtils.isBlank(login))
			throw new IllegalArgumentException("Le login ne peut être vide !");
		
		if(StringUtils.isBlank(password))
			throw new IllegalArgumentException("Le mot de passe ne peut être vide !");
		
		if(this.has(login))
			throw new IllegalArgumentException(String.format("Ce login %s est déjà utilisé !", login));
		
		try(
				final Connection connection = this.source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("INSERT INTO person (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
				final PreparedStatement pstmt1 = connection.prepareStatement("INSERT INTO app_user (id, login, password, salt) VALUES (?, ?, ?, ?)")
		){			
			pstmt.setString(1, name);			
			pstmt.executeUpdate();		
			final Long id;
			try (final ResultSet rs = pstmt.getGeneratedKeys()) {
				rs.next();
				id = rs.getLong(1);
			}		
			pstmt1.setLong(1, id);
			pstmt1.setString(2, login);
			final Salt salt = new GeneratedSalt();
			pstmt1.setString(3, new EncryptedWordImpl(password, salt).value());
			pstmt1.setString(4, salt.value());
			pstmt1.executeUpdate();
			return new DbUser(this.source, id);
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public Iterable<User> iterate() {
		
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT id FROM app_user")
		){
			final Collection<User> items = new ArrayList<>();
			
			try(final ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					items.add(new DbUser(source, rs.getLong(1)));
				}
			}
			
			return items;
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public boolean has(String login) {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) as nb FROM app_user WHERE login=?")
		){
			pstmt.setString(1, login);
		
			try(final ResultSet rs = pstmt.executeQuery()){
				rs.next();
				Long nb = rs.getLong(1);
				return nb > 0;
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public void remove(final Long id) {
		try {
			final boolean used = new JdbcSession(this.source)
	            .sql(
	                new Joined(
	                    " ",
	                    "SELECT COUNT(*) FROM app_user",
	                    "WHERE id=? AND (login IN (SELECT author FROM event_log)",
	                    "OR id IN (SELECT author_id FROM payment_order)",
	                    "OR id IN (SELECT author_id FROM payment_order_group)",
	                    "OR id IN (SELECT issuer_id FROM bank_note)",
	                    "OR id IN (SELECT author_id FROM reference_document))"
	                ).toString()
	            )
	            .set(id)
	            .select(new SingleOutcome<>(Long.class)) > 0;
	        if(used) {
	        	throw new IllegalArgumentException("L'utilisateur ne peut pas être supprimé (déjà utilisé) !");
	        }
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("DELETE FROM app_user WHERE id=?");
			final PreparedStatement pstmt1 = connection.prepareStatement("DELETE FROM person WHERE id=?");
		) {
			pstmt.setLong(1, id);
			pstmt1.setLong(1, id);
			pstmt.executeUpdate();
			pstmt1.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public Long count() {
		try {
			return
				new JdbcSession(this.source)
					.sql("SELECT COUNT(*) FROM app_user")
					.select(new SingleOutcome<>(Long.class));
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

}
