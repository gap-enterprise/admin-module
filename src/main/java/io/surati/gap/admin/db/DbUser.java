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
package io.surati.gap.admin.db;

import com.google.common.base.Objects;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.api.Profile;
import io.surati.gap.admin.api.User;
import io.surati.gap.admin.exceptions.DatabaseException;
import io.surati.gap.admin.secure.ConstantSalt;
import io.surati.gap.admin.secure.EncryptedWordImpl;
import io.surati.gap.admin.secure.GeneratedSalt;
import io.surati.gap.admin.secure.Salt;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.cactoos.text.Joined;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * User in Database.
 * 
 * @since 0.1
 */
public final class DbUser extends DbAbstractPerson implements User {
	
	private static String AVATAR_DEFAULT_LOCATION = "images/avatar.jpg";
	private static String AVATAR_FOLDER = "images";

	static {
		File folder = new File(AVATAR_FOLDER);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		File file = new File(AVATAR_DEFAULT_LOCATION);
		if(!file.exists()) {
			try (
				InputStream in = User.class
					.getClassLoader()
					.getResourceAsStream("io/surati/gap/admin/img/avatar.jpg")
			) {
				Files.copy(in, Paths.get(AVATAR_DEFAULT_LOCATION));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbUser(final DataSource source, final Long id) {
		super(source, id);
	}
	
	/**
	 * Checks if an User with login and not id exists
	 * @param login Login
	 * @return boolean exits
	 */
	private boolean loginIsUsed(String login) {
		try (
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) as nb FROM app_user WHERE login=? AND id <>?")
			){
				pstmt.setString(1, login);
				pstmt.setLong(2, id);
			
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
	public String login() {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT login FROM app_user WHERE id=?")
		){
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public String password() {
		try (
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("SELECT password FROM app_user WHERE id=?")
			){
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public void forceChangePassword(final String newpwd) {
		if(StringUtils.isBlank(newpwd)) {
			throw new IllegalArgumentException("New password must be given !");
		}
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE app_user SET password=?, salt=? WHERE id=?")
		) {
			final Salt salt = new GeneratedSalt();
			pstmt.setString(1, new EncryptedWordImpl(newpwd, salt).value());
			pstmt.setString(2, salt.value());
			pstmt.setLong(3, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public void changePassword(final String oldpassword, final String password) {		 
		if(StringUtils.isBlank(oldpassword)) {
			throw new IllegalArgumentException("L'ancien mot de passe est obligatoire !");
		}		
		if(password.equals(oldpassword)) {
			throw new IllegalArgumentException("Le nouveau mot de passe doit être différent de l'ancien !");
		}
		if(
			!this.password().equals(
				new EncryptedWordImpl(oldpassword, this.salt()).value()
			)
		) {
			throw new IllegalArgumentException("L'ancien mot de passe ne correspond pas !");
		}
		this.forceChangePassword(password);		
	}
	
	@Override
	public boolean blocked() {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT blocked FROM app_user WHERE id=?")
		){
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getBoolean(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public void block(boolean enable) {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE app_user SET blocked=? WHERE id=?")
		) {
			pstmt.setBoolean(1, enable);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public void update(final String login, final String name) {
		
		if(StringUtils.isBlank(login))
			throw new IllegalArgumentException("Le login doit être renseigné !");
		
		if(StringUtils.isBlank(name))
			throw new IllegalArgumentException("Le nom doit être renseigné !");
		
		if(this.loginIsUsed(login))
			throw new IllegalArgumentException("Ce login est déjà utilisé.");
			
		try (
			final Connection connection = source.getConnection();			
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE app_user SET login=? WHERE id=?")
		) {
			super.update(name);
			pstmt.setString(1, login);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public Profile profile() {
		try(
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("SELECT profile_id FROM app_user WHERE id=?");
			){
				pstmt.setLong(1, id);
			
				try(final ResultSet rs = pstmt.executeQuery()){
					if(rs.next()) {
						Long id = rs.getLong(1);
						return new DbProfile(source, id);
					} else {
						throw new IllegalArgumentException(String.format("L'utilisateur avec l'ID %s n'existe pas !", id));
					}			
				}
			} catch(SQLException e) {
				throw new DatabaseException(e);
			}
	}

	@Override
	public void assign(Profile profile) {		
		try (
			final Connection connection = source.getConnection();
				
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE app_user SET profile_id=? WHERE id=?")
		) {
			pstmt.setLong(1, profile.id());
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public Salt salt() {
		try {
			return new ConstantSalt(
				new JdbcSession(this.source)
					.sql(
		        		new Joined(
	        				" ",
	        				"SELECT salt FROM app_user",
	        				"WHERE id=?"
	        			).toString()
	        		)
					.set(this.id)
		            .select(new SingleOutcome<>(String.class)));
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public void changePhoto(InputStream content, String ext) throws IOException {
		Files.walk(Paths.get(AVATAR_FOLDER))
			.filter(
				f -> f.getFileName().toString().startsWith(String.format("avatar_%s_", this.id))
			).forEach(
				path -> {
					try {
						Files.delete(path);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
			);
		final String filenametosave = String.format("%s/avatar_%s_%s.%s", AVATAR_FOLDER, this.id, UUID.randomUUID(), ext);
		final Path path = new File(filenametosave).toPath();
        Files.copy(content, path);
	}

	@Override
	public InputStream photo() throws IOException {
		final String photo = this.photoLocation();
		return new FileInputStream(
			new File(photo)
		);
	}

	@Override
	public String photoBase64() {
		try {
			final String photo = this.photoLocation();
			final String ext = FilenameUtils.getExtension(photo);
			final InputStream in = this.photo();
			byte[] bytes = IOUtils.toByteArray(in);
			return String.format("data:image/%s;base64,%s", ext, Base64.encodeBase64String(bytes));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String photoLocation() {
		final String imageFile;
		try (Stream<Path> files = Files.walk(Paths.get(AVATAR_FOLDER))) {
	        final Optional<String> file =  files.filter(f -> f.getFileName().toString().startsWith(String.format("avatar_%s", this.id)))
                .map(f -> f.getFileName().toString())
                .findFirst();
	        if(file.isPresent()) {
	        	imageFile = file.get();
	        }
	        else {
	        	imageFile = "avatar.jpg"; 
	        }
	        return String.format("%s/%s", AVATAR_FOLDER, imageFile);
	    } catch (IOException ex) {
	    	throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof User)) {
			return false;
		}
		final User that = (User)obj;
		return Objects.equal(this.id, that.id());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
}
 