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
package io.surati.gap.admin.module.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.module.api.EventLog;
import io.surati.gap.admin.module.exceptions.DatabaseException;
import org.cactoos.text.Joined;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;

public final class DbEventLog implements EventLog {

	private final DataSource source;
	private final Long id;
	
	public DbEventLog(final DataSource source, final Long id) {
		this.source = source;
		this.id = id;
	}
	
	@Override
	public Long id() {
		return this.id;
	}

	@Override
	public LocalDateTime date() {
		try (
			final Connection connection = this.source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement(
				new Joined(
    				" ",
    				"SELECT date FROM event_log",
    				"WHERE id=?"
    			).toString()
			)
		){
			pstmt.setLong(1, this.id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getTimestamp(1).toLocalDateTime();
			}
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public Level level() {
		try {
			return Level.parse(
				new JdbcSession(this.source)
					.sql(
		        		new Joined(
	        				" ",
	        				"SELECT level_id FROM event_log",
	        				"WHERE id=?"
	        			).toString()
	        		)
					.set(this.id)
		            .select(new SingleOutcome<>(String.class))
			);
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public String message() {
		try {
			return new JdbcSession(this.source)
				.sql(
	        		new Joined(
        				" ",
        				"SELECT message FROM event_log",
        				"WHERE id=?"
        			).toString()
        		)
				.set(this.id)
	            .select(new SingleOutcome<>(String.class));
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public String details() {
		try {
			return new JdbcSession(this.source)
				.sql(
	        		new Joined(
        				" ",
        				"SELECT details FROM event_log",
        				"WHERE id=?"
        			).toString()
        		)
				.set(this.id)
	            .select(new SingleOutcome<>(String.class));
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public String author() {
		try {
			return new JdbcSession(this.source)
				.sql(
	        		new Joined(
        				" ",
        				"SELECT author FROM event_log",
        				"WHERE id=?"
        			).toString()
        		)
				.set(this.id)
	            .select(new SingleOutcome<>(String.class));
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public String ipAddress() {
		try {
			return new JdbcSession(this.source)
				.sql(
	        		new Joined(
        				" ",
        				"SELECT ip_address FROM event_log",
        				"WHERE id=?"
        			).toString()
        		)
				.set(this.id)
	            .select(new SingleOutcome<>(String.class));
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

}
