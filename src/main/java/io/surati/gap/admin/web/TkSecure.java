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
package io.surati.gap.admin.web;

import io.surati.gap.admin.rq.RqUser;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;

import javax.sql.DataSource;

/**
 * Take that secures a page.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
public final class TkSecure implements Take {

	/**
     * Original take.
     */
    private final Take origin;

    /**
     * DataSource.
     */
    private final DataSource source;
    
    /**
     * Ctor.
     * @param take Original
     * @param source DataSource
     */
    public TkSecure(final Take take, final DataSource source) {
        this.origin = take;
        this.source = source;
    }

    @Override
    public Response act(final Request request) throws Exception {
    	new RqUser(this.source, request);
		return this.origin.act(request);
    }

}
