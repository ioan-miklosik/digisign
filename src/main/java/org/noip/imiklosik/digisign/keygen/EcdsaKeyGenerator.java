package org.noip.imiklosik.digisign.keygen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;


@Component
public class EcdsaKeyGenerator extends KeyGenerator {

    @Value("${digisign.ecdsa.keypair.algorithm}")
    private String keyPairAlgorithm;

    @Value("${digisign.ecdsa.keypair.provider}")
    private String keyPairProvider;

    @Value("${digisign.ecdsa.keypair.curve}")
    private String keyPairCurve;

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

    public KeyPair newKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyPairAlgorithm, keyPairProvider);
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec(keyPairCurve);
        keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

}
