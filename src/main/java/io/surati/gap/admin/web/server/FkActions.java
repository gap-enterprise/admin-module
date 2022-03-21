package io.surati.gap.admin.web.server;

import com.minlessika.db.Database;
import com.minlessika.db.TkTransaction;
import io.surati.gap.admin.web.TkAnonymous;
import io.surati.gap.admin.web.TkSecure;
import io.surati.gap.admin.web.actions.TkAccessRightDelete;
import io.surati.gap.admin.web.actions.TkAccessRightSave;
import io.surati.gap.admin.web.actions.TkAuthenticate;
import io.surati.gap.admin.web.actions.TkEnterpriseLogoSave;
import io.surati.gap.admin.web.actions.TkEnterpriseSave;
import io.surati.gap.admin.web.actions.TkLogout;
import io.surati.gap.admin.web.actions.TkProfileDelete;
import io.surati.gap.admin.web.actions.TkProfileSave;
import io.surati.gap.admin.web.actions.TkUserAvatarSave;
import io.surati.gap.admin.web.actions.TkUserBlock;
import io.surati.gap.admin.web.actions.TkUserDelete;
import io.surati.gap.admin.web.actions.TkUserNameUpdate;
import io.surati.gap.admin.web.actions.TkUserPasswordForceChange;
import io.surati.gap.admin.web.actions.TkUserPasswordUpdate;
import io.surati.gap.admin.web.actions.TkUserSave;
import org.takes.facets.auth.Pass;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for actions.
 *
 * @since 0.1
 */
public final class FkActions extends FkWrap {

	public FkActions(
		final Database source,
		final Pass pass
	) {
		super(
			new FkChain(
					new FkRegex(
						"/authenticate", 
						new TkAnonymous(
							new TkAuthenticate(source, pass)
						)
					),
					new FkRegex(
						"/logout", 
						new TkSecure(
							new TkLogout(source),
							source
						)
					),
					new FkRegex(
						"/user-profile/update-user-name", 
						new TkSecure(
							new TkUserNameUpdate(source),
							source
						)
					),
					new FkRegex(
						"/user-profile/update-change-password", 
						new TkSecure(
							new TkTransaction(
								new TkUserPasswordUpdate(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/user/save", 
						new TkSecure(
							new TkTransaction(
								new TkUserSave(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/user/delete",
						new TkSecure(
							new TkTransaction(
								new TkUserDelete(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/user/block",
						new TkSecure(
							new TkTransaction(
								new TkUserBlock(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/user/update-password",
						new TkSecure(
							new TkTransaction(
								new TkUserPasswordForceChange(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/profile/save", 
						new TkSecure(
							new TkTransaction(
								new TkProfileSave(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/profile/delete",
						new TkSecure(
							new TkTransaction(
								new TkProfileDelete(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/access-right/save", 
						new TkSecure(
							new TkTransaction(
								new TkAccessRightSave(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/access-right/delete",
						new TkSecure(
							new TkTransaction(
								new TkAccessRightDelete(source),
								source
							),
							source
						)
					),
					new FkRegex(
						"/enterprise/save", 
						new TkSecure(
							new TkEnterpriseSave(),
							source
						)
					),
					new FkRegex(
						"/user/avatar/save", 
						new TkSecure(
							new TkUserAvatarSave(source, pass),
							source
						)
					),
					new FkRegex(
						"/enterprise/logo/save",
						new TkSecure(
							new TkTransaction(
								new TkEnterpriseLogoSave(source),
								source
							),
							source
						)
					)
			)
		);
	}
}
