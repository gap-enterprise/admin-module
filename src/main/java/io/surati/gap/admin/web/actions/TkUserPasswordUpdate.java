/**
MIT License

Copyright (c) 2021 Surati

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package io.surati.gap.admin.web.actions;

import io.surati.gap.admin.api.User;
import io.surati.gap.admin.rq.RqUser;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.RsLogout;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import javax.sql.DataSource;
import java.util.logging.Level;

/**
 * Update user password.
 * 
 * @since 0.1
 */
public final class TkUserPasswordUpdate implements Take {


	/**
	 * Database
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkUserPasswordUpdate(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
		final String oldpassword = form.single("oldpass");
		final String password = form.single("password");		
		final User currentUser = new RqUser(this.source, req);
		currentUser.changePassword(oldpassword, password);		
		return new RsForward(
			new RsLogout(
				new RsFlash(
					String.format("Le mot de passe a été changé avec succès !"),
					Level.INFO
				)
			),
			"/login"
		);	
	}
}
