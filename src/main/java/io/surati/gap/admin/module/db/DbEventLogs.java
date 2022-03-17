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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DbEventLogs implements EventLogs {
	
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbEventLogs(final DataSource source) {
		this.source = source;
	}
	
	@Override
	public EventLog get(Long id) {	
		try(
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM event_log WHERE id=?");
			){
				pstmt.setLong(1, id);
			
				try(final ResultSet rs = pstmt.executeQuery()){
					if(rs.next()) {
						return new DbEventLog(source, id);
					} else {
						throw new IllegalArgumentException(String.format("Event log with ID %s not found !", id));
					}			
				}
			} catch(SQLException e) {
				throw new DatabaseException(e);
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
	public Long count() {
		try {
			return
				new JdbcSession(this.source)
					.sql("SELECT COUNT(*) FROM event_log")
					.select(new SingleOutcome<>(Long.class));
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

}
