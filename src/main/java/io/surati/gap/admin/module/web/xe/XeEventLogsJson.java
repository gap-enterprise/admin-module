package io.surati.gap.admin.module.web.xe;

import io.surati.gap.admin.module.api.EventLog;
import io.surati.gap.commons.utils.convert.FrShortDateFormat;
import org.takes.rs.RsJson;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.io.IOException;

public final class XeEventLogsJson implements RsJson.Source {

	private final Long count;
	private final Iterable<EventLog> items;
	
	public XeEventLogsJson(final Iterable<EventLog> items, final Long count) {
		this.count = count;
		this.items = items;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return Json.createObjectBuilder()
		   .add("items", toJson(this.items))
           .add("count", this.count)
		   .build();
	}
	
	private static JsonArray toJson(final Iterable<EventLog> items) throws IOException {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (EventLog item : items) {
			builder.add(Json.createObjectBuilder()
				.add("id", item.id())
				.add("message", item.message())
                .add("details", item.details() == null ? "" : item.details())
                .add("level_id", item.level().toString())
                .add("author", item.author())
                .add("ip_address", item.ipAddress())
                .add("date", new FrShortDateFormat().convert(item.date()))
           );
		}
		return builder.build();
	}
}
