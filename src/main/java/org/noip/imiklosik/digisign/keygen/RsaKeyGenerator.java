package org.noip.imiklosik.digisign.keygen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;


@Component
public class RsaKeyGenerator extends KeyGenerator {

    @Value("${digisign.rsa.keypair.algorithm}")
    private String keyPairAlgorithm;

    @Value("${digisign.rsa.keypair.provider}")
    private String keyPairProvider;

    @Value("${digisign.charencoding}")
    private String keyCharEnc;

    @Override
    protected String getKeyPairAlgorithm() {
        return keyPairAlgorithm;
    }

    @Override
    protected String getKeyPairProvider() {
        return keyPairProvider;
    }

    @Override
    protected String getKeyCharEnc() {
        return keyCharEnc;
    }

    public KeyPair newKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyPairAlgorithm, keyPairProvider);
        keyPairGenerator.initialize(2048, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

}
