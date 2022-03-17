package io.surati.gap.admin.module.sample;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

final class SampleTest {

    @Test
    void test() {
        MatcherAssert.assertThat(
            "Ok".length(),
            Matchers.equalTo(2)
        );
    }
}
