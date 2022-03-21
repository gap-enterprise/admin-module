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

import java.time.LocalDateTime;
import java.util.logging.Level;

/**
 * Log event.
 *
 * @since 0.1
 */
public interface EventLog {

	/**
	 * Get identifier.
	 * @return Number
	 */
	Long id();

	/**
	 * Get date of event.
	 * @return Datetime
	 */
	LocalDateTime date();

	/**
	 * Get level.
	 * @return Level
	 */
	Level level();

	/**
	 * Get message.
	 * @return Text
	 */
	String message();

	/**
	 * Get details on event.
	 * @return Text
	 */
	String details();

	/**
	 * Get author.
	 * @return login
	 */
	String author();

	/**
	 * Get IP address.
	 * @return IP
	 */
	String ipAddress();
}
