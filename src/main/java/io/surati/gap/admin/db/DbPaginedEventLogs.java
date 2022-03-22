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
import io.surati.gap.admin.api.EventLogs;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import io.surati.gap.commons.utils.time.Period;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

/**
 * Paginated log events coming from database.
 *
 * @since 0.1
 */
public final class DbPaginedEventLogs implements EventLogs {

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
	 * Data Source.
	 */
	private final DataSource source;

	/**
	 * Number of items per page.
	 */
	private final Long nbperpage;

	/**
	 * Page.
	 */
	private final Long page;

	/**
	 * Filter.
	 */
	private final String filter;

	/**
	 * Period.
	 */
	private final Period period;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param nbperpage Number of items per page
	 * @param page Current page
	 * @param filter Filter
	 * @param period Period
	 */
	public DbPaginedEventLogs(
		final DataSource source, final Long nbperpage, final Long page,
		final String filter, final Period period
	) {
		this.source = source;
		this.ctx = DSL.using(new DefaultConfiguration().set(this.source));
		this.nbperpage = nbperpage;
		this.page = page;
		this.filter = filter;
		this.period = period;
	}
	
	@Override
	public EventLog get(final Long id) {
		if (this.ctx.fetchCount(EVENT_LOG, this.condition()) == 0) {
			throw new IllegalArgumentException(
				String.format("Log event with ID %s not found !", id)
			);
		}
		return new DbEventLog(
			this.source,
			id
		);
	}

	@Override
	public Iterable<EventLog> iterate() {
		return this.ctx
			.selectFrom(EVENT_LOG)
			.where(this.condition())
			.orderBy(EVENT_LOG.ID.desc())
			.offset(this.nbperpage * (this.page - 1))
			.limit(this.nbperpage)
			.fetch(
				rec -> new DbEventLog(this.source, rec.getId())
			);
	}

	@Override
	public Long count() {
		return Long.valueOf(
			this.ctx
			.fetchCount(EVENT_LOG, this.condition())
		);
	}

	private Condition condition() {
		Condition result = DSL.trueCondition();
		if (StringUtils.isNotBlank(filter)) {
			result = result.and(
				DSL.falseCondition()
					.or(EVENT_LOG.MESSAGE.like("%" + this.filter + "%"))
					.or(EVENT_LOG.LEVEL_ID.like("%" + this.filter + "%"))
					.or(EVENT_LOG.AUTHOR.like("%" + this.filter + "%"))
					.or(EVENT_LOG.IP_ADDRESS.like("%" + this.filter + "%"))
			);
		}
		if (this.period.begin() != LocalDate.MIN) {
			result = result.and(
				EVENT_LOG.DATE.greaterOrEqual(
					LocalDateTime.of(this.period.begin(), LocalTime.MIDNIGHT)
				)
			);
		}
		if (this.period.end() != LocalDate.MAX) {
			result = result.and(
				EVENT_LOG.DATE.lessOrEqual(
					LocalDateTime.of(this.period.end(), LocalTime.of(23, 59))
				)
			);
		}
		return result;
	}
}
