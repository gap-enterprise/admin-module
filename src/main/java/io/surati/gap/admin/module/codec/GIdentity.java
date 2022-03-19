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
package io.surati.gap.admin.module.codec;

import io.surati.gap.admin.module.api.User;
import org.takes.facets.auth.Identity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class GIdentity implements Identity {

	private final User user;

	public GIdentity(final User user) {
		this.user = user;
	}

	@Override
	public String urn() {
		return String.format("urn:gap:%s", user.login());
	}

	@Override
	public Map<String, String> properties() {
		Map<String, String> props = new HashMap<>();
		props.put("id", user.id().toString());
		props.put("login", user.login());
		props.put("password", user.password());
		props.put("profile", user.profile().name());
		props.put("photo", String.format("/%s" ,user.photoLocation()));
		return Collections.unmodifiableMap(props);
	}

}
