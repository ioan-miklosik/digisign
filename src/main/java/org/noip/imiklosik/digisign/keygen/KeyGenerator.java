package org.noip.imiklosik.digisign.keygen;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public abstract class KeyGenerator {

    protected abstract String getKeyPairAlgorithm();
    protected abstract String getKeyPairProvider();
    protected abstract String getKeyCharEnc();

    public PublicKey pubKeyFromBase64String(String publicKeyBase64) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(getKeyPairAlgorithm(), getKeyPairProvider());
        Charset charset = Charset.forName(getKeyCharEnc());
        byte[] publicBytes = Base64.getDecoder().decode(publicKeyBase64.getBytes(charset));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        return keyFactory.generatePublic(keySpec);
    }

    public PrivateKey privKeyFromBase64String(String privateKeyBase64) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(getKeyPairAlgorithm(), getKeyPairProvider());
        Charset charset = Charset.forName(getKeyCharEnc());
        byte[] privateBytes = Base64.getDecoder().decode(privateKeyBase64.getBytes(charset));
        PKCS8EncodedKeySpec keySpecPriv = new PKCS8EncodedKeySpec(privateBytes);
        return keyFactory.generatePrivate(keySpecPriv);
    }

    public KeyPair fromBase64Strings(String privateKeyBase64, String publicKeyBase64) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        return new KeyPair(
                pubKeyFromBase64String(publicKeyBase64),
                privKeyFromBase64String(privateKeyBase64)
        );
    }

}
