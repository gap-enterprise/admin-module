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


/**
 * Users who manage application.
 * 
 * @since 0.1
 */
public interface Users {
	
	/**
	 * Check a user credentials
	 * @param login
	 * @param password
	 * @return boolean
	 */
	boolean authenticate(String login, String password);
	
	/**
	 * Check a user credentials
	 * @param login
	 * @param password
	 * @return boolean
	 */
	boolean authenticatePwdEncrypted(String login, String password);
	
	/**
	 * Checks if a user with login exists
	 * @param login
	 * @return boolean exits
	 */
	boolean has(String login);
	
	/**
	 * Get User
	 * @param login
	 * @return User
	 */
	User get(String login);
	
	/**
	 * Get User
	 * @param id
	 * @return User
	 */
	User get(Long id);
	
	/**
	 * Add a new user
	 * @param name
	 * @param login
	 * @param password
	 * @return 
	 */
	User register(String name, String login, String password);
	
	/**
	 * Iterate item
	 * @return All users
	 */
	Iterable<User> iterate();
	
	/**
	 * Remove a {@link User}.
	 * @param id
	 */
	void remove(Long id);

	/**
	 * Get total number of users.
	 * @return Number
	 */
	Long count();
}
