package io.surati.gap.admin;

import com.lightweight.db.EmbeddedOracleDataSource;
import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import com.lightweight.db.LiquibaseDataSource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

/**
 * JUnit5 test template extension running test methods with all data source types.
 *
 * @since 0.1
 */
public final class DataSourceExtension
    implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod().isPresent();
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return new LinkedList<>(
            Arrays.asList(
                new LiquibaseDataSource(
                    new EmbeddedPostgreSQLDataSource(),
                    "liquibase/db-admin.changelog-master.xml"
                ),
                new LiquibaseDataSource(
                    new EmbeddedOracleDataSource(),
                    "liquibase/db-admin.changelog-master.xml"
                )
            )
        ).stream().map(DataSourceContext::new);
    }

    /**
     * Test template context with bound data source.
     *
     * @since 0.1
     */
    private static class DataSourceContext implements TestTemplateInvocationContext {

        /**
         * Data source bound
         */
        private final DataSource src;

        /**
         * Ctor.
         * @param src Data source to bind
         */
        DataSourceContext(final DataSource src) {
            this.src = src;
        }

        @Override
        public String getDisplayName(final int index) {
            return String.format("[%s]", this.src.getClass().getSimpleName());
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return Collections.singletonList(new Resolver());
        }

        /**
         * Resolver for {@link DataSource} parameter.
         *
         * @since 0.1
         */
        private class Resolver implements ParameterResolver {

            @Override
            public boolean supportsParameter(
                final ParameterContext parameter,
                final ExtensionContext extension) throws ParameterResolutionException {
                return parameter.getParameter().getType().equals(DataSource.class);
            }

            @Override
            public Object resolveParameter(
                final ParameterContext parameter,
                final ExtensionContext extension) throws ParameterResolutionException {
                return DataSourceContext.this.src;
            }
        }
    }
}
