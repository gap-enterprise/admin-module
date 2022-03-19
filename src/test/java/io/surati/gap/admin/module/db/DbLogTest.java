package io.surati.gap.admin.module.db;

import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import com.lightweight.db.LiquibaseDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

final class DbEventLogsTest {

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
    void addEvent() {
        new DbEventLogs(this.source).
    }
}
