/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.module.jooq.generated.tables;


import io.surati.gap.admin.module.jooq.generated.Keys;
import io.surati.gap.admin.module.jooq.generated.Public;
import io.surati.gap.admin.module.jooq.generated.tables.records.EventLogRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventLog extends TableImpl<EventLogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.event_log</code>
     */
    public static final EventLog EVENT_LOG = new EventLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EventLogRecord> getRecordType() {
        return EventLogRecord.class;
    }

    /**
     * The column <code>public.event_log.id</code>.
     */
    public final TableField<EventLogRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.event_log.date</code>.
     */
    public final TableField<EventLogRecord, LocalDateTime> DATE = createField(DSL.name("date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.event_log.message</code>.
     */
    public final TableField<EventLogRecord, String> MESSAGE = createField(DSL.name("message"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.event_log.details</code>.
     */
    public final TableField<EventLogRecord, String> DETAILS = createField(DSL.name("details"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.event_log.level_id</code>.
     */
    public final TableField<EventLogRecord, String> LEVEL_ID = createField(DSL.name("level_id"), SQLDataType.VARCHAR(15).nullable(false), this, "");

    /**
     * The column <code>public.event_log.author</code>.
     */
    public final TableField<EventLogRecord, String> AUTHOR = createField(DSL.name("author"), SQLDataType.VARCHAR(25).nullable(false), this, "");

    /**
     * The column <code>public.event_log.ip_address</code>.
     */
    public final TableField<EventLogRecord, String> IP_ADDRESS = createField(DSL.name("ip_address"), SQLDataType.VARCHAR(15).nullable(false), this, "");

    private EventLog(Name alias, Table<EventLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private EventLog(Name alias, Table<EventLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.event_log</code> table reference
     */
    public EventLog(String alias) {
        this(DSL.name(alias), EVENT_LOG);
    }

    /**
     * Create an aliased <code>public.event_log</code> table reference
     */
    public EventLog(Name alias) {
        this(alias, EVENT_LOG);
    }

    /**
     * Create a <code>public.event_log</code> table reference
     */
    public EventLog() {
        this(DSL.name("event_log"), null);
    }

    public <O extends Record> EventLog(Table<O> child, ForeignKey<O, EventLogRecord> key) {
        super(child, key, EVENT_LOG);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<EventLogRecord, Long> getIdentity() {
        return (Identity<EventLogRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<EventLogRecord> getPrimaryKey() {
        return Keys.EVENT_LOG_PKEY;
    }

    @Override
    public List<UniqueKey<EventLogRecord>> getKeys() {
        return Arrays.<UniqueKey<EventLogRecord>>asList(Keys.EVENT_LOG_PKEY);
    }

    @Override
    public EventLog as(String alias) {
        return new EventLog(DSL.name(alias), this);
    }

    @Override
    public EventLog as(Name alias) {
        return new EventLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public EventLog rename(String name) {
        return new EventLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EventLog rename(Name name) {
        return new EventLog(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, LocalDateTime, String, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
