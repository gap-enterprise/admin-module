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
import com.jcabi.jdbc.Outcome;
import io.surati.gap.admin.module.api.EventLog;
import io.surati.gap.admin.module.api.Log;
import io.surati.gap.admin.module.exceptions.DatabaseException;
import io.surati.gap.admin.module.rq.RqUser;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.Joined;
import org.takes.Request;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;

public final class DbLog implements Log {

	private final DataSource source;
	
	private final String ipaddress;
	
	private final String author;

	public DbLog(final DataSource source, final Request req) {
		this(source, loginFrom(source, req), ipAddressFrom(req));
	}

	public DbLog(final DataSource source, final String author, final String ipaddress) {
		this.source = source;
		this.author = author;
		this.ipaddress = ipaddress;
	}

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

	private void log(Level level, String message) {
		this.log(level, message, null);
	}

	private void log(Level level, String message, String details) {
		try {
			new JdbcSession(this.source)
	            .sql(
	                new Joined(
	                    " ",
	                    "INSERT INTO event_log",
	                    "(date, level_id, message, details, author, ip_address)",
	                    "VALUES",
	                    "(?, ?, ?, ?, ?, ?)"
	                ).toString()
	            )
	            .set(Timestamp.valueOf(LocalDateTime.now()))
	            .set(level.getName())
	            .set(message)
	            .set(details)
	            .set(this.author)
	            .set(this.ipaddress)
	            .insert(Outcome.VOID);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
	}

	@Override
	public Iterable<EventLog> iterate() {
		try {
            return 
                new JdbcSession(this.source)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT id FROM event_log",
            				"ORDER BY id DESC"
                        ).toString()
                    )
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
	public void info(String message) {
		this.info(message, new IterableOf<>());
	}

	@Override
	public void warning(String message) {
		this.warning(message, new IterableOf<>());
	}

}
