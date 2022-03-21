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

import io.surati.gap.admin.db.DbLog;
import io.surati.gap.admin.db.DbProfileAccesses;
import io.surati.gap.admin.db.DbProfiles;
import io.surati.gap.admin.api.Access;
import io.surati.gap.admin.api.Log;
import io.surati.gap.admin.api.Profile;
import io.surati.gap.admin.api.ProfileAccesses;
import org.apache.commons.lang3.StringUtils;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;

/**
 * Take that creates a access right for a profile.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
public final class TkAccessRightSave implements Take {

	/**
	 * Database
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkAccessRightSave(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final Log log = new DbLog(this.source, req);
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		final Long profile_id = Long.parseLong(form.single("profile_id"));		
		final Profile profile = new DbProfiles(this.source).get(profile_id);
		final ProfileAccesses items = new DbProfileAccesses(this.source, profile);
		final Collection<String> accadded = new LinkedList<>();
		for (String name : form.names()) {
			if(name.startsWith("access-")) {
				final boolean ischecked = Boolean.parseBoolean(form.single(name));
				if(ischecked) {
					final Access access = Access.valueOf(name.split("-")[1].toUpperCase());
					items.add(access);
					accadded.add(access.name());
				}
			}
		}
		log.info(
			"Ajout d'accès (%s) au profil (%s)",
			StringUtils.abbreviate(String.join(", ", accadded), 175),
			profile
		);
		return new RsForward(
			new RsFlash(
				String.format("Mise à jour de(s) droit(s) d'accès du profil %s avec succès !", profile.name()),
				Level.INFO
			),
			String.format("/profile/view?id=%s", profile.id())
		);	
	}
}
