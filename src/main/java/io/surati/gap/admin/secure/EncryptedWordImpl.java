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
package io.surati.gap.admin.secure;
/**
 * EncryptedWordImpl
 * @since 0.1
 */
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public final class EncryptedWordImpl implements EncryptedWord {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    
    private final String word;
	private final Salt salt;
	
	public EncryptedWordImpl(final String word, final Salt salt) {
		this.word = word;
		this.salt = salt;
	}
	
	@Override
	public String value() {
        byte[] secureword = hash(word.toCharArray(), salt.value().getBytes());
        return Base64.getEncoder().encodeToString(secureword);
	}

	private static byte[] hash(char[] word, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(word, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(word, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing the word : " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}
