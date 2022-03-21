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

import io.surati.gap.admin.module.api.Company;
import io.surati.gap.admin.module.api.ReferenceDocumentType;
import io.surati.gap.admin.module.prop.PropCompany;
import java.util.logging.Level;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

/**
 * Take that saves enterprise information.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 3.0
 */

public final class TkEnterpriseSave implements Take {
	
	@Override
	public Response act(Request req) throws Exception {		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		final String name = form.single("name");
		final String abbreviated = form.single("abbreviated");
		final String ncc = form.single("ncc");
		final String city = form.single("city");
		final String country = form.single("country");
		final String address = form.single("address");
		final String tel1 = form.single("tel1");
		final String tel2 = form.single("tel2", "");
		final String email = form.single("email", "");
		final String website = form.single("website", "");
		final String headquarters = form.single("headquarters", "");
		final String representative = form.single("representative", "");
		final String repreposition = form.single("representative_position", "");
		final String reprecivility = form.single("representative_civility", "");
		final String exportlocation = form.single("export_location", "");
		final ReferenceDocumentType defaultrefdoctypeid = ReferenceDocumentType.valueOf(form.single("default_reference_document_type_id"));
		final Company company = new PropCompany();
		company.reidentify(name, abbreviated, ncc);
		company.contacts(tel1, tel2, email, website);
		company.relocate(city, country, address, headquarters);
		company.representative(representative, repreposition, reprecivility);
		company.parameter("export_location", exportlocation);
		if(defaultrefdoctypeid == ReferenceDocumentType.NONE)
			throw new IllegalArgumentException("Le type de document de référence par d�faut ne peut pas être null ou vide.");
		else
			company.parameter(PropCompany.DEFAULT_REFERENCE_DOCUMENT_TYPE, defaultrefdoctypeid.name());
		return new RsForward(
			new RsFlash(
				"Mise à jour des informations de l'entreprise avec succès !",
				Level.INFO
			),
			"/enterprise"
		);	
	}

}
