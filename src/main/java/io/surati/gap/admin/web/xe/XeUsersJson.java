/**
MIT License

Copyright (c) 2021 Surati

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */ 
package io.surati.gap.admin.web.xe;

/**
 * Xml UsersJson
 * @since 0.1
 */
import io.surati.gap.admin.api.User;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.io.IOException;
import org.takes.rs.RsJson;

public final class XeUsersJson implements RsJson.Source {

	private final Long count;
	private final Iterable<User> items;
	
	public XeUsersJson(final Iterable<User> items, final Long count) {
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
	
	private static JsonArray toJson(final Iterable<User> items) throws IOException {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (User item : items) {
			builder.add(Json.createObjectBuilder()
				.add("id", item.id())
				.add("name", item.name())
				.add("login", item.login())
				.add("profile", item.profile().name())
                .add("profile_id", item.profile().id())
           );
		}
		return builder.build();
	}

}
