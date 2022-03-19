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
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.module.api.User;
import io.surati.gap.admin.module.api.Users;
import io.surati.gap.admin.module.exceptions.DatabaseException;
import org.cactoos.text.Joined;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Pagined banks from Database.
 * 
 * @since 0.1
 */
public final class DbPaginedUsers implements Users  {
	
	/**
	 * Origin.
	 */
	private final Users origin;
	
	/**
	 * Data Source.
	 */
	private final DataSource source;
	
	private final Long nbperpage;
	
	private final Long page;
	
	private final String filter;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbPaginedUsers(final DataSource source, final Long nbperpage, final Long page, final String filter) {
		this.origin = new DbUsers(source);
		this.source = source;
		this.nbperpage = nbperpage;
		this.page = page;
		this.filter = filter;
	}
	
	@Override
	public boolean authenticate(String login, String password) {
		return this.origin.authenticate(login, password);
	}

	@Override
	public boolean authenticatePwdEncrypted(String login, String password) {
		return this.origin.authenticatePwdEncrypted(login, password);
	}

	@Override
	public boolean has(String login) {
		return this.origin.has(login);
	}

	@Override
	public User get(String login) {
		return this.origin.get(login);
	}

	@Override
	public User get(Long id) {
		return this.origin.get(id);
	}

	@Override
	public User register(String name, String login, String password) {
		return this.origin.register(name, login, password);
	}

	@Override
	public Iterable<User> iterate() {
		try {
            return 
                new JdbcSession(this.source)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT au.id FROM app_user as au",
	                        "LEFT JOIN person as ps ON ps.id = au.id",
	                        "LEFT JOIN profile as pf ON pf.id = au.profile_id",
	                        "WHERE au.login ILIKE ? OR pf.name ILIKE ? OR ps.name ILIKE ?",
            				"LIMIT ? OFFSET ?"
                        ).toString()
                    )
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set(this.nbperpage)
                    .set(this.nbperpage * (this.page - 1))
                    .select(
                        new ListOutcome<>(
                            rset ->
                            new DbUser(
                                this.source,
                                rset.getLong(1)
                            )
                        )
                    );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
	}

	@Override
	public void remove(Long id) {
		this.origin.remove(id);
	}

	@Override
	public Long count() {
		try {
			return
				new JdbcSession(this.source)
					.sql(
	                    new Joined(
	                        " ",
	                        "SELECT COUNT(au.*) FROM app_user as au",
	                        "LEFT JOIN person as ps ON ps.id = au.id",
	                        "LEFT JOIN profile as pf ON pf.id = au.profile_id",
	                        "WHERE au.login ILIKE ? OR pf.name ILIKE ? OR ps.name ILIKE ?"
	                    ).toString()
	                )
					.set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
					.select(new SingleOutcome<>(Long.class));
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}
}
