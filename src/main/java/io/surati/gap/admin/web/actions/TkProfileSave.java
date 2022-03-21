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

import io.surati.gap.admin.db.DbProfiles;
import io.surati.gap.admin.api.Profile;
import io.surati.gap.admin.api.Profiles;
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
 * Take that creates a profile.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
public class TkProfileSave implements Take {

	/**
	 * Database
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkProfileSave(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {	
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		final Long id = Long.parseLong(form.single("id", "0"));		
		final String name = form.single("name");
		
		final Profiles items = new DbProfiles(this.source);
		final String msg;
		final Profile item;
		
		if(id.equals(0L)) {
			item = items.add(name);
			msg = String.format("Le profil %s a été créé avec succès !", name);
		} else {
			item = items.get(id);
			item.update(name);
			msg = String.format("Le profil %s a été modifié avec succès !", name);
		}
		
		return new RsForward(
			new RsFlash(
				msg,
				Level.INFO
			),
			String.format("/profile/view?id=%s", item.id())
		);	
	}
}
