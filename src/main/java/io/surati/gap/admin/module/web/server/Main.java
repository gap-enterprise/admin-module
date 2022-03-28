/**
MIT License

Copyright (c) 2021 Surati

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package io.surati.gap.admin.module.web.server;

import com.lightweight.db.LiquibaseDataSource;
import com.minlessika.db.BasicDatabase;
import com.minlessika.db.Database;
import com.minlessika.utils.ConsoleArgs;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.surati.gap.admin.module.AdminModule;
import io.surati.gap.admin.base.db.AdminDatabaseBuiltWithLiquibase;
import io.surati.gap.web.base.FkMimes;
import io.surati.gap.web.base.TkSafeUserAlert;
import io.surati.gap.web.base.auth.SCodec;
import io.surati.gap.web.base.TkSafe;
import io.surati.gap.web.base.auth.TkAuth;
import org.apache.commons.lang3.StringUtils;
import org.takes.facets.auth.Pass;
import org.takes.facets.auth.PsByFlag;
import org.takes.facets.auth.PsChain;
import org.takes.facets.auth.PsCookie;
import org.takes.facets.auth.PsLogout;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.forward.TkForward;
import org.takes.http.Exit;
import org.takes.http.FtCli;
import org.takes.tk.TkSlf4j;
import javax.sql.DataSource;
import java.util.Map;

/**
 * Entry of application.
 * 
 * @since 0.1
 */
public final class Main {
	
	/**
	 * App entry
	 * @param args Arguments
	 * @throws Exception If some problems in
	 */
	public static void main(String[] args) throws Exception {		
		final Map<String, String> argsMap = new ConsoleArgs("--", args).asMap();
        final String dbname = argsMap.get("db-name");
        final String dbport = argsMap.get("db-port");
        final String dbhost = argsMap.get("db-host");
        final String user = argsMap.get("db-user");
        final String password = argsMap.get("db-password");
		final String url = String.format("jdbc:postgresql://%s:%s/%s", dbhost, dbport, dbname);
		final String driver = argsMap.get("driver");	
		final HikariConfig configdb = new HikariConfig();
		configdb.setDriverClassName(driver);
		configdb.setJdbcUrl(url);
		configdb.setUsername(user);
		configdb.setPassword(password);
		int psize = 5;
		if(StringUtils.isNotBlank(argsMap.get("db-pool-size"))) {
			psize = Integer.parseInt(argsMap.get("db-pool-size"));
		}
		configdb.setMaximumPoolSize(psize);
		final DataSource source = new HikariDataSource(configdb);
		final Database database = new BasicDatabase(
			new LiquibaseDataSource(
				source,
				AdminDatabaseBuiltWithLiquibase.CHANGELOG_MASTER_FILENAME
			)
		);
		AdminModule.setup();
		final Pass pass = new PsChain(
			new PsByFlag(
				new PsByFlag.Pair(
					PsLogout.class.getSimpleName(), 
					new PsLogout()
				)
			),
			new PsCookie(
				new SCodec()
			)
		);
		new FtCli(
			new TkSlf4j(
				new TkSafe(
					new TkForward(
						new TkFlash(
							new TkAuth(
								new TkSafeUserAlert(
									source,
									new TkFork(
										new FkMimes(),
										new FkRegex("/robots\\.txt", ""),
										new FkActions(database, pass),
										new FkPages(database),
										new FkApi(database)
									)
								),
								pass
							)
						)
					)
				)
			),
			args
		).start(Exit.NEVER);	
	}
    
}
