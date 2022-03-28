package io.surati.gap.admin.module.secure;

import io.surati.gap.admin.base.api.Access;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link AdminAccess}.
 *
 * @since 0.4
 */
final class AdminAccessTest {

    @Test
    void containsRight() {
        MatcherAssert.assertThat(
            Access.VALUES,
            Matchers.hasItem(AdminAccess.CONFIGURER_UTILISATEURS)
        );
    }
}
