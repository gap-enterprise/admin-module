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
package io.surati.gap.admin.codec;

/**
 * SCodec
 *
 * @since 0.1
 */
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.codecs.CcBase64;
import org.takes.facets.auth.codecs.CcCompact;
import org.takes.facets.auth.codecs.CcHex;
import org.takes.facets.auth.codecs.CcSafe;
import org.takes.facets.auth.codecs.CcSalted;
import org.takes.facets.auth.codecs.CcXor;
import org.takes.facets.auth.codecs.Codec;

import java.io.IOException;

public final class SCodec implements Codec {

	private final Codec origin;
	
	public SCodec() {
		this("My faith is in Jesus-Christ ! + 1");
	}

	public SCodec(final String passphrase) {
		this.origin = new CcBase64(
			new CcSafe(
            	new CcHex(
                    new CcXor(
                        new CcSalted(new CcCompact()),
                        passphrase
                    )
                )
			)
		);
	}
	
	@Override
	public byte[] encode(Identity identity) throws IOException {
		return origin.encode(identity);
	}

	@Override
	public Identity decode(byte[] bytes) throws IOException {
		return origin.decode(bytes);
	}

}
