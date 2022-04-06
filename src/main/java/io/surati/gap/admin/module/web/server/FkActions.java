package io.surati.gap.admin.module.web.server;

import io.surati.gap.admin.module.web.actions.TkAccessRightDelete;
import io.surati.gap.admin.module.web.actions.TkAccessRightSave;
import io.surati.gap.admin.module.web.actions.TkAuthenticate;
import io.surati.gap.admin.module.web.actions.TkEnterpriseLogoSave;
import io.surati.gap.admin.module.web.actions.TkEnterpriseSave;
import io.surati.gap.admin.module.web.actions.TkLogout;
import io.surati.gap.admin.module.web.actions.TkProfileDelete;
import io.surati.gap.admin.module.web.actions.TkProfileSave;
import io.surati.gap.admin.module.web.actions.TkUserAvatarSave;
import io.surati.gap.admin.module.web.actions.TkUserBlock;
import io.surati.gap.admin.module.web.actions.TkUserDelete;
import io.surati.gap.admin.module.web.actions.TkUserNameUpdate;
import io.surati.gap.admin.module.web.actions.TkUserPasswordForceChange;
import io.surati.gap.admin.module.web.actions.TkUserPasswordUpdate;
import io.surati.gap.admin.module.web.actions.TkUserSave;
import io.surati.gap.web.base.TkAnonymous;
import io.surati.gap.web.base.TkSecure;
import javax.sql.DataSource;
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

	public FkActions(final DataSource source, final Pass pass) {
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
							new TkUserPasswordUpdate(source),
							source
						)
					),
					new FkRegex(
						"/user/save", 
						new TkSecure(
							new TkUserSave(source),
							source
						)
					),
					new FkRegex(
						"/user/delete",
						new TkSecure(
							new TkUserDelete(source),
							source
						)
					),
					new FkRegex(
						"/user/block",
						new TkSecure(
							new TkUserBlock(source),
							source
						)
					),
					new FkRegex(
						"/user/update-password",
						new TkSecure(
							new TkUserPasswordForceChange(source),
							source
						)
					),
					new FkRegex(
						"/profile/save", 
						new TkSecure(
							new TkProfileSave(source),
							source
						)
					),
					new FkRegex(
						"/profile/delete",
						new TkSecure(
							new TkProfileDelete(source),
							source
						)
					),
					new FkRegex(
						"/access-right/save", 
						new TkSecure(
							new TkAccessRightSave(source),
							source
						)
					),
					new FkRegex(
						"/access-right/delete",
						new TkSecure(
							new TkAccessRightDelete(source),
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
							new TkEnterpriseLogoSave(source),
							source
						)
					)
			)
		);
	}
}
