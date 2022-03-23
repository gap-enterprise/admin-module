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
import io.surati.gap.web.base.rq.RqUser;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import javax.sql.DataSource;
import java.util.logging.Level;

/**
 * Take that block or unblock an user.
 *
 * <p>The class is immutable and thread-safe.</p>
 * 
 * @since 0.1
 */
public final class TkUserBlock implements Take {
	
	/**
	 * Database
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkUserBlock(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {		
		final User currentUser = new RqUser(this.source, req);
		final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
		
		if(currentUser.id().equals(id)) 
			throw new IllegalArgumentException("Vous ne pouvez pas effectuer cette action !");
		
		final boolean enable = Boolean.parseBoolean(new RqHref.Smart(req).single("enable"));
		
		final User user = new DbUsers(source).get(id);
		user.block(enable);
		
		final String msg;
		if(enable) {
			msg = String.format("L'utilisateur a été bloqué avec succès !");
		} else {
			msg = String.format("L'utilisateur a été debloqué avec succès !");
		}
		
		return new RsForward(
			new RsFlash(
					msg,
				Level.INFO
			),
			"/user"
		);	
	}
	
}
