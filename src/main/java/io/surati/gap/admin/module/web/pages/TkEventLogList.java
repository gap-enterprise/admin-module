package io.surati.gap.admin.module.web.pages;

import io.surati.gap.admin.module.web.server.RsPage;
import org.cactoos.iterable.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;

import javax.sql.DataSource;

public final class TkEventLogList implements Take {
	
	private final DataSource source;
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkEventLogList(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		return new RsPage(
			"/xsl/event_log/list.xsl", 
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "event-log")
			)
		);
	}
}
