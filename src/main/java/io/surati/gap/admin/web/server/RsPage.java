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
package io.surati.gap.admin.web.server;

import com.minlessika.utils.PreviousLocation;
import io.surati.gap.admin.rq.RqUser;
import io.surati.gap.admin.web.xe.XeAccesses;
import org.takes.Request;
import org.takes.Response;
import org.takes.Scalar;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.facets.auth.XeIdentity;
import org.takes.facets.auth.XeLogoutLink;
import org.takes.facets.flash.XeFlash;
import org.takes.facets.fork.FkTypes;
import org.takes.facets.fork.RsFork;
import org.takes.rs.RsWithType;
import org.takes.rs.RsWrap;
import org.takes.rs.RsXslt;
import org.takes.rs.xe.RsXembly;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDate;
import org.takes.rs.xe.XeMemory;
import org.takes.rs.xe.XeMillis;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeStylesheet;
import org.takes.rs.xe.XeWhen;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;

/**
 * Index resource, front page of the website.
 *
 * @since 0.1
 */
public final class RsPage extends RsWrap {

	public RsPage(final String xsl, final Request req, final DataSource source, final Scalar<Iterable<XeSource>> src) throws IOException {
		super(RsPage.make(xsl, req, source, src));
	}
	
	public RsPage(final String xsl, final Request req, final DataSource source) throws IOException {
		super(RsPage.make(xsl, req, source, Collections::emptyList));
	}
	
	private static Response make(final String xsl, final Request req, final DataSource source, final Scalar<Iterable<XeSource>> src) throws IOException {	
		final XeSource sec;
		if(!new RqAuth(req).identity().equals(Identity.ANONYMOUS)) {
			sec = new XeAccesses("current_user_accesses", new RqUser(source, req).profile().accesses().iterate());
		} else {
			sec = XeSource.EMPTY;
		}
		final Response raw = new RsXembly(
			new XeStylesheet(xsl),
			new XeAppend(
				"page", 
				new XeMillis(false),
				new XeChain(src),
				new XeMemory(),
				new XeDate(),
				new XeFlash(req),
				new XeWhen(
					!new RqAuth(req).identity().equals(Identity.ANONYMOUS), 
					new XeChain(
						new XeIdentity(req), 
						new XeLogoutLink(req),
						sec
					)
				),
                new XeAppend(
                    "version",
                    new XeAppend("name", "3.0.0")
                ),
                new XeAppend(
            		"previous_location",
            		new PreviousLocation(req).toString()
            	),
                new XeMillis(true)
			)
		);
		
		return new RsFork(
            req,
            new FkTypes(
                "*/*",
                new RsXslt(new RsWithType(raw, "text/html"))
            )
        );
	}

}
