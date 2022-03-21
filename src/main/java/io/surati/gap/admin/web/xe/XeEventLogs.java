package io.surati.gap.admin.web.xe;

/**
 * XeEventLogs
 * @since 0.1
 */
import io.surati.gap.admin.api.EventLog;
import io.surati.gap.admin.api.EventLogs;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeEventLogs extends XeWrap {

	public XeEventLogs(final EventLogs logs) {
		this(logs.iterate());
	}
	
	public XeEventLogs(final Iterable<EventLog> items) {
		super(
			new XeDirectives(
				new Directives()
					.add("event_logs")
					.append(
						new Joined<>(
							new Mapped<>(
								item -> new XeEventLog("event_log", item).toXembly(),
								items
							)
						)
					)
			)
		);
	}
}
