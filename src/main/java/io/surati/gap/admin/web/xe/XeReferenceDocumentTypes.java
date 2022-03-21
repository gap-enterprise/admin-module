package io.surati.gap.admin.web.xe;

/**
 * Xml Reference Document Types
 * @since 0.1
 */
import io.surati.gap.admin.api.ReferenceDocumentType;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeReferenceDocumentTypes extends XeWrap {

	public XeReferenceDocumentTypes() {
		this(
			new ListOf<>(
				Arrays.stream(ReferenceDocumentType.values())
					.filter(c -> c != ReferenceDocumentType.NONE)
					.collect(Collectors.toList())
			)
		);
	}
	public XeReferenceDocumentTypes(final Iterable<ReferenceDocumentType> items) {
		super(
			new XeDirectives(
				new Directives()
					.add("reference_document_types")
					.append(
						new Joined<>(
							new Mapped<>(
								item -> new XeReferenceDocumentType("reference_document_type", item).toXembly(),
								items
							)
						)
					)
			)
		);
	}
}
