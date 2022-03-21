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
 * Log
 *
 * @since 0.1
 */
public interface Log {
	/**
	 * Message de notification, d'information
	 * @param message Message
	 * @param args Arguments
	 */
	void info(String message, Object... args);
	
	/**
	 * Message de notification, d'information
	 * @param message Message
	 */
	void info(String message);
	
	/**
	 * Message de mauvaises requêtes ({@link IllegalArgumentException},
	 * d'erreurs utilisateur
	 * @param message Message
	 * @param args Arguments
	 */
	void warning(String message, Object... args);
	
	/**
	 * Message de mauvaises requêtes ({@link IllegalArgumentException},
	 * d'erreurs utilisateur
	 * @param message Message
	 */
	void warning(String message);
	
	/**
	 * Message d'erreurs provenant du serveur
	 * @param message Message
	 * @param details Details de l'erreur
	 */
	void error(String message, String details);

	Iterable<EventLog> iterate();
}
