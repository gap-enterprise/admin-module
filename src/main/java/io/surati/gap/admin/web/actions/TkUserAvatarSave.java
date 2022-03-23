package io.surati.gap.admin.web.actions;

/**
 * Take that saves an user avatar.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.logging.Level;

import io.surati.gap.admin.codec.GIdentity;
import io.surati.gap.admin.db.DbLog;
import io.surati.gap.admin.db.DbUsers;
import io.surati.gap.admin.api.Log;
import io.surati.gap.admin.api.User;
import io.surati.gap.commons.utils.convert.RqFilename;
import io.surati.gap.web.base.log.RqLog;
import io.surati.gap.web.base.rq.RootPageUri;
import io.surati.gap.web.base.rq.RqUser;
import org.apache.commons.io.FilenameUtils;
import org.cactoos.text.TextOf;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.Pass;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rq.multipart.RqMtSmart;

public final class TkUserAvatarSave implements Take {

	/**
	 * Database
	 */
	private final DataSource source;
	
	private final Pass pass;

	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkUserAvatarSave(final DataSource source, final Pass pass) {
		this.source = source;
		this.pass = pass;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final Log log = new RqLog(this.source, req);
		final RqGreedy rqg = new RqGreedy(req);
		final RqMtSmart mt = new RqMtSmart(rqg);					        
		final Request rqimage = mt.single("avatar");
		final Long id = Long.parseLong(new TextOf(new RqFormSmart(mt.single("id")).body()).asString().trim());
		final User user = new DbUsers(this.source).get(id);
		try(InputStream file = rqimage.body()) {
			final String filename = new RqFilename(rqimage).value().toLowerCase();
			final String ext = FilenameUtils.getExtension(filename);
			user.changePhoto(file, ext);
		}
		final String uri = new RootPageUri(req).toString();
		final User currentuser = new RqUser(this.source, req);
		if(user.equals(currentuser)) {
			final Identity idt = new GIdentity(user);
			log.info("Changement de la photo de mon profil");
			return pass.exit(
				new RsForward(
					new RsFlash(
						"Changement de la photo de profil avec succès !",
						Level.INFO
					),
					uri
				), 
				idt
			);
		} else {
			log.info("Changement de la photo de profil de l'utilisateur (%s)", user);
			return new RsForward(
				new RsFlash(
					"Changement de la photo effectué avec succès !",
					Level.INFO
				),
				uri
			);
		}
			
	}
}
