package io.surati.gap.admin.module.web.rest;

import io.surati.gap.admin.module.api.EventLogs;
import io.surati.gap.admin.module.db.DbPaginedEventLogs;
import io.surati.gap.admin.module.web.xe.XeEventLogsJson;
import io.surati.gap.commons.utils.time.Period;
import io.surati.gap.commons.utils.time.SafePeriodText;
import javax.sql.DataSource;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsJson;

public final class TkEventLogSearch implements Take {

	/**
	 * Database
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkEventLogSearch(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {	
		final Smart href = new Smart(req);
		final String filter = href.single("filter", "");					
		final Long page = Long.parseLong(href.single("page"));
		final Long nbperpage = Long.parseLong(href.single("nbperpage"));
		final Period period = new SafePeriodText(
			href.single("begindate", ""),
			href.single("enddate", "")
		);
		final EventLogs logs = new DbPaginedEventLogs(
				this.source, 
				nbperpage, 
				page, 
				filter,
				period
		);
		return new RsJson(
			new XeEventLogsJson(logs.iterate(), logs.count())
		);
	}
}
