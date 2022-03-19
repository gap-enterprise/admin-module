package io.surati.gap.admin.module.db;

import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import com.lightweight.db.LiquibaseDataSource;
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
     * Data source.
     */
    private DataSource source;

    @BeforeEach
    void setup() {
        this.source = new LiquibaseDataSource(
            new EmbeddedPostgreSQLDataSource(),
            "liquibase/db-admin.changelog-master.xml"
        );
    }

    @Test
    void addInfoEvent() {
        final String message = "I'm connected.";
        new DbLog(
            this.source,
            DbLogTest.AUTHOR,
            DbLogTest.IP_ADDRESS
        ).info(message);
        MatcherAssert.assertThat(
            new DbEventLogs(this.source).get(1L),
            new Satisfies<>(
                evt -> evt.author().equals(DbLogTest.AUTHOR) &&
                    evt.ipAddress().equals(DbLogTest.IP_ADDRESS) &&
                    evt.message().equals(message) &&
                    evt.level() == Level.INFO
            )
        );
    }
}
