package io.surati.gap.admin.web.actions;

/**
 * Take that change an enterprise logo.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
import io.surati.gap.admin.db.DbLog;
import io.surati.gap.admin.api.Company;
import io.surati.gap.admin.api.Log;
import io.surati.gap.admin.prop.PropCompany;
import io.surati.gap.commons.utils.convert.RqFilename;
import io.surati.gap.web.base.log.RqLog;
import org.apache.commons.io.FilenameUtils;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.multipart.RqMtSmart;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.logging.Level;

public final class TkEnterpriseLogoSave implements Take {

	/**
	 * Database
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkEnterpriseLogoSave(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final Log log = new RqLog(this.source, req);
		final RqGreedy rqg = new RqGreedy(req);
		final RqMtSmart mt = new RqMtSmart(rqg);					        
		final Request rqimage = mt.single("logo");
		final Company company = new PropCompany();
		try(InputStream file = rqimage.body()) {
			final String filename = new RqFilename(rqimage).value().toLowerCase();
			final String ext = FilenameUtils.getExtension(filename);
			company.changeLogo(file, ext);
		}
		log.info("Changement du logo de l'entreprise");
		return new RsForward(
			new RsFlash(
				"Changement du logo effectué avec succès !",
				Level.INFO
			),
			"/enterprise"
		);
	
	}
}
