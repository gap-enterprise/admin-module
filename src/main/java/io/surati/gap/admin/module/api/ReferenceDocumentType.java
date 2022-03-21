package io.surati.gap.admin.module.api;

public enum ReferenceDocumentType {

	NONE("Aucun"),
	INVOICE("Facture"),
	EXPENSE_REPORT("Note de frais"),
	PAY_SLIP("Bulletin de salaire"),
	WARRANT("Mandat");

	private final String title;

	private ReferenceDocumentType(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.title;
	}
}
