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

import io.surati.gap.admin.module.api.Access;
import io.surati.gap.admin.module.api.Log;
import io.surati.gap.admin.module.api.Profile;
import io.surati.gap.admin.module.api.ProfileAccesses;
import io.surati.gap.admin.module.db.DbLog;
import io.surati.gap.admin.module.db.DbProfileAccesses;
import io.surati.gap.admin.module.db.DbProfiles;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import javax.sql.DataSource;
import java.util.logging.Level;

/**
 * Take that deletes a access right.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1.0
 */

public final class TkAccessRightDelete implements Take {
	
	/**
	 * Database
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkAccessRightDelete(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final Log log = new DbLog(this.source, req);
		final Access access = Access.valueOf(new RqHref.Smart(req).single("id"));
		final Long profile_id = Long.parseLong(new RqHref.Smart(req).single("profile", "0"));
		final Profile profile = new DbProfiles(this.source).get(profile_id);
		final ProfileAccesses items = new DbProfileAccesses(this.source, profile);
		items.remove(access);
		log.info("Suppression de l'accès (%s) du profil (%s)", access.name(), profile.toString());
		return new RsForward(
			new RsFlash(
					String.format("Le droit d'accès du profil %s a été supprimé avec succès !", profile.name()),
				Level.INFO
			),
			String.format("/profile/view?id=%s", profile.id())
		);	
	}
}
