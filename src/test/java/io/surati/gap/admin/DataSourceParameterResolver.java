package io.surati.gap.admin;

import java.util.Optional;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.engine.execution.BeforeEachMethodAdapter;
import org.junit.jupiter.engine.extension.ExtensionRegistry;

/**
 * Data source parameter resolver.
 *
 * @since 0.1
 */
public final class DataSourceParameterResolver
    implements BeforeEachMethodAdapter, ParameterResolver {

    /**
     * Data source extension resolver.
     */
    private ParameterResolver resolver = null;

    @Override
    public void invokeBeforeEachMethod(ExtensionContext context, ExtensionRegistry registry) throws Throwable {
        Optional<ParameterResolver> resolverOptional = registry.getExtensions(ParameterResolver.class)
            .stream()
            .filter(parameterResolver ->
                parameterResolver.getClass().getName()
                    .contains("Resolver")
            )
            .findFirst();
        if (!resolverOptional.isPresent()) {
            throw new IllegalStateException(
                "Resolver missed in the registry. Probably it's not a TestTemplate Test");
        } else {
            this.resolver = resolverOptional.get();
        }
    }

    @Override
    public boolean supportsParameter(
        ParameterContext parameterContext, ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return this.resolver.supportsParameter(parameterContext, extensionContext);
    }

    @Override
    public Object resolveParameter(
        ParameterContext parameterContext, ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return this.resolver.resolveParameter(parameterContext, extensionContext);
    }
}
