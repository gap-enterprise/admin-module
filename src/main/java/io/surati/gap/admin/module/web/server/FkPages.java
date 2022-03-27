package io.surati.gap.admin.module.web.server;

import com.minlessika.db.Database;
import io.surati.gap.admin.module.web.pages.TkIndex;
import io.surati.gap.admin.module.web.pages.TkLogin;
import io.surati.gap.admin.module.web.pages.TkProfileList;
import io.surati.gap.admin.module.web.pages.TkUserAvatarEdit;
import io.surati.gap.admin.module.web.pages.TkUserEdit;
import io.surati.gap.admin.module.web.pages.TkUserList;
import io.surati.gap.admin.module.web.pages.TkUserProfileEditPassword;
import io.surati.gap.admin.module.web.pages.TkAccessRightAdd;
import io.surati.gap.admin.module.web.pages.TkEnterprise;
import io.surati.gap.admin.module.web.pages.TkEnterpriseEdit;
import io.surati.gap.admin.module.web.pages.TkEnterpriseLogoEdit;
import io.surati.gap.admin.module.web.pages.TkEventLogList;
import io.surati.gap.admin.module.web.pages.TkEventLogView;
import io.surati.gap.admin.module.web.pages.TkProfileEdit;
import io.surati.gap.admin.module.web.pages.TkProfileView;
import io.surati.gap.admin.module.web.pages.TkUserEditPassword;
import io.surati.gap.admin.module.web.pages.TkUserProfile;
import io.surati.gap.admin.module.web.pages.TkUserView;
import io.surati.gap.web.base.TkAnonymous;
import io.surati.gap.web.base.TkDashboardBlank;
import io.surati.gap.web.base.TkHome;
import io.surati.gap.web.base.TkSecure;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for pages.
 *
 * @since 0.1
 */
public final class FkPages extends FkWrap {

	public FkPages(
		final Database source
	) {
		super(
			new FkChain(
				new FkRegex(
					"/", 
					new TkAnonymous(new TkIndex())
				),
				new FkRegex(
					"/login", 
					new TkAnonymous(new TkLogin(source))
				),
				new FkRegex(
					"/home", 
					new TkSecure(
						new TkHome(source),
						source
					)
				),
				new FkRegex(
					"/dashboard/blank", 
					new TkSecure(
						new TkDashboardBlank(source),
						source
					)
				),
				new FkRegex(
					"/user-profile", 
					new TkSecure(
						new TkUserProfile(source),
						source
					)
				),
				new FkRegex(
					"/user-profile/change-password",
					new TkSecure(
						new TkUserProfileEditPassword(source),
						source
					)
				),
				new FkRegex(
					"/user", 
					new TkSecure(
						new TkUserList(source),
						source
					)
				),
				new FkRegex(
					"/user/view", 
					new TkSecure(
						new TkUserView(source),
						source
					)
				),
				new FkRegex(
					"/user/edit", 
					new TkSecure(
						new TkUserEdit(source),
						source
					)
				),
				new FkRegex(
					"/user/password", 
					new TkSecure(
						new TkUserEditPassword(source),
						source
					)
				),
				new FkRegex(
					"/profile", 
					new TkSecure(
						new TkProfileList(source),
						source
					)
				),
				new FkRegex(
					"/profile/view", 
					new TkSecure(
						new TkProfileView(source),
						source
					)
				),
				new FkRegex(
					"/profile/edit", 
					new TkSecure(
						new TkProfileEdit(source),
						source
					)
				),
				new FkRegex(
					"/access-right/add", 
					new TkSecure(
						new TkAccessRightAdd(source),
						source
					)
				),
				new FkRegex(
					"/enterprise", 
					new TkSecure(
						new TkEnterprise(source),
						source
					)
				),
				new FkRegex(
					"/enterprise/edit", 
					new TkSecure(
						new TkEnterpriseEdit(source),
						source
					)
				),
				new FkRegex(
					"/event-log", 
					new TkSecure(
						new TkEventLogList(source),
						source
					)
				),
				new FkRegex(
					"/event-log/view", 
					new TkSecure(
						new TkEventLogView(source),
						source
					)
				),
				new FkRegex(
					"/user/avatar/edit", 
					new TkSecure(
						new TkUserAvatarEdit(source),
						source
					)
				),
				new FkRegex(
					"/enterprise/logo/edit",
					new TkSecure(
						new TkEnterpriseLogoEdit(source),
						source
					)
				)
			)
		);
	}
}
