/*
 * MIT License
 *
 * Copyright (c) 2022 Surati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.module.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.module.api.Profile;
import io.surati.gap.admin.module.api.Profiles;
import io.surati.gap.admin.module.exceptions.DatabaseException;
import org.apache.commons.lang3.StringUtils;
import org.cactoos.text.Joined;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Profiles in Database.
 * 
 * @since 3.0
 */
public final class DbProfiles implements Profiles {

	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbProfiles(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Profile get(Long id) {
		if(this.has(id)) {
			return new DbProfile(this.source, id);
		} else {
			throw new IllegalArgumentException(String.format("Le profil avec ID %s n'a pas été trouvé !", id));
		}
	}

	private boolean has(final Long id) {
		try {
			return new JdbcSession(this.source)
				.sql("SELECT COUNT(*) FROM profile WHERE id=?")
				.set(id)
				.select(new SingleOutcome<>(Long.class)) > 0;
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}
	
	private boolean has(final String name) {
		try {
			return new JdbcSession(this.source)
				.sql("SELECT COUNT(*) FROM profile WHERE name=?")
				.set(name)
				.select(new SingleOutcome<>(Long.class)) > 0;
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}
	
	@Override
	public Profile add(String name) {		
		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Le nom ne peut être vide !");
		}
		if(this.has(name)) {
			throw new IllegalArgumentException("Le nom est déjà utilisé !");
		}
		try {
			return new DbProfile(
				this.source,
				new JdbcSession(this.source)
					.sql(
						new Joined(
							" ",
                            "INSERT INTO profile",
                            "(name)",
                            "VALUES",
                            "(?)"
						).toString()
					)
					.set(name)
					.insert(Outcome.LAST_INSERT_ID)
			);
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public Iterable<Profile> iterate() {		
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT id FROM profile")
		){
			final Collection<Profile> items = new ArrayList<>();
			
			try(final ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					items.add(new DbProfile(source, rs.getLong(1)));
				}
			}
			
			return items;
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
		                    "SELECT COUNT(*) FROM profile",
		                    "WHERE id=? AND id IN (SELECT profile_id FROM app_user)"
		                ).toString()
		            )
		            .set(id)
		            .select(new SingleOutcome<>(Long.class)) > 0;
		        if(used) {
		        	throw new IllegalArgumentException("Le profil ne peut pas être supprimé (déjà utilisé) !");
		        }
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("DELETE FROM profile WHERE id=?");
		) {
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
