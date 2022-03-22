/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
	
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.db;

import io.surati.gap.admin.api.EventLog;
import io.surati.gap.admin.api.Log;
import io.surati.gap.admin.rq.RqUser;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import org.cactoos.iterable.IterableOf;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.takes.Request;

/**
 * Log that stores events in database.
 *
 * @since 0.1
 */
public final class DbLog implements Log {

	/**
	 * Table of log events.
	 */
	private static final io.surati.gap.admin.jooq.generated.tables.EventLog EVENT_LOG =
		io.surati.gap.admin.jooq.generated.tables.EventLog.EVENT_LOG;

	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	/**
	 * Data source.
	 */
	private final DataSource source;

	/**
	 * IP address.
	 */
	private final String ipaddress;

	/**
	 * Author.
	 */
	private final String author;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param req Request
	 */
	public DbLog(final DataSource source, final Request req) {
		this(source, loginFrom(source, req), ipAddressFrom(req));
	}

	/**
	 * Ctor.
	 * @param source Data source
	 * @param author Author
	 * @param ipaddress IP address
	 */
	public DbLog(final DataSource source, final String author, final String ipaddress) {
		this.source = source;
		this.ctx = DSL.using(new DefaultConfiguration().set(this.source));
		this.author = author;
		this.ipaddress = ipaddress;
	}

	/**
	 * IP address from request.
	 * @param req Request
	 * @return IP address
	 */
	private static String ipAddressFrom(Request req) {
		try {
			for (String head : req.head()) {
				if(head.contains("X-Takes-RemoteAddress")) {
					return head.split(":")[1].trim();
				}
			}
			return "127.0.0.1";
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Login from request.
	 * @param source Data source
	 * @param req Request
	 * @return Login
	 */
	private static String loginFrom(final DataSource source, final Request req) {
		try {
			return new RqUser(source, req).login();
		} catch (Exception e) {
			return "anonymous";
		}
	}

	@Override
	public void info(String message, Object... args) {
		this.log(Level.INFO, String.format(message, args));
	}

	@Override
	public void warning(String message, Object... args) {
		this.log(Level.WARNING, String.format(message, args));
	}

	@Override
	public void error(String message, String details) {
		this.log(Level.SEVERE, message, details);
	}

	/**
	 * Log with level.
	 * @param level Level
	 * @param message Message
	 */
	private void log(Level level, String message) {
		this.log(level, message, null);
	}

	/**
	 * Log with level and details.
	 * @param level Level
	 * @param message Message
	 * @param details Details
	 */
	private void log(Level level, String message, String details) {
		this.ctx.insertInto(EVENT_LOG)
			.set(EVENT_LOG.DATE, LocalDateTime.now())
			.set(EVENT_LOG.LEVEL_ID, level.getName())
			.set(EVENT_LOG.MESSAGE, message)
			.set(EVENT_LOG.DETAILS, details)
			.set(EVENT_LOG.AUTHOR, this.author)
			.set(EVENT_LOG.IP_ADDRESS, this.ipaddress)
			.execute();
	}

	@Override
	public Iterable<EventLog> iterate() {
		return this.ctx
			.selectFrom(EVENT_LOG)
			.orderBy(EVENT_LOG.ID.desc())
			.fetch(
				rec -> new DbEventLog(
					this.source, rec.getId()
				)
			);
	}

	@Override
	public void info(String message) {
		this.info(message, new IterableOf<>());
	}

	@Override
	public void warning(String message) {
		this.warning(message, new IterableOf<>());
	}

}
