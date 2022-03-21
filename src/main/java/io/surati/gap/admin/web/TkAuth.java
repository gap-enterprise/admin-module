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
/**
 * Take that authentificates an user.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @since 0.1
 */
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.Pass;
import org.takes.facets.auth.PsCookie;
import org.takes.facets.auth.RqWithAuth;
import org.takes.misc.Opt;
import org.takes.rq.RqWithoutHeader;

public final class TkAuth implements Take {

	/**
     * Original take.
     */
    private final Take origin;

    /**
     * Pass.
     */
    private final Pass pass;

    /**
     * Header to set in case of authentication.
     */
    private final String header;

    /**
     * Ctor.
     * @param take Original
     * @param pss Pass
     */
    public TkAuth(final Take take, final Pass pss) {
        this(take, pss, TkAuth.class.getSimpleName());
    }

    /**
     * Ctor.
     * @param take Original
     * @param pss Pass
     * @param hdr Header to set
     */
    public TkAuth(final Take take, final Pass pss, final String hdr) {
        this.origin = take;
        this.pass = pss;
        this.header = hdr;
    }

    @Override
    public Response act(final Request request) throws Exception {
        final Opt<Identity> user = this.pass.enter(request);
        final Response response;
        if (user.has()) {
            response = this.act(request, user.get());
        } else {
            response = this.origin.act(request);
        }
        return response;
    }

    /**
     * Make take.
     * @param req Request
     * @param identity Identity
     * @return Take
     * @throws Exception If fails
     */
    private Response act(final Request req, final Identity identity)
        throws Exception {
        Request wrap = new RqWithoutHeader(req, this.header);
        if (!identity.equals(Identity.ANONYMOUS)) {
            wrap = new RqWithAuth(identity, this.header, wrap);
        }
        final Response resp = this.origin.act(wrap);
        boolean hasalreadyidt = false;
        for (String header : resp.head()) {
			if(header.contains(PsCookie.class.getSimpleName())) {
				hasalreadyidt = true;
				break;
			}
		}
        if(!hasalreadyidt) {
        	return this.pass.exit(resp, identity);
        } else {
        	return resp;
        }       
    }

}
