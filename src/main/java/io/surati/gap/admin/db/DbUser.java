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
package io.surati.gap.admin.db;

import com.google.common.base.Objects;
import io.surati.gap.admin.api.Profile;
import io.surati.gap.admin.api.User;
import io.surati.gap.admin.jooq.generated.tables.records.AppUserRecord;
import io.surati.gap.admin.secure.ConstantSalt;
import io.surati.gap.admin.secure.EncryptedWordImpl;
import io.surati.gap.admin.secure.GeneratedSalt;
import io.surati.gap.admin.secure.Salt;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

/**
 * User in database.
 * 
 * @since 0.1
 */
public final class DbUser extends DbAbstractPerson implements User {

	/**
	 * Table of user.
	 */
	private static final io.surati.gap.admin.jooq.generated.tables.AppUser APP_USER =
		io.surati.gap.admin.jooq.generated.tables.AppUser.APP_USER;

	/**
	 * Record.
	 */
	private final AppUserRecord record;
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	private static String AVATAR_DEFAULT_LOCATION = "images/avatar.jpg";
	private static String AVATAR_FOLDER = "images";

	static {
		File folder = new File(AVATAR_FOLDER);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		File file = new File(AVATAR_DEFAULT_LOCATION);
		if(!file.exists()) {
			try (
				InputStream in = User.class
					.getClassLoader()
					.getResourceAsStream("io/surati/gap/admin/img/avatar.jpg")
			) {
				Files.copy(in, Paths.get(AVATAR_DEFAULT_LOCATION));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbUser(final DataSource source, final Long id) {
		super(source, id);
		this.ctx = DSL.using(new DefaultConfiguration().set(this.source));
		this.record = this.ctx.fetchOne(APP_USER, APP_USER.ID.eq(id));
	}
	
	/**
	 * Checks if an User with login and not id exists
	 * @param login Login
	 * @return boolean exits
	 */
	private boolean loginIsUsed(String login) {
		return this.ctx
			.fetchCount(
				APP_USER, 
				APP_USER.LOGIN.eq(login),
				APP_USER.ID.notEqual(this.id)
			) > 0;
	}
	
	@Override
	public String login() {
		return this.record.getLogin();
	}

	@Override
	public String password() {
		return this.record.getPassword();
	}

	@Override
	public void forceChangePassword(final String newpwd) {
		if(StringUtils.isBlank(newpwd)) {
			throw new IllegalArgumentException("New password must be given !");
		}
		final Salt salt = new GeneratedSalt();
		this.record.setPassword(new EncryptedWordImpl(newpwd, salt).value());
		this.record.setSalt(salt.value());
		this.record.store();
	}
	
	@Override
	public void changePassword(final String oldpassword, final String password) {		 
		if(StringUtils.isBlank(oldpassword)) {
			throw new IllegalArgumentException("L'ancien mot de passe est obligatoire !");
		}		
		if(password.equals(oldpassword)) {
			throw new IllegalArgumentException("Le nouveau mot de passe doit être différent de l'ancien !");
		}
		if(
			!this.password().equals(
				new EncryptedWordImpl(oldpassword, this.salt()).value()
			)
		) {
			throw new IllegalArgumentException("L'ancien mot de passe ne correspond pas !");
		}
		this.forceChangePassword(password);		
	}
	
	@Override
	public boolean blocked() {
		return this.record.getBlocked();
	}
	
	@Override
	public void block(final boolean enable) {
		this.record.setBlocked(enable);
		this.record.store();
	}
	
	@Override
	public void update(final String login, final String name) {
		if(StringUtils.isBlank(login))
			throw new IllegalArgumentException("Le login doit être renseigné !");
		if(StringUtils.isBlank(name))
			throw new IllegalArgumentException("Le nom doit être renseigné !");
		if(this.loginIsUsed(login))
			throw new IllegalArgumentException("Ce login est déjà utilisé.");
		super.update(name);
		this.record.setLogin(login);
		this.record.store();
	}

	@Override
	public Profile profile() {
		if (this.record.getProfileId() == null) {
			throw new IllegalArgumentException("L'utilisateur n'a pas de profil !");
		}
		return new DbProfile(this.source, this.record.getProfileId());
	}

	@Override
	public void assign(final Profile profile) {
		this.record.setProfileId(profile.id());
		this.record.store();
	}

	@Override
	public Salt salt() {
		return new ConstantSalt(
			this.record.getSalt()
		);
	}

	@Override
	public void changePhoto(final InputStream content, final String ext) throws IOException {
		Files.walk(Paths.get(AVATAR_FOLDER))
			.filter(
				f -> f.getFileName().toString().startsWith(String.format("avatar_%s_", this.id))
			).forEach(
				path -> {
					try {
						Files.delete(path);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
			);
		final String filenametosave = String.format(
			"%s/avatar_%s_%s.%s", AVATAR_FOLDER,
			this.id, UUID.randomUUID(), ext
		);
		final Path path = new File(filenametosave).toPath();
        Files.copy(content, path);
	}

	@Override
	public InputStream photo() throws IOException {
		final String photo = this.photoLocation();
		return new FileInputStream(photo);
	}

	@Override
	public String photoBase64() {
		try {
			final String photo = this.photoLocation();
			final String ext = FilenameUtils.getExtension(photo);
			final InputStream in = this.photo();
			byte[] bytes = IOUtils.toByteArray(in);
			return String.format("data:image/%s;base64,%s", ext, Base64.encodeBase64String(bytes));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String photoLocation() {
		final String imageFile;
		try (Stream<Path> files = Files.walk(Paths.get(AVATAR_FOLDER))) {
	        final Optional<String> file =  files.filter(f -> f.getFileName().toString().startsWith(String.format("avatar_%s", this.id)))
                .map(f -> f.getFileName().toString())
                .findFirst();
	        if(file.isPresent()) {
	        	imageFile = file.get();
	        }
	        else {
	        	imageFile = "avatar.jpg"; 
	        }
	        return String.format("%s/%s", AVATAR_FOLDER, imageFile);
	    } catch (IOException ex) {
	    	throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof User)) {
			return false;
		}
		final User that = (User)obj;
		return Objects.equal(this.id, that.id());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
}
 