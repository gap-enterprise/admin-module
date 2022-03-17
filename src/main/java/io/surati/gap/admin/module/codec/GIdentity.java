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
