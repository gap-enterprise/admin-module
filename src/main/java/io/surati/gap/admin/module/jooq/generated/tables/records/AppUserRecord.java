/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.module.jooq.generated.tables.records;


import io.surati.gap.admin.module.jooq.generated.tables.AppUser;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 * @since 0.1
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AppUserRecord extends UpdatableRecordImpl<AppUserRecord> implements Record6<Long, String, String, String, Boolean, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.app_user.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.app_user.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.app_user.login</code>.
     */
    public void setLogin(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.app_user.login</code>.
     */
    public String getLogin() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.app_user.password</code>.
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.app_user.password</code>.
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.app_user.salt</code>.
     */
    public void setSalt(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.app_user.salt</code>.
     */
    public String getSalt() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.app_user.blocked</code>.
     */
    public void setBlocked(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.app_user.blocked</code>.
     */
    public Boolean getBlocked() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>public.app_user.profile_id</code>.
     */
    public void setProfileId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.app_user.profile_id</code>.
     */
    public Long getProfileId() {
        return (Long) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, String, String, Boolean, Long> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, String, Boolean, Long> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return AppUser.APP_USER.ID;
    }

    @Override
    public Field<String> field2() {
        return AppUser.APP_USER.LOGIN;
    }

    @Override
    public Field<String> field3() {
        return AppUser.APP_USER.PASSWORD;
    }

    @Override
    public Field<String> field4() {
        return AppUser.APP_USER.SALT;
    }

    @Override
    public Field<Boolean> field5() {
        return AppUser.APP_USER.BLOCKED;
    }

    @Override
    public Field<Long> field6() {
        return AppUser.APP_USER.PROFILE_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getLogin();
    }

    @Override
    public String component3() {
        return getPassword();
    }

    @Override
    public String component4() {
        return getSalt();
    }

    @Override
    public Boolean component5() {
        return getBlocked();
    }

    @Override
    public Long component6() {
        return getProfileId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getLogin();
    }

    @Override
    public String value3() {
        return getPassword();
    }

    @Override
    public String value4() {
        return getSalt();
    }

    @Override
    public Boolean value5() {
        return getBlocked();
    }

    @Override
    public Long value6() {
        return getProfileId();
    }

    @Override
    public AppUserRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public AppUserRecord value2(String value) {
        setLogin(value);
        return this;
    }

    @Override
    public AppUserRecord value3(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public AppUserRecord value4(String value) {
        setSalt(value);
        return this;
    }

    @Override
    public AppUserRecord value5(Boolean value) {
        setBlocked(value);
        return this;
    }

    @Override
    public AppUserRecord value6(Long value) {
        setProfileId(value);
        return this;
    }

    @Override
    public AppUserRecord values(Long value1, String value2, String value3, String value4, Boolean value5, Long value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AppUserRecord
     */
    public AppUserRecord() {
        super(AppUser.APP_USER);
    }

    /**
     * Create a detached, initialised AppUserRecord
     */
    public AppUserRecord(Long id, String login, String password, String salt, Boolean blocked, Long profileId) {
        super(AppUser.APP_USER);

        setId(id);
        setLogin(login);
        setPassword(password);
        setSalt(salt);
        setBlocked(blocked);
        setProfileId(profileId);
    }
}
