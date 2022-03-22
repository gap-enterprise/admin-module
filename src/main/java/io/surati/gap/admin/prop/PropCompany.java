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
package io.surati.gap.admin.prop;
/**
 * PropCompany
 * @since 0.1
 */
import io.surati.gap.admin.api.Company;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Stream;

public final class PropCompany implements Company {

	private final File file;

	private final Properties prop;
	
	private static String LOGO_DEFAULT_LOCATION = "images/logo.png";
	private static String LOGO_FOLDER = "images";
	public static String DEFAULT_REFERENCE_DOCUMENT_TYPE = "default_reference_document_type";

	static {
		File folder = new File(LOGO_FOLDER);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		File file = new File(LOGO_DEFAULT_LOCATION);
		if(!file.exists()) {
			try (
				InputStream in = Company.class
					.getClassLoader()
					.getResourceAsStream("io/surati/gap/web/base/img/logo.png")
			) {
				Files.copy(in, Paths.get(LOGO_DEFAULT_LOCATION));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public PropCompany() {
		this(PropCompany.openConfigFile());
	}

	public PropCompany(final File file) {
		this.file = file;
		this.prop = PropCompany.loadConfigFile(this.file);
	}
	
	@Override
	public String name() {
		return this.get("name");
	}

	@Override
	public String abbreviated() {
		return this.get("abbreviated");
	}

	@Override
	public String ncc() {
		return this.get("ncc");
	}

	@Override
	public String city() {
		return this.get("city");
	}

	@Override
	public String country() {
		return this.get("country");
	}

	@Override
	public String tel1() {
		return this.get("tel1");
	}

	@Override
	public String tel2() {
		return this.get("tel2");
	}

	@Override
	public String email() {
		return this.get("email");
	}

	@Override
	public String website() {
		return this.get("website");
	}

	@Override
	public String address() {
		return this.get("address");
	}
	
	@Override
	public String headquarters() {
		return this.get("headquarters");
	}	

	@Override
	public void reidentify(String name, String abbreviated, String ncc) {
		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Vous devez spécifier le nom de l'entreprise !");
		}
		if(StringUtils.isBlank(abbreviated)) {
			throw new IllegalArgumentException("Vous devez spécifier l'abrégé du nom de l'entreprise !");
		}
		this.save("name", name);
		this.save("abbreviated", abbreviated);
		this.save("ncc", ncc);
	}

	@Override
	public void relocate(String city, String country, String address, String headquarters) {
		if(StringUtils.isBlank(city)) {
			throw new IllegalArgumentException("Vous devez spécifier une ville !");
		}
		if(StringUtils.isBlank(country)) {
			throw new IllegalArgumentException("Vous devez spécifier un pays !");
		}
		this.save("city", city);
		this.save("country", country);
		this.save("address", address);
		this.save("headquarters", headquarters);
	}
	
	@Override
	public void contacts(String tel1, String tel2, String email, String website) {
		if(StringUtils.isBlank(email)) {
			throw new IllegalArgumentException("Vous devez spécifier une adresse mail !");
		}
		this.save("tel1", tel1);
		this.save("tel2", tel2);
		this.save("email", email);
		this.save("website", website);
	}

	/**
	 * Save value at key.
	 * @param key Key
	 * @param value Value to save
	 */
	private void save(String key, String value) {
		this.prop.setProperty(key, String.valueOf(value));
		
		try(final OutputStream output = new FileOutputStream(this.file)) {			
		    this.prop.store(output, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get value of key.
	 * @param key Key
	 * @return value
	 */
	private String get(String key) {
		return this.prop.getProperty(key);
	}

	/**
	 * Get Properties file from file
	 * @param file Properties file
	 * @return
	 */
	private static Properties loadConfigFile(final File file) {		
		final Properties prop = new Properties();	
		try(final InputStream input = new FileInputStream(file)){
			prop.load(input);
		} catch (Exception e) {			
			throw new RuntimeException(e);
		}
		return prop;
	}
	
	/**
	 * Get default Properties file
	 * @return file
	 */
	private static File openConfigFile() {
		final Path path = Paths.get(System.getProperty("user.dir"), "config.properties");
		final File file = path.toFile();		
		if(!file.exists()) {
			try {
				try (
					InputStream in = PropCompany.class
						.getClassLoader()
						.getResourceAsStream("config.properties")
				) {
					Files.copy(in, path);
				}
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return file;
	}

	@Override
	public String representative() {
		return this.get("representative");
	}

	@Override
	public String representativePosition() {
		return this.get("representative_position");
	}

	@Override
	public String representativeCivility() {
		return this.get("representative_civility");
	}

	@Override
	public void representative(String name, String position, String civility) {
		this.save("representative", name);
		this.save("representative_position", position);
		this.save("representative_civility", civility);
	}

	@Override
	public String parameter(String key) {
		return this.get(key);
	}

	@Override
	public void parameter(String key, String value) {
		this.save(key, value);
	}

	@Override
	public void changeLogo(InputStream content, String ext) throws IOException {
		Files.walk(Paths.get(LOGO_FOLDER))
			.filter(
				f -> f.getFileName().toString().startsWith("logo_entreprise")
			).forEach(
				path -> {
					try {
						Files.delete(path);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
			);
		final String filenametosave = String.format("%s/logo_entreprise_%s.%s", LOGO_FOLDER, UUID.randomUUID(), ext);
		final Path path = new File(filenametosave).toPath();
        Files.copy(content, path);
	}

	@Override
	public InputStream logo() throws IOException {
		final String logo = this.logoLocation();
		return new FileInputStream(
			new File(logo)
		);
	}

	@Override
	public String logoBase64() {
		try {
			final String logo = this.logoLocation();
			final String ext = FilenameUtils.getExtension(logo);
			final InputStream in = this.logo();
			byte[] bytes = IOUtils.toByteArray(in);
			return String.format("data:image/%s;base64,%s", ext, Base64.encodeBase64String(bytes));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String logoLocation() {
		final String imageFile;
		try (Stream<Path> files = Files.walk(Paths.get(LOGO_FOLDER))) {
	        final Optional<String> file =  files.filter(f -> f.getFileName().toString().startsWith("logo_entreprise"))
                .map(f -> f.getFileName().toString())
                .findFirst();
	        if(file.isPresent()) {
	        	imageFile = file.get();
	        }
	        else {
	        	imageFile = "logo.png"; 
	        }
	        return String.format("%s/%s", LOGO_FOLDER, imageFile);
	    } catch (IOException ex) {
	    	throw new RuntimeException(ex);
		}
	}
}
