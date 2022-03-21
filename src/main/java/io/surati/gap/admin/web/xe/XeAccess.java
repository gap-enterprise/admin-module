package io.surati.gap.admin.web.xe;

/**
 * XeAccess
 * @since 0.1
 */
import io.surati.gap.admin.api.Access;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public class XeAccess extends XeWrap {

	public XeAccess(final Access access) {
		this("item", access);
	}
	
	public XeAccess(final String name, final Access access) {
		super(
			new XeDirectives(
				new Directives()
				.add(name)
					.add("id").set(access.name()).up()
					.add("name").set(access.title()).up()
				.up()
			)
		);
	}
}
