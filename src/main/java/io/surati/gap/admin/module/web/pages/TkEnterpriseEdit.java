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
package io.surati.gap.admin.module.web.pages;

import io.surati.gap.admin.module.api.Company;
import io.surati.gap.admin.module.prop.PropCompany;
import io.surati.gap.admin.module.web.server.RsPage;
import io.surati.gap.admin.module.web.xe.XeReferenceDocumentTypes;
import javax.sql.DataSource;
import org.cactoos.iterable.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;

/**
 * Take that edit enterprise information
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 3.0
 */

public final class TkEnterpriseEdit implements Take {

	private final DataSource source;
	
	public TkEnterpriseEdit(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {	
		final Company company = new PropCompany();
		final XeSource src;		
		src = new XeChain(
			new XeAppend("name", company.name()),
			new XeAppend("abbreviated", company.abbreviated()),
			new XeAppend("ncc", company.ncc()),
			new XeAppend("city", company.city()),
			new XeAppend("country", company.country()),
			new XeAppend("address", company.address()),
			new XeAppend("tel1", company.tel1()),
			new XeAppend("tel2", company.tel2()),
			new XeAppend("email", company.email()),
			new XeAppend("website", company.website()),
			new XeAppend("headquarters", company.headquarters()),
			new XeAppend("representative", company.representative()),
			new XeAppend("representative_position", company.representativePosition()),
			new XeAppend("representative_civility", company.representativeCivility()),
			new XeAppend("export_location", company.parameter("export_location")),
			new XeReferenceDocumentTypes(),
			new XeAppend("default_reference_document_type_id", company.parameter(PropCompany.DEFAULT_REFERENCE_DOCUMENT_TYPE))
		);
		return new RsPage(
			"/xsl/enterprise/edit.xsl", 
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "enterprise"),
				src
			)
		);
	}
}
