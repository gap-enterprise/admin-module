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
package io.surati.gap.admin.module.codec;

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
