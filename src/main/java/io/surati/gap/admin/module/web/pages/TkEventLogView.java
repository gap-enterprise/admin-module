package io.surati.gap.admin.module.web.pages;

/**
 * Take that view an event log.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
import io.surati.gap.admin.base.api.EventLog;
import io.surati.gap.admin.base.db.DbEventLogs;
import io.surati.gap.admin.module.web.xe.XeEventLog;
import io.surati.gap.admin.module.web.server.RsPage;
import org.cactoos.iterable.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import javax.sql.DataSource;

public final class TkEventLogView implements Take {

	/**
	 * DataSource
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkEventLogView(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
		final EventLog log = new DbEventLogs(this.source).get(id);
		final XeSource src = new XeEventLog(log);
		return new RsPage(
			"/io/surati/gap/admin/module/xsl/event_log/view.xsl",
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "event-log"),
				src
			)
		);
	}
}
