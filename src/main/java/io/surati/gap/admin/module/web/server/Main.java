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

import com.minlessika.db.BasicDatabase;
import com.minlessika.db.Database;
import com.minlessika.utils.ConsoleArgs;
import com.minlessika.utils.PreviousLocation;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.surati.gap.admin.module.AdminModule;
import io.surati.gap.admin.module.codec.SCodec;
import io.surati.gap.admin.base.db.AdminDatabaseBuiltWithLiquibase;
import io.surati.gap.web.base.FkMimes;
import io.surati.gap.web.base.TkAuth;
import io.surati.gap.web.base.TkSafeUserAlert;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Pass;
import org.takes.facets.auth.PsByFlag;
import org.takes.facets.auth.PsChain;
import org.takes.facets.auth.PsCookie;
import org.takes.facets.auth.PsLogout;
import org.takes.facets.auth.RsLogout;
import org.takes.facets.fallback.Fallback;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.RqFallback;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkParams;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.forward.RsForward;
import org.takes.facets.forward.TkForward;
import org.takes.http.Exit;
import org.takes.http.FtCli;
import org.takes.misc.Opt;
import org.takes.rs.RsHtml;
import org.takes.rs.RsText;
import org.takes.rs.RsVelocity;
import org.takes.rs.RsWithStatus;
import org.takes.tk.TkRedirect;
import org.takes.tk.TkSlf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Entry of application.
 * 
 * @since 0.1
 */
public final class Main {

	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
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
		final Database database = new BasicDatabase(source);
		try {
			database.startTransaction();
			new AdminDatabaseBuiltWithLiquibase(database);
			database.terminateTransaction();
		} catch (final Exception exe) {
			database.rollback();
			throw exe;
		}
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
				Main.safe(
					new TkForward(
						new TkFlash(
							Main.auth(
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
	
	/**
	 * Applies user authentication rules on take
	 * @param take Take
	 * @return Take with user authentication rules applied
	 */
	private static Take auth(final Take take, final Pass pass) {
		return new TkAuth(
			new TkFork(
				new FkParams(
					PsByFlag.class.getSimpleName(), 
					Pattern.compile(".+"), 
					new TkRedirect("/login")
				),
				new FkFixed(take)
			), 
			pass
		);
	}
	
    /**
     * With fallback.
     * @param take Takes
     * @return Safe takes
     */
    private static Take safe(final Take take){   	
        return new TkFallback(
            take,
            new FbChain(
                new FbStatus(
                    HttpURLConnection.HTTP_NOT_FOUND,
                    (Fallback) req -> {
                    	final Throwable ex = req.throwable();
                    	final Throwable cause = ExceptionUtils.getRootCause(ex);
                    	logger.error("Exception erreur 404 - Ressource non trouvée", cause);
                    	return new Opt.Single<>(
	                        new RsWithStatus(
	                    		new RsHtml(
	                    			new RsVelocity(
                                        Main.class.getResource("/io/surati/gap/web/base/vm/404.html.vm")
                                    )
	            			    ),
	                            HttpURLConnection.HTTP_NOT_FOUND
	                        )
	                    );
                    } 
                ),
                new FbStatus(
                    HttpURLConnection.HTTP_BAD_REQUEST,
                    (Fallback) req -> {     
                    	final Throwable ex = req.throwable();
                    	final Throwable cause = ExceptionUtils.getRootCause(ex);
                    	logger.error("Exception erreur 400 - Mauvaise requête", cause);
                    	final String fullMessage = ex.getLocalizedMessage();
                    	final String message = fullMessage.split("\\[400\\]")[1].trim();
                    	if(message.startsWith("IllEg:")) {                    		
                    		String url = new PreviousLocation(req, "/home").toString();
                        	return new Opt.Single<>(
								new RsForward(
									new RsFlash(
										message.replaceFirst("IllEg:", ""),
										Level.WARNING
									),
									url
								)
							);
                    	} else {
                    		return new Opt.Single<>(
            					new RsWithStatus(
                					new RsHtml(
                						new RsText(ex.getLocalizedMessage())
                				    ),
                				    HttpURLConnection.HTTP_BAD_REQUEST
                			    )
            			    );
                    	}                    		             
                    }
                ),
                new FbStatus(
                    HttpURLConnection.HTTP_UNAUTHORIZED,
                    (Fallback) req -> {
                    	final Throwable ex = req.throwable();
                    	final Throwable cause = ExceptionUtils.getRootCause(ex);
                    	logger.error("Exception erreur 401 - Accès non authorisé", cause);
                    	return new Opt.Single<>(
                			new RsLogout(
            					new RsForward(
                            		new RsFlash(
                                		"Vous devez vous connecter avant de continuer !",
                                		Level.WARNING
                            		),
    		                        "/login"
    		                    )
                			)    
                        );
                    }
                ),
                new FbStatus(
                    HttpURLConnection.HTTP_FORBIDDEN,
                    (Fallback) req -> {
                    	final Throwable ex = req.throwable();
                    	final Throwable cause;
                    	if(ExceptionUtils.getRootCause(ex) == null) {
                    		cause = ex;
                    	} else {
                    		cause = ExceptionUtils.getRootCause(ex);
                    	}
                    	logger.error("Exception erreur 403 - Accès Interdit", cause);
                    	return new Opt.Single<>(
                			new RsForward(
                        		new RsFlash(
                            		"Vous n'avez pas suffisamment de droits pour accéder à cette ressource !",
                            		Level.WARNING
                        		),
		                        "/home"
		                    )
                        );
                    }
                ),
                req -> new Opt.Single<>(Main.fatal(req))
            )
        );
    }

    /**
     * Make fatal error page.
     * @param req Request
     * @return Response
     * @throws IOException 
     * @throws Exception 
     */
    private static Response fatal(final RqFallback req) throws IOException {
    	final Throwable ex = req.throwable();
    	final Throwable cause = ExceptionUtils.getRootCause(ex);
    	logger.error("Exception erreur 500", cause);
    	return new RsWithStatus(
			new RsHtml(
				new RsVelocity(
                    Main.class.getResource("/io/surati/gap/web/base/vm/500.html.vm")
                )
		    ),
		    HttpURLConnection.HTTP_INTERNAL_ERROR
	    );
    }
    
}
