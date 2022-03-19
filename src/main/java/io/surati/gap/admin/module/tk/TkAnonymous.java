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
package io.surati.gap.admin.module.tk;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.facets.forward.RsForward;
import org.takes.rs.RsEmpty;

/**
 * Take available for anonymous users.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 3.0
 */

public final class TkAnonymous implements Take {
	
    /**
     * Original take.
     */
    private final Take origin;

    /**
     * Location where to forward.
     */
    private final String loc;

    /**
     * Ctor.
     * @param take Original
     */
    public TkAnonymous(final Take take) {
        this(take, "/home");
    }

    /**
     * Ctor.
     * @param take Original
     * @param location Where to forward
     */
    public TkAnonymous(final Take take, final String location) {
        this.origin = take;
        this.loc = location;
    }

    @Override
    public Response act(final Request request) throws Exception {
        if (!new RqAuth(request).identity().equals(Identity.ANONYMOUS)) {
            throw new RsForward(
                new RsEmpty(),
                this.loc
            );
        }
        return this.origin.act(request);
    }

}
