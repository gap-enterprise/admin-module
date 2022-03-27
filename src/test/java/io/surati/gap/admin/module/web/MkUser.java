package io.surati.gap.admin.module.web;

import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.admin.base.secure.Salt;

import java.io.IOException;
import java.io.InputStream;

public final class MkUser implements User {

    @Override
    public String login() {
        return "fake";
    }

    @Override
    public String password() {
        return "fake2022";
    }

    @Override
    public Salt salt() {
        return null;
    }

    @Override
    public void forceChangePassword(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public void block(boolean b) {

    }

    @Override
    public boolean blocked() {
        return false;
    }

    @Override
    public void update(String s, String s1) {

    }

    @Override
    public Profile profile() {
        return null;
    }

    @Override
    public void assign(Profile profile) {

    }

    @Override
    public void changePhoto(InputStream inputStream, String s) throws IOException {

    }

    @Override
    public InputStream photo() throws IOException {
        return null;
    }

    @Override
    public String photoBase64() {
        return null;
    }

    @Override
    public String photoLocation() {
        return null;
    }

    @Override
    public Long id() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void update(String s) {

    }
}
