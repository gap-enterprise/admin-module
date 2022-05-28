package io.surati.gap.admin.module;

import io.surati.gap.admin.base.db.AdminDemoDatabase;
import io.surati.gap.admin.base.db.AdminProdDatabase;
import io.surati.gap.admin.module.web.server.FkActions;
import io.surati.gap.admin.module.web.server.FkApi;
import io.surati.gap.admin.module.web.server.FkPages;
import io.surati.gap.commons.utils.pf4j.DatabaseSetup;
import io.surati.gap.commons.utils.pf4j.ModuleRegistration;
import io.surati.gap.commons.utils.pf4j.WebFront;
import javax.sql.DataSource;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.takes.facets.auth.Pass;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.Fork;

public final class AdminPlugin extends Plugin {

    public AdminPlugin(final PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("Starting Administration plugin...");
    }

    @Override
    public void stop() {
        System.out.println("Stopping Administration plugin...");
    }

    @Override
    public void delete() {
        System.out.println("Deleting Administration plugin...");
    }

    @Extension
    public static final class AdminRegistration implements ModuleRegistration {

        @Override
        public void register() {
            AdminModule.setup();
        }
    }

    @Extension
    public static final class AdminDatabaseSetup implements DatabaseSetup {

        @Override
        public void migrate(final DataSource src, final boolean demo) {
            if (demo) {
                new AdminDemoDatabase(src);
            } else {
                new AdminProdDatabase(src);
            }
        }
    }

    @Extension
    public static final class AdminWebFront implements WebFront {

        @Override
        public Fork pages() {
            return new FkChain();
        }

        @Override
        public Fork pages(final DataSource src) {
            return new FkChain(
                new FkPages(src),
                new FkApi(src)
            );
        }

        @Override
        public Fork pages(final DataSource src, final Pass pass) {
            return new FkActions(src, pass);
        }
    }
}
