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
package io.surati.gap.admin.module;

import io.surati.gap.admin.base.api.Module;
import io.surati.gap.admin.module.secure.AdminAccess;
import io.surati.gap.web.base.menu.DashboardMenu;
import io.surati.gap.web.base.menu.Menu;
import io.surati.gap.web.base.menu.SimpleMenu;
import io.surati.gap.web.base.menu.SimpleSubmenu;
import org.cactoos.iterable.IterableOf;
import org.pf4j.Plugin;

/**
 * Admin module.
 *
 * @since 0.3
 */
public enum AdminModule implements Module {

	ADMIN(
		"Administration",
		"Ce module permet de gérer les utilisateurs, leurs profils, les droits d'accès, etc."
	);

	public static void setup() {
		Module.VALUES.add(ADMIN);
		Module.BY_CODE.put(ADMIN.code(), ADMIN);
		for (final DashboardMenu dmenu : AdminDashboardMenu.values()) {
			DashboardMenu.VALUES.add(dmenu);
		}
		Menu.VALUES.add(
			new SimpleMenu(
				1000,
				"admin",
				"lnr-cog",
				"Administration",
				"bg-danger",
				"Gérer la sécurité, l'audit, etc.",
				new IterableOf<>(
					new SimpleSubmenu(
						1, "users", "lnr-user", "Utilisateurs", "/user",
						new IterableOf<>(
							AdminAccess.VISUALISER_UTILISATEURS,
							AdminAccess.CONFIGURER_UTILISATEURS,
							AdminAccess.BLOQUER_UTILISATEURS,
							AdminAccess.CHANGER_MOT_DE_PASSE_UTILISATEURS
						),
						false
					),
					new SimpleSubmenu(
						2, "profile", "lnr-license", "Profils", "/profile",
						new IterableOf<>(
							AdminAccess.VISUALISER_PROFILS,
							AdminAccess.CONFIGURER_PROFILS
						),
						false
					),
					new SimpleSubmenu(
						3, "enterprise", "lnr-database", "Entreprise", "/enterprise",
						new IterableOf<>(
							AdminAccess.VISUALISER_INFO_ENTREPRISE,
							AdminAccess.CONFIGURER_INFO_ENTREPRISE
						),
						false
					),
					new SimpleSubmenu(
						4, "event-log", "lnr-layers", "Journalisation", "/event-log",
						new IterableOf<>(
							AdminAccess.VISUALISER_LA_JOURNALISATION
						),
						false
					)
				)
			)
		);
	}

	/**
	 * Title of access.
	 */
	private String title;

	/**
	 * Description.
	 */
	private String description;

	/**
	 * Ctor.
	 * @param title Title
	 * @param description Description
	 */
	AdminModule(final String title, final String description) {
		this.title = title;
		this.description = description;
	}
	
	/**
	 * Get title.
	 * @return Title
	 */
	@Override
	public String title() {
		return this.title;
	}

	/**
	 * Get description.
	 * @return Description
	 */
	@Override
	public String description() {
		return this.description;
	}

	/**
	 * Get Code.
	 * @return Code
	 */
	public String code() {
		return this.name();
	}
	
	@Override
	public String toString() {
		return this.title;
	}
}
