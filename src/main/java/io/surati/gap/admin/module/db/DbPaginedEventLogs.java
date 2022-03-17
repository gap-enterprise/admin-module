/*
 * MIT License
 *
 * Copyright (c) 2022 Surati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.module.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.module.api.EventLog;
import io.surati.gap.admin.module.api.EventLogs;
import io.surati.gap.admin.module.exceptions.DatabaseException;
import org.cactoos.text.Joined;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;

public final class DbPaginedEventLogs implements EventLogs {

	/**
	 * Origin.
	 */
	private final EventLogs origin;
	
	/**
	 * Data Source.
	 */
	private final DataSource source;
	
	private final Long nbperpage;
	
	private final Long page;
	
	private final String filter;
	
	private final LocalDate begindate;
	
	private final LocalDate enddate;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbPaginedEventLogs(final DataSource source, final Long nbperpage, final Long page, final String filter, final LocalDate begindate, final LocalDate enddate) {
		this.origin = new DbEventLogs(source);
		this.source = source;
		this.nbperpage = nbperpage;
		this.page = page;
		this.filter = filter;
		this.begindate = begindate;
		this.enddate = enddate;
	}
	
	@Override
	public EventLog get(Long id) {
		return this.origin.get(id);
	}

	@Override
	public Iterable<EventLog> iterate() {
		try {
            return 
                new JdbcSession(this.source)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT ev.id FROM event_log as ev",
	                        "WHERE (ev.message ILIKE ? OR ev.level_id ILIKE ? OR ev.author ILIKE ? OR ev.ip_address ILIKE ?)",
	                        "AND (to_char(?::date, 'YYYY-MM-DD') = '1970-01-01' OR date_trunc('day', ev.date)::date >= ?)",
	                        "AND (to_char(?::date, 'YYYY-MM-DD') = '1970-01-01' OR date_trunc('day', ev.date)::date <= ?)",
	                        "ORDER BY ev.id DESC",
            				"LIMIT ? OFFSET ?"
                        ).toString()
                    )
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set(java.sql.Date.valueOf(this.begindate))
                    .set(java.sql.Date.valueOf(this.begindate))
                    .set(java.sql.Date.valueOf(this.enddate))
                    .set(java.sql.Date.valueOf(this.enddate))
                    .set(this.nbperpage)
                    .set(this.nbperpage * (this.page - 1))
                    .select(
                        new ListOutcome<>(
                            rset ->
                            new DbEventLog(
                                this.source,
                                rset.getLong(1)
                            )
                        )
                    );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
	}

	@Override
	public Long count() {
		try {
			return
				new JdbcSession(this.source)
					.sql(
	                    new Joined(
	                        " ",
	                        "SELECT COUNT(ev.*) FROM event_log as ev",
	                        "WHERE (ev.message ILIKE ? OR ev.level_id ILIKE ? OR ev.author ILIKE ? OR ev.ip_address ILIKE ?)",
	                        "AND (to_char(?::date, 'YYYY-MM-DD') = '1970-01-01' OR date_trunc('day', ev.date)::date >= ?)",
	                        "AND (to_char(?::date, 'YYYY-MM-DD') = '1970-01-01' OR date_trunc('day', ev.date)::date <= ?)"
	                    ).toString()
	                )
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set("%" + this.filter + "%")
                    .set(java.sql.Date.valueOf(this.begindate))
                    .set(java.sql.Date.valueOf(this.begindate))
                    .set(java.sql.Date.valueOf(this.enddate))
                    .set(java.sql.Date.valueOf(this.enddate))
					.select(new SingleOutcome<>(Long.class));
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

}
