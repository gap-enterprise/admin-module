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
package io.surati.gap.admin.module.web.xe;

/**
 * Xml user
 * @since 0.1
 */
import io.surati.gap.admin.base.api.User;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeUser extends XeWrap {

	public XeUser(final User user) {
		this("item", user);
	}
	
	public XeUser(final String name, final User user) {
		super(
			new XeDirectives(
				new Directives()
				.add(name)
					.add("id").set(user.id()).up()
					.add("login").set(user.login()).up()
					.add("name").set(user.name()).up()
					.add("password").set(user.password()).up()
					.add("blocked").set(user.blocked()).up()
					.add("profile").set(user.profile().name()).up()
					.add("profile_id").set(user.profile().id()).up()
					.add("photo").set(user.photoBase64()).up()
				.up()
			)
		);
	}
}
