/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
	
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.api;

import io.surati.gap.admin.secure.Salt;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * User.
 * 
 * @since 0.1
 */
public interface User extends Person {
	
	/**
	 * Login of a user
	 * @return login
	 */
	String login();
	
	/**
	 * Password of a user
	 * @return password
	 */
	String password();
	
	/**
	 * Salt.
	 * @return salt
	 */
	Salt salt();
	
	/**
	 * Force password changing
	 * @param newPassword new password
	 */
	void forceChangePassword(String newPassword);
	
	/**
	 * Change user password
	 * @param oldpassword
	 * @param password
	 */
	void changePassword(String oldpassword, String password);
	
	/**
	 * Block or not user
	 * @param enable Blocked or not
	 */
	void block(boolean enable);
	
	/**	
	 * If user is blocked
	 * @return blocked or not
	 */
	boolean blocked();
	
	/**
	 * Update.
	 * @param login
	 * @param name 
	 */
	void update(String login, String name);
	
	/**
	 * Profile.
	 * @return profile
	 */
	Profile profile();
	
	/**
	 * Assign a profile.
	 * @param profile Profile to assign
	 */
	void assign(Profile profile);
	
	void changePhoto(final InputStream content, final String ext) throws IOException;
	
	InputStream photo() throws IOException;
	
	String photoBase64();
	
	String photoLocation();
	
	User EMPTY = new User() {
		
		@Override
		public void update(String name) {

		}
		
		@Override
		public String name() {
			return StringUtils.EMPTY;
		}
		
		@Override
		public Long id() {
			return 0L;
		}
		
		@Override
		public void update(String login, String name) {

		}
		
		@Override
		public Salt salt() {
			return null;
		}
		
		@Override
		public Profile profile() {
			return null;
		}
		
		@Override
		public String password() {
			return null;
		}
		
		@Override
		public String login() {
			return null;
		}
		
		@Override
		public void forceChangePassword(String newPassword) {
			
		}
		
		@Override
		public void changePassword(String oldpassword, String password) {

		}
		
		@Override
		public boolean blocked() {
			return false;
		}
		
		@Override
		public void block(boolean enable) {
			
		}
		
		@Override
		public void assign(Profile profile) {
			
		}

		@Override
		public void changePhoto(InputStream content, String ext) throws IOException {
			
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
	};
}
