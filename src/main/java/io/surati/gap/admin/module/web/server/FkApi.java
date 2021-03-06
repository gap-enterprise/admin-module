package io.surati.gap.admin.module.web.server;

import io.surati.gap.admin.module.web.rest.TkEventLogSearch;
import io.surati.gap.admin.module.web.rest.TkUserSearch;
import io.surati.gap.web.base.TkSecure;
import javax.sql.DataSource;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;

/**
 * Front for API.
 *
 * @since 0.1
 */
public final class FkApi extends FkWrap {

	public FkApi(final DataSource source) {
		super(
			new FkChain(
				new FkRegex(
					"/user/search", 
					new TkSecure(
						new TkUserSearch(source),
						source
					)
				),
				new FkRegex(
					"/event-log/search", 
					new TkSecure(
						new TkEventLogSearch(source),
						source
					)
				)
			)
		);
	}
}
