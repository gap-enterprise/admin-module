package io.surati.gap.admin.web.server;

import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkFiles;
import org.takes.tk.TkWithType;

/**
 * Front for mime types.
 *
 * @since 0.1
 */
public final class FkMimes extends FkWrap {

	public FkMimes() {
		super(
			new FkChain(
				new FkRegex(
					"/io/.+/css/.+",
					new TkWithType(new TkClasspath(), "text/css")
				),
				new FkRegex(
					"/io/.+/js/.+",
					new TkWithType(new TkClasspath(), "application/javascript")
				),
				new FkRegex(
					"/io/.+/img/.+\\.svg",
					new TkWithType(new TkClasspath(), "image/svg+xml")
				),
				new FkRegex(
					"/io/.+/img/.+\\.png",
					new TkWithType(new TkClasspath(), "image/png")
				),
				new FkRegex(
					"/io/.+/img/.+\\.jpg",
					new TkWithType(new TkClasspath(), "image/jpeg")
				),
				new FkRegex(
					"/io/.+/img/.+\\.gif",
					new TkWithType(new TkClasspath(), "image/gif")
				),
				new FkRegex(
					"/io/.+/img/.+\\.eot",
					new TkWithType(new TkClasspath(), "application/vnd.ms-fontobject")
				),
				new FkRegex(
					"/io/.+/img/.+\\.ttf",
					new TkWithType(new TkClasspath(), "font/ttf")
				),
				new FkRegex(
					"/io/.+/img/.+\\.woff",
					new TkWithType(new TkClasspath(), "font/woff")
				),
				new FkRegex(
					"/io/.+/img/.+\\.woff2",
					new TkWithType(new TkClasspath(), "font/woff2")
				),
				new FkRegex(
					"/io/.+/img/.+\\.ico",
					new TkWithType(new TkClasspath(), "image/x-icon")
				),
				new FkRegex(
					"/io/.+/csv/.+\\.csv",
					new TkWithType(new TkClasspath(), "text/csv")
	            ),
				new FkRegex(
					"/io/.+/xls/.+\\.xls",
					new TkWithType(new TkClasspath(), "application/vnd.ms-excel")
	            ),
				new FkRegex(
					"/io/.+/xls/.+\\.xlsx",
					new TkWithType(new TkClasspath(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	            ),
				new FkRegex(
					"/io/.+/images/.+",
					new TkFiles(".")
	            )
			)
		);
	}
}
