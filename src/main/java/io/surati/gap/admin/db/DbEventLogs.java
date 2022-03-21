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

import io.surati.gap.admin.jooq.generated.tables.EventLog;
import io.surati.gap.admin.api.EventLogs;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

/**
 * Log events from database.
 *
 * @since 0.1
 */
public final class DbEventLogs implements EventLogs {

	/**
	 * Table of log events.
	 */
	private static final EventLog EVENT_LOG =
		EventLog.EVENT_LOG;

	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	/**
	 * Data source.
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source Data source.
	 */
	public DbEventLogs(final DataSource source) {
		this.source = source;
		this.ctx = DSL.using(new DefaultConfiguration().set(this.source));
	}
	
	@Override
	public io.surati.gap.admin.api.EventLog get(final Long id) {
		if (this.ctx.fetchCount(EVENT_LOG) == 0) {
			throw new IllegalArgumentException(
				String.format("Event log with ID %s not found !", id)
			);
		}
		return new DbEventLog(source, id);
	}

	@Override
	public Iterable<io.surati.gap.admin.api.EventLog> iterate() {
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
	public Long count() {
		return Long.valueOf(
			this.ctx.fetchCount(EVENT_LOG)
		);
	}

}
