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
package io.surati.gap.admin.rq;

import io.surati.gap.admin.db.DbUsers;
import io.surati.gap.admin.api.Profile;
import io.surati.gap.admin.api.User;
import io.surati.gap.admin.api.Users;
import io.surati.gap.admin.secure.Salt;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import org.takes.HttpException;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;

/**
 * User of {@link Request}.
 *
 * @since 0.1
 */
public final class RqUser implements User {
    
	private final User origin;
	
	/**
     * Ctor.
     * @param source DataSource
     * @param req Request
     * @throws IOException 
     * @throws IOException If fails
     */
    public RqUser(final DataSource source, final Request req) throws IOException {
        this.origin = takeUser(source, req);
    }

	private static User takeUser(
		final DataSource source,
		final Request request
	) throws IOException {     
		final Identity identity = new RqAuth(request).identity();
		User user;
		if (identity.equals(Identity.ANONYMOUS)) {
			user = User.EMPTY;
		} else {
			try {
				Map<String, String> props = identity.properties();
		    	Users users = new DbUsers(source);
		    	final String login = props.get("login");
		    	final String password = props.get("password");
		    	if(users.authenticatePwdEncrypted(login, password)) {
		    		user = users.get(login);
		    	} else {
		    		user = User.EMPTY;
		    	}
			} catch (IllegalArgumentException ex) {
				user = User.EMPTY;
			}
		}
		if(user == User.EMPTY) {
			throw new HttpException(
                HttpURLConnection.HTTP_UNAUTHORIZED
            );
		}
		return user;
    }

	@Override
	public Long id() {
		return this.origin.id();
	}

	@Override
	public String name() {
		return this.origin.name();
	}

	@Override
	public void update(String name) {
		this.origin.update(name);
	}

	@Override
	public String login() {
		return this.origin.login();
	}

	@Override
	public String password() {
		return this.origin.password();
	}

	@Override
	public void forceChangePassword(String newPassword) {
		this.origin.forceChangePassword(newPassword);
	}

	@Override
	public void changePassword(String oldpassword, String password) {
		this.origin.changePassword(oldpassword, password);
	}

	@Override
	public void block(boolean enable) {
		this.origin.block(enable);		
	}

	@Override
	public boolean blocked() {
		return this.origin.blocked();
	}

	@Override
	public void update(String login, String name) {
		this.origin.update(login, name);
	}

	@Override
	public Profile profile() {
		return this.origin.profile();
	}

	@Override
	public void assign(Profile profile) {
		this.origin.assign(profile);
	}

	@Override
	public Salt salt() {
		return this.origin.salt();
	}

	@Override
	public void changePhoto(InputStream content, String ext) throws IOException {
		this.origin.changePhoto(content, ext);
	}

	@Override
	public InputStream photo() throws IOException {
		return this.origin.photo();
	}

	@Override
	public String photoBase64() {
		return this.origin.photoBase64();
	}

	@Override
	public String photoLocation() {
		return this.origin.photoLocation();
	}
	
	@Override
	public boolean equals(final Object obj) {
		return this.origin.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return this.origin.hashCode();
	}
}
