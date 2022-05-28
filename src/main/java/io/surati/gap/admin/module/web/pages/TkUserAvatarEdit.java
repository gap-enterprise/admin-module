package io.surati.gap.admin.module.web.pages;

/**
 * Take that edits an user avatar.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
import io.surati.gap.admin.base.api.User;
import io.surati.gap.admin.base.db.DbUsers;
import io.surati.gap.admin.module.web.xe.XeUser;
import io.surati.gap.admin.module.web.server.RsPage;
import io.surati.gap.web.base.xe.XeRootPage;
import org.cactoos.iterable.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import javax.sql.DataSource;

public final class TkUserAvatarEdit implements Take {

	/**
	 * DataSource
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkUserAvatarEdit(final DataSource source) {
		this.source = source;
	}

	@Override
	public Response act(Request req) throws Exception {
		final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
		final User user = new DbUsers(this.source).get(id);
		final XeSource src = new XeUser(user);
		return new RsPage(
			"/io/surati/gap/admin/module/xsl/user/avatar_edit.xsl",
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "user"),
				new XeRootPage(req),
				src
			)
		);
	}
}
