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

import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import com.lightweight.db.LiquibaseDataSource;
import io.surati.gap.admin.module.api.EventLog;
import io.surati.gap.admin.module.api.EventLogs;
import io.surati.gap.admin.module.api.Log;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Satisfies;

final class DbLogTest {

    /**
     * Author.
     */
    private static final String AUTHOR = "admin";

    /**
     * IP address.
     */
    private static final String IP_ADDRESS = "127.0.0.1";

    /**
     * Log.
     */
    private Log log;

    /**
     * Log events.
     */
    private EventLogs events;

    @BeforeEach
    void setup() {
    	final DataSource source = new LiquibaseDataSource(
            new EmbeddedPostgreSQLDataSource(),
            "liquibase/db-admin.changelog-master.xml"
        );
    	this.log = new DbLog(
            source,
            DbLogTest.AUTHOR,
            DbLogTest.IP_ADDRESS
        );
    	this.events = new DbEventLogs(source);
    }

    @Test
    void addInfoEvent() {
        final String message = "I'm connected.";
        this.log.info(message);
        MatcherAssert.assertThat(
            this.events.get(1L),
            new Satisfies<>(
                evt -> evt.author().equals(DbLogTest.AUTHOR) &&
                    evt.ipAddress().equals(DbLogTest.IP_ADDRESS) &&
                    evt.message().equals(message) &&
                    evt.level() == Level.INFO
            )
        );
    }
    
    @Test
    void addErrorEvent() {
        final String message = "HTTP Error 500";
        final String details = "HTTP Error 500 (Internal Server Error).";
        log.error(message, details);
        MatcherAssert.assertThat(
            events.get(1L),
            new Satisfies<>(
                evt -> evt.author().equals(DbLogTest.AUTHOR) &&
                    evt.ipAddress().equals(DbLogTest.IP_ADDRESS) &&
                    evt.message().equals(message) &&
                    evt.details().equals(details) &&
                    evt.level() == Level.SEVERE
            )
        );
    }
    
    @Test
    void addWarningEvent() {
        final String message = "Ouh! The username is invalid.";
        log.warning(message);
        MatcherAssert.assertThat(
            events.get(1L),
            new Satisfies<>(
                evt -> evt.author().equals(DbLogTest.AUTHOR) &&
                    evt.ipAddress().equals(DbLogTest.IP_ADDRESS) &&
                    evt.message().equals(message) &&
                    evt.level() == Level.WARNING
            )
        );
    }
    
    @Test
    void iterate() {
        final String[] messages = { "Welcome admin.", "Something is wrong right now!" };
        final Level[] levels = { Level.INFO, Level.WARNING };
    	log.info(messages[0]);
    	log.warning(messages[1]);
        MatcherAssert.assertThat(
            "Log should have two events.",
            events.count(),
            new IsEqual<>(Long.valueOf(messages.length))
        );
        int idx = messages.length;
    	for (final EventLog event : log.iterate()) {
            idx -= 1;
            final String message = messages[idx];
            final Level level = levels[idx];
            MatcherAssert.assertThat(
                "Log events should match in descending order.",
                event,
                new Satisfies<>(
                    evt -> evt.author().equals(DbLogTest.AUTHOR) &&
                        evt.ipAddress().equals(DbLogTest.IP_ADDRESS) &&
                        evt.message().equals(message) &&
                        evt.level() == level
                )
            );
		}
    }
}
