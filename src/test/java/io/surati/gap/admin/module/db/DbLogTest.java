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
