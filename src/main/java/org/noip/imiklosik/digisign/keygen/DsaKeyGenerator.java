package org.noip.imiklosik.digisign.keygen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;


@Component
public class DsaKeyGenerator extends KeyGenerator{

    @Value("${digisign.dsa.keypair.algorithm}")
    private String keyPairAlgorithm;

    @Value("${digisign.dsa.keypair.provider}")
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
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyPairAlgorithm, keyPairProvider);
        keyGen.initialize(1024, new SecureRandom());
        return keyGen.generateKeyPair();
    }
}
