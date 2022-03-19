package io.surati.gap.admin.module.db;

import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import com.lightweight.db.LiquibaseDataSource;

import io.surati.gap.admin.module.api.EventLog;
import io.surati.gap.admin.module.api.EventLogs;
import io.surati.gap.admin.module.api.Log;

import javax.sql.DataSource;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Satisfies;

import java.util.logging.Level;

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
     * Log log.
     */
    private Log log;
    private EventLogs events;

    @BeforeEach
    void setup() {
    	final DataSource source;
    	source = new LiquibaseDataSource(
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
        log.info(message);
        MatcherAssert.assertThat(
            events.get(1L),
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
    public void iterate() {
    	log.info("Welcome admin.");
    	log.warning("Something is wrong right now!");
    	final Iterable<EventLog> events = log.iterate();
    	for (EventLog event : events) {
            MatcherAssert.assertThat(
                    event,
                    new Satisfies<>(
                        evt -> evt.author().equals(DbLogTest.AUTHOR) &&
                            evt.ipAddress().equals(DbLogTest.IP_ADDRESS) &&
                            evt.message().equals(event.message()) &&
                            evt.level() == event.level()
                    )
                );
		}
    }
}
