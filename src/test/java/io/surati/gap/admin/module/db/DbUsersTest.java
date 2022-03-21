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

import javax.sql.DataSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Satisfies;
import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import com.lightweight.db.LiquibaseDataSource;
import io.surati.gap.admin.module.api.User;
import io.surati.gap.admin.module.api.Users;

final class DbUsersTest {

    /**
     * Users users.
     */
    private Users users;
	
	@BeforeEach
    void setup() {
    	final DataSource source = new LiquibaseDataSource(
            new EmbeddedPostgreSQLDataSource(),
            "liquibase/db-admin.changelog-master.xml"
        );
    	this.users = new DbUsers(source);
    }
	
	@Test
	void register() {
		final String name = "Administrateur";
		final String login = "admin-gap";
		final String password = "gap2022";
		final User user = users.register(name, login, password);
		MatcherAssert.assertThat(
			users.get(1L),
			new Satisfies<>(
					usr -> usr.name().equals(user.name()) &&
						   usr.login().equals(user.login()) &&
						   usr.password().equals(user.password())
			)
		);
	}
	
	@Test
	public void hasUserByItsLogin() {
		final String name = "Administrateur";
		final String login = "admin-gap";
		final String password = "gap2022";
		users.register(name, login, password);
		MatcherAssert.assertThat(
			users.has("admin-gap"),
			Matchers.is(true)
		);
		MatcherAssert.assertThat(
			users.has("brou87"),
			Matchers.is(false)
		);
	}
	
	@Test
	public void hasUserByItsId() {
		final String name = "Administrateur";
		final String login = "admin-gap";
		final String password = "gap2022";
		users.register(name, login, password);
		MatcherAssert.assertThat(
			users.get(login).id(),
			Matchers.equalTo(1L)
		);
	}
	
	@Test
	void countTotalNumberOfUsers() {
		final String[] names = { "Mentor", "Guest" };
		final String[] logins = { "mentor", "guest" };
		final String password = "gap2022";
		users.register(names[0], logins[0], password);
		users.register(names[1], logins[1], password);
		MatcherAssert.assertThat(
            "Application should have two registrations.",
            users.count(),
            new IsEqual<>(Long.valueOf(logins.length))
        );
	}
	
	@Test
	void authenticateUserWithNonEncryptedPassword() {
		final String name = "Administrateur";
		final String login = "admin-gap";
		final String password = "gap2022";
		users.register(name, login, password);
		MatcherAssert.assertThat(
			users.authenticate(login, password),
			Matchers.is(true)
		);
	}
	
	@Test
	void authenticateUserWithEncryptedPassword() {
		final String name = "Administrateur";
		final String login = "admin-gap";
		final String password = "gap2022";
		users.register(name, login, password);
		MatcherAssert.assertThat(
			users.authenticatePwdEncrypted(login, password),
			Matchers.is(false)
		);
	}
	
	/*@Test
	void removeUser() {
		final String name = "Administrateur";
		final String login = "admin-gap";
		final String password = "gap2022";
		users.register(name, login, password);
		final User user = users.get(login);
		users.remove(user.id());
		MatcherAssert.assertThat(
			user.id(),
			Matchers.equalTo(0L)
		);
	}*/
	
    @Test
    void iterate() {
    	final String[] names = { "Mentor", "Guest" };
		final String[] logins = { "mentor", "guest" };
		final String password = "gap2022";
		users.register(names[0], logins[0], password);
		users.register(names[1], logins[1], password);
		MatcherAssert.assertThat(
            "Application should have two registrations.",
            users.count(),
            new IsEqual<>(Long.valueOf(logins.length))
        );
		int idx = logins.length;
		for (final User user : users.iterate()) {
			idx -= 1;
			final String login = logins[idx];
            final String name = names[idx];
			MatcherAssert.assertThat(
				"User should match in descending order.",
				user,
				new Satisfies<>(
						usr -> usr.name().equals(user.name()) &&
							   usr.login().equals(user.login()) &&
							   usr.password().equals(user.password())
				)
			);
		}
    }
}
