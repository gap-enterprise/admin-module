/*
 * MIT License
 *
 * Copyright (c) 2022 Surati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.module.tk;

import io.surati.gap.admin.module.db.DbLog;
import org.takes.HttpException;
import org.takes.Take;
import org.takes.tk.TkWrap;

import javax.sql.DataSource;
import java.net.HttpURLConnection;

/**
 * Take that safely alert user.
 *
 * @since 3.0
 */
public final class TkSafeUserAlert extends TkWrap  {

	public TkSafeUserAlert(final DataSource source, final Take take) {
		super(
			(req) -> {
				try {
					return take.act(req);
				} catch (IllegalArgumentException ex) {
					new DbLog(source, req).warning(ex.getLocalizedMessage());
					throw new HttpException(
		                HttpURLConnection.HTTP_BAD_REQUEST,
		                String.format(
		                    "IllEg:%s",
		                    ex.getLocalizedMessage()
		                )
		            );
				}
			}
		);
	}
}
