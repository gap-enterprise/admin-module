package io.surati.gap.admin.module.web.xe;

/**
 * XeEventLog
 * @since 0.1
 */
import com.minlessika.map.CleanMap;
import io.surati.gap.admin.module.api.EventLog;
import io.surati.gap.commons.utils.convert.FrShortDateFormat;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeEventLog extends XeWrap {

	public XeEventLog(final EventLog log) {
		this("item", log);
	}

	public XeEventLog(final String name, final EventLog log) {
		super(
			new XeDirectives(
				new Directives()
				.add(name)				
				.add(
					new CleanMap<>()
						.add("id", log.id())
						.add("date", new FrShortDateFormat().convert(log.date()))
						.add("details", log.details() == null ? null : log.details())
						.add("level_id", log.level())
						.add("message", log.message())
						.add("author", log.author())
						.add("ip_address", log.ipAddress())
				)
				.up()
			)
		);
	}
}