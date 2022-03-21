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
 * Company
 *
 * @since 0.1
 */
import java.io.IOException;
import java.io.InputStream;

public interface Company {
	
	String name();
	
	String abbreviated();
	
	String ncc();
	
	String city();
	
	String country();
	
	String tel1();
	
	String tel2();
	
	String email();
	
	String website();
	
	String address();
	
	String headquarters();
	
	String representative();
	
	String representativePosition();
	
	String representativeCivility();
	
	void reidentify(String name, String abbreviated, String ncc);
	
	void relocate(String city, String country, String address, String headquarters);
	
	void contacts(String tel1, String tel2, String email, String website);

	void representative(String name, String position, String civility);
	
	String parameter(String key);
	
	void parameter(String key, String value);
	
	void changeLogo(final InputStream content, final String ext) throws IOException;
	
	InputStream logo() throws IOException;
	
	String logoBase64();
	
	String logoLocation();
}
