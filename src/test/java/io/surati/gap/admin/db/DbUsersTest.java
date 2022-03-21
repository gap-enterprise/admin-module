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

import io.surati.gap.admin.DataSourceExtension;
import io.surati.gap.admin.DataSourceParameterResolver;
import io.surati.gap.admin.api.User;
import io.surati.gap.admin.api.Users;
import javax.sql.DataSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.llorllale.cactoos.matchers.Satisfies;
import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import com.lightweight.db.LiquibaseDataSource;

/**
 * Test case for {@link DbUsers}.
 *
 * @since 0.1
 */
@ExtendWith(DataSourceExtension.class)
@ExtendWith(DataSourceParameterResolver.class)
final class DbUsersTest {

    /**
     * Users users.
     */
    private Users users;
    
    /**
     * User admin.
     */
    private User admin;
	
	@BeforeEach
    void setup() {
    	final DataSource source = new LiquibaseDataSource(
            new EmbeddedPostgreSQLDataSource(),
            "liquibase/db-admin.changelog-master.xml"
        );
    	this.users = new DbUsers(source);
    	this.admin = new DbUser(source, 1L);
    }
	
	@TestTemplate
	void registersAnUser() {
		final String name = "Marx Brou";
		final String login = "brou87";
		final User user = this.users.register(name, login, "broupwd");
		MatcherAssert.assertThat(
			user,
			new Satisfies<>(
				usr -> usr.name().equals(name) &&
				   usr.login().equals(login)
			)
		);
	}
	
	@TestTemplate
	public void checksUserExistenceByItsLogin() {
		MatcherAssert.assertThat(
			this.users.has(this.admin.login()),
			Matchers.is(true)
		);
	}
	
	@TestTemplate
	public void getsUserById() {
		final User user = this.users.get(this.admin.id());
		MatcherAssert.assertThat(
			user,
			new Satisfies<>(
				usr -> usr.id().equals(this.admin.id()) &&
				   usr.name().equals(this.admin.name()) &&
                   usr.login().equals(this.admin.login())
			)
		);
	}
	
	@TestTemplate
	void countsTotalNumberOfUsers() {
		this.users.register("Mentor", "mentor", "mentorpwd");
		this.users.register("Guest", "guest", "guestpwd");
		MatcherAssert.assertThat(
            this.users.count(),
            Matchers.equalTo(3L)
        );
	}
	
	@TestTemplate
	void authenticatesUserWithNonEncryptedPassword() {
		MatcherAssert.assertThat(
			this.users.authenticate(this.admin.login(), "admin"),
			Matchers.is(true)
		);
	}
	
	@TestTemplate
	void authenticatesUserWithEncryptedPassword() {
		MatcherAssert.assertThat(
			users.authenticatePwdEncrypted(
				this.admin.login(),
				this.admin.password()
			),
			Matchers.is(true)
		);
	}
	
	@TestTemplate
    void iterate() {
    	final String[] names = {"Administrateur", "Mentor", "Guest"};
		final String[] logins = {"admin", "mentor", "guest"};
		this.users.register(names[1], logins[1], "pwd1");
		this.users.register(names[2], logins[2], "pwd2");
		MatcherAssert.assertThat(
            "Application should have three users.",
            this.users.count(),
            new IsEqual<>(Long.valueOf(3L))
        );
		int idx = 0;
		for (final User user : this.users.iterate()) {
			final String name = names[idx];
			final String login = logins[idx];
			MatcherAssert.assertThat(
				"Users should match in ascending order.",
				user,
				new Satisfies<>(
					usr -> usr.name().equals(name) &&
						   usr.login().equals(login)
				)
			);
			idx ++;
		}
    }
}
