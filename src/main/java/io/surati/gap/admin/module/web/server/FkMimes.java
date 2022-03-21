package io.surati.gap.admin.module.web.server;

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
					"/css/.+", 
					new TkWithType(new TkClasspath(), "text/css")
				),
				new FkRegex(
					"/js/.+", 
					new TkWithType(new TkClasspath(), "application/javascript")
				),
				new FkRegex(
					"/img/.+\\.svg", 
					new TkWithType(new TkClasspath(), "image/svg+xml")
				),
				new FkRegex(
					"/img/.+\\.png", 
					new TkWithType(new TkClasspath(), "image/png")
				),
				new FkRegex(
					"/img/.+\\.jpg", 
					new TkWithType(new TkClasspath(), "image/jpeg")
				),
				new FkRegex(
					"/img/.+\\.gif", 
					new TkWithType(new TkClasspath(), "image/gif")
				),
				new FkRegex(
					"/img/.+\\.eot", 
					new TkWithType(new TkClasspath(), "application/vnd.ms-fontobject")
				),
				new FkRegex(
					"/img/.+\\.ttf", 
					new TkWithType(new TkClasspath(), "font/ttf")
				),
				new FkRegex(
					"/img/.+\\.woff", 
					new TkWithType(new TkClasspath(), "font/woff")
				),
				new FkRegex(
					"/img/.+\\.woff2", 
					new TkWithType(new TkClasspath(), "font/woff2")
				),
				new FkRegex(
					"/img/.+\\.ico", 
					new TkWithType(new TkClasspath(), "image/x-icon")
				),
				new FkRegex(
					"/csv/.+\\.csv",
					new TkWithType(new TkClasspath(), "text/csv")
	            ),
				new FkRegex(
					"/xls/.+\\.xls",
					new TkWithType(new TkClasspath(), "application/vnd.ms-excel")
	            ),
				new FkRegex(
					"/xls/.+\\.xlsx",
					new TkWithType(new TkClasspath(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	            ),
				new FkRegex(
					"/images/.+",
					new TkFiles(".")
	            )
			)
		);
	}
}
