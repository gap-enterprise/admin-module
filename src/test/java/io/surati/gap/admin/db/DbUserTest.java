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

import io.surati.gap.admin.DataSourceExtension;
import io.surati.gap.admin.DataSourceParameterResolver;
import io.surati.gap.admin.api.User;
import javax.sql.DataSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test case for {@link DbUser}.
 *
 * @since 0.1
 */
@ExtendWith(DataSourceExtension.class)
@ExtendWith(DataSourceParameterResolver.class)
final class DbUserTest {

    /**
     * User to test.
     */
    private User user;

    @BeforeEach
    void setup(final DataSource source) {
        this.user = new DbUsers(source).register(
            "Olivier B. OURA", "baudoliver7", "gap"
        );
    }

    @TestTemplate
    void blocksUser() {
        this.user.block(true);
        MatcherAssert.assertThat(
            "User should be blocked.",
            this.user.blocked(),
            Matchers.is(true)
        );
        this.user.block(false);
        MatcherAssert.assertThat(
            "User should be unblocked.",
            this.user.blocked(),
            Matchers.is(false)
        );
    }
}
