/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.module.jooq.generated.tables;

import io.surati.gap.admin.module.jooq.generated.Keys;
import io.surati.gap.admin.module.jooq.generated.Public;
import io.surati.gap.admin.module.jooq.generated.tables.records.AccessProfileRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 * @since 0.1
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccessProfile extends TableImpl<AccessProfileRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.access_profile</code>
     */
    public static final AccessProfile ACCESS_PROFILE = new AccessProfile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccessProfileRecord> getRecordType() {
        return AccessProfileRecord.class;
    }

    /**
     * The column <code>public.access_profile.access_id</code>.
     */
    public final TableField<AccessProfileRecord, String> ACCESS_ID = createField(DSL.name("access_id"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>public.access_profile.profile_id</code>.
     */
    public final TableField<AccessProfileRecord, Long> PROFILE_ID = createField(DSL.name("profile_id"), SQLDataType.BIGINT.nullable(false), this, "");

    private AccessProfile(Name alias, Table<AccessProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private AccessProfile(Name alias, Table<AccessProfileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.access_profile</code> table reference
     */
    public AccessProfile(String alias) {
        this(DSL.name(alias), ACCESS_PROFILE);
    }

    /**
     * Create an aliased <code>public.access_profile</code> table reference
     */
    public AccessProfile(Name alias) {
        this(alias, ACCESS_PROFILE);
    }

    /**
     * Create a <code>public.access_profile</code> table reference
     */
    public AccessProfile() {
        this(DSL.name("access_profile"), null);
    }

    public <O extends Record> AccessProfile(Table<O> child, ForeignKey<O, AccessProfileRecord> key) {
        super(child, key, ACCESS_PROFILE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<AccessProfileRecord> getPrimaryKey() {
        return Keys.ACCESS_PROFILE_PKEY;
    }

    @Override
    public List<UniqueKey<AccessProfileRecord>> getKeys() {
        return Arrays.<UniqueKey<AccessProfileRecord>>asList(Keys.ACCESS_PROFILE_PKEY);
    }

    @Override
    public List<ForeignKey<AccessProfileRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AccessProfileRecord, ?>>asList(Keys.PROFILE_ACCESS_PROFILE_PROFILE_ID_FKEY);
    }

    private transient Profile _profile;

    public Profile profile() {
        if (_profile == null)
            _profile = new Profile(this, Keys.PROFILE_ACCESS_PROFILE_PROFILE_ID_FKEY);

        return _profile;
    }

    @Override
    public AccessProfile as(String alias) {
        return new AccessProfile(DSL.name(alias), this);
    }

    @Override
    public AccessProfile as(Name alias) {
        return new AccessProfile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AccessProfile rename(String name) {
        return new AccessProfile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AccessProfile rename(Name name) {
        return new AccessProfile(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
