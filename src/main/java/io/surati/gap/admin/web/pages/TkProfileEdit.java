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
package io.surati.gap.admin.web.pages;

import io.surati.gap.admin.api.Profile;
import io.surati.gap.admin.db.DbProfiles;
import io.surati.gap.admin.web.xe.XeProfile;
import io.surati.gap.web.base.RsPage;
import org.cactoos.iterable.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import javax.sql.DataSource;

/**
 * Take that allows to enter details for profile to create or edit.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */ 
public final class TkProfileEdit implements Take {
	
	/**
	 * DataSource
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkProfileEdit(final DataSource source) {
		this.source = source;
	}

	@Override
	public Response act(Request req) throws Exception {
		
		final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
		final XeSource src;
		
		if(id.equals(0L)) {
			src = XeSource.EMPTY;
		} else {
			final Profile profile = new DbProfiles(this.source).get(id);
			src = new XeProfile(profile);
		}
		return new RsPage(
			"/io/surati/gap/admin/xsl/access_right_profile/edit.xsl",
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "profile"),
				src
			)
		);
	}

}
