/*
 * MIT License
 *
 * Copyright (c) 2022 Surati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.module.db;

import io.surati.gap.admin.module.api.Person;
import io.surati.gap.admin.module.exceptions.DatabaseException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Person in Database.
 * 
 * @since 3.0
 */
public abstract class DbAbstractPerson implements Person {

	/**
	 * Data source
	 */
	protected final DataSource source;
	
	/**
	 * Id of person
	 */
	protected final Long id;
	
	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbAbstractPerson(final DataSource source, final Long id) {
		this.source = source;
		this.id = id;
	}
	
	@Override
	public Long id() {
		return id;
	}

	@Override
	public String name() {
		try (
				final Connection connection = source.getConnection(); 
				final PreparedStatement pstmt = connection.prepareStatement("SELECT name FROM person WHERE id=?")
		) {
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public void update(final String name) {
		
		if(name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Person's name must be given !");
		
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE person SET name=? WHERE id=?")
		) {
			pstmt.setString(1, name);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
	}

}
