package io.surati.gap.admin.web.pages;

/**
 * Take that edits an enterprise logo.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
import io.surati.gap.web.base.RsPage;
import org.cactoos.iterable.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;

import javax.sql.DataSource;

public final class TkEnterpriseLogoEdit implements Take {

	/**
	 * DataSource
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 */
	public TkEnterpriseLogoEdit(final DataSource source) {
		this.source = source;
	}

	@Override
	public Response act(Request req) throws Exception {
		return new RsPage(
			"/io/surati/gap/admin/xsl/enterprise/logo_edit.xsl",
			req,
			this.source,
			() -> new Sticky<>(
				new XeAppend("menu", "enterprise")
			)
		);
	}
}
