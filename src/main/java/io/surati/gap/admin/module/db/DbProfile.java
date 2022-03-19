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
package io.surati.gap.admin.module.db;

import io.surati.gap.admin.module.api.Profile;
import io.surati.gap.admin.module.api.ProfileAccesses;
import io.surati.gap.admin.module.exceptions.DatabaseException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DbProfile implements Profile {

	/**
	 * Identifier.
	 */
	private final Long id;
	
	/**
	 * DataSource.
	 */
	private final DataSource source;

	/**
	 * Ctor.
	 * @param source DataSource
	 * @param id Identifier
	 */
	public DbProfile(final DataSource source, final Long id) {
		this.source = source;
		this.id = id;
	}
	
	
	/**
	 * Checks if a Profile with name and not id exists
	 * @param name
	 * @param id
	 * @return boolean exits
	 */
	private boolean nameIsUsed(String name) {
		try (
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) as nb FROM profile WHERE name=? AND id <>?")
			){
				pstmt.setString(1, name);
				pstmt.setLong(2, id);
			
				try(final ResultSet rs = pstmt.executeQuery()){
					rs.next();
					Long nb = rs.getLong(1);
					return nb > 0;
				}
			} catch(SQLException e) {
				throw new DatabaseException(e);
			}
	}
	
	@Override
	public Long id() {
		return this.id;
	}

	@Override
	public String name() {
		try (
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("SELECT name FROM profile WHERE id=?")
			){
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
			throw new IllegalArgumentException("Le nom doit être renseigné !");
		
		if(this.nameIsUsed(name))
			throw new IllegalArgumentException("Ce nom est déjà utilisé.");
			
		try (
			final Connection connection = source.getConnection();
				
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE profile SET name=? WHERE id=?")
		) {
			pstmt.setString(1, name);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public ProfileAccesses accesses() {
		return new DbProfileAccesses(this.source, this);
	}

	@Override
	public String toString() {
		return String.format("ID=%s, Intitulé=%s", this.id, this.name());
	}
}
