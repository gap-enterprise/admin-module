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
package io.surati.gap.admin.module.web.actions;

import io.surati.gap.admin.module.api.Profile;
import io.surati.gap.admin.module.api.User;
import io.surati.gap.admin.module.api.Users;
import io.surati.gap.admin.module.db.DbProfiles;
import io.surati.gap.admin.module.db.DbUsers;
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
 * Take that create or edit an user.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1.0
 */

public final class TkUserSave implements Take {

	/**
	 * Database
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkUserSave(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		final Long id = Long.parseLong(form.single("id", "0"));	
		final String name = form.single("name");
		final Long profile_id = Long.parseLong(form.single("profile_id"));	
		final String password = "gap";
		final Users items = new DbUsers(this.source);
		final String msg;
		final User item;
		final Profile profile = new DbProfiles(this.source).get(profile_id);
		if(id.equals(0L)) {
			final String login = form.single("login");
			item = items.register(name, login, password);
			msg = String.format("L'utilisateur %s a été créé avec succès !", name);
		} else {
			item = items.get(id);
			item.update(item.login(), name);
			msg = String.format("L'utilisateur %s a été modifié avec succès !", name);
		}
		item.assign(profile);
		return new RsForward(
			new RsFlash(
				msg,
				Level.INFO
			),
			String.format("/user/view?id=%s", item.id())
		);	
	}
}
