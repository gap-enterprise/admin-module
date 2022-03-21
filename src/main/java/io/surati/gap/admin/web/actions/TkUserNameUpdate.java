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

import io.surati.gap.admin.db.DbUsers;
import io.surati.gap.admin.api.User;
import io.surati.gap.admin.rq.RqUser;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import javax.sql.DataSource;
import java.util.logging.Level;

/**
 * Take that update a user name.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
public final class TkUserNameUpdate implements Take {

	/**
	 * DataSource
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkUserNameUpdate(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {	
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));						
		final String name = form.single("newName");		
		final User currentUser = new RqUser(this.source, req);
		final Long id = currentUser.id();	
		final User user = new DbUsers(source).get(id);
		user.update(name);	
		return new RsForward(
			new RsFlash(
				"Vos nom et prénoms ont été modifiés avec succès !",
				Level.INFO
			),
			"/user-profile"
		);	
	}
}
