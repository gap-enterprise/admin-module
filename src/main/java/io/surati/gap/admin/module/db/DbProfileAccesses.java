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
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.module.api.Access;
import io.surati.gap.admin.module.api.Profile;
import io.surati.gap.admin.module.api.ProfileAccesses;
import io.surati.gap.admin.module.exceptions.DatabaseException;
import org.cactoos.text.Joined;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * All profile accesses from Database.
 *
 * @since 3.0
 */
public final class DbProfileAccesses implements ProfileAccesses {

	/**
	 * DataSource.
	 */
	private final DataSource source;

	/**
	 * Profile.
	 */
	private final Profile profile;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 * @param profile Profile
	 */
	public DbProfileAccesses(final DataSource source, final Profile profile) {
		this.source = source;
		this.profile = profile;
	}

	private final boolean isAdmin() {
		return this.profile.name().equals("Administrateur");
	}

	@Override
	public Iterable<Access> iterate() {
		try {
			if(this.isAdmin()) {
				return () -> Arrays.stream(Access.values()).filter(a -> a != Access.TRAVAILLER_DANS_SON_PROPRE_ESPACE_DE_TRAVAIL).iterator();
			} else {
				return 
	                new JdbcSession(this.source)
	                    .sql(
	                        new Joined(
	                            " ",
	                            "SELECT access_id FROM access_profile",
	            				"WHERE profile_id=?"
	                        ).toString()
	                    )
	                    .set(this.profile.id())
	                    .select(
	                		new ListOutcome<>(
	                        rset ->
	                            Access.valueOf(rset.getString(1))
	                        )
	                    );
			}
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
	}

	@Override
	public boolean has(Access access) {
		try {
			if(this.isAdmin() && access != Access.TRAVAILLER_DANS_SON_PROPRE_ESPACE_DE_TRAVAIL) {
				return true;
			} else {
				return new JdbcSession(this.source)
				        .sql(
			        		new Joined(
		        				" ",
		        				"SELECT COUNT(*) FROM access_profile",
		        				"WHERE access_id=? AND profile_id=?"
		        			).toString()
		        		)
				        .set(access.name())
				        .set(this.profile.id())
				        .select(new SingleOutcome<>(Long.class)) > 0;
			}
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public void add(Access access) {
		try {
			if(this.isAdmin()) {
				return;
			}
			new JdbcSession(this.source)
            .sql(
                new Joined(
                    " ",
                    "INSERT INTO access_profile",
                    "(access_id, profile_id)",
                    "VALUES",
                    "(?, ?)"
                ).toString()
            )
            .set(access.name())
            .set(this.profile.id())
            .insert(Outcome.VOID);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
	}

	@Override
	public void remove(Access access) {
		try {
			if(this.isAdmin()) {
				return;
			}
            new JdbcSession(this.source)
                .sql(
                    new Joined(
                        " ",
                        "DELETE FROM access_profile",
                        "WHERE access_id=? AND profile_id=?"
                    ).toString()
                )
                .set(access.name())
                .set(this.profile.id())
                .execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
	}
	
	@Override
	public void removeAll() {
		try {
			if(this.isAdmin()) {
				return;
			}
            new JdbcSession(this.source)
                .sql(
                    new Joined(
                        " ",
                        "DELETE FROM access_profile",
                        "WHERE profile_id=?"
                    ).toString()
                )
                .set(this.profile.id())
                .execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
	}

}
