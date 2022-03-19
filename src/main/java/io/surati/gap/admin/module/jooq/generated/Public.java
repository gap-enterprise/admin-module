/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.module.jooq.generated;


import io.surati.gap.admin.module.jooq.generated.tables.AccessProfile;
import io.surati.gap.admin.module.jooq.generated.tables.AppUser;
import io.surati.gap.admin.module.jooq.generated.tables.EventLog;
import io.surati.gap.admin.module.jooq.generated.tables.Person;
import io.surati.gap.admin.module.jooq.generated.tables.Profile;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.access_profile</code>.
     */
    public final AccessProfile ACCESS_PROFILE = AccessProfile.ACCESS_PROFILE;

    /**
     * The table <code>public.app_user</code>.
     */
    public final AppUser APP_USER = AppUser.APP_USER;

    /**
     * The table <code>public.event_log</code>.
     */
    public final EventLog EVENT_LOG = EventLog.EVENT_LOG;

    /**
     * The table <code>public.person</code>.
     */
    public final Person PERSON = Person.PERSON;

    /**
     * The table <code>public.profile</code>.
     */
    public final Profile PROFILE = Profile.PROFILE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            AccessProfile.ACCESS_PROFILE,
            AppUser.APP_USER,
            EventLog.EVENT_LOG,
            Person.PERSON,
            Profile.PROFILE);
    }
}
