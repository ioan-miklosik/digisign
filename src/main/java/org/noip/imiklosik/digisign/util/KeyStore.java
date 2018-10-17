package org.noip.imiklosik.digisign.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.noip.imiklosik.digisign.keygen.DsaKeyGenerator;
import org.noip.imiklosik.digisign.keygen.EcdsaKeyGenerator;
import org.noip.imiklosik.digisign.keygen.RsaKeyGenerator;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

@Component
public class KeyStore {

    @Value("${digisign.dsa.privkey}")
    private String dsaPrivateKeyBase64;

    @Value("${digisign.dsa.pubkey}")
    private String dsaPublicKeyBase64;

    @Value("${digisign.ecdsa.privkey}")
    private String ecdsaPrivateKeyBase64;

    @Value("${digisign.ecdsa.pubkey}")
    private String ecdsaPublicKeyBase64;

    @Value("${digisign.rsa.privkey}")
    private String rsaPrivateKeyBase64;

    @Value("${digisign.rsa.pubkey}")
    private String rsaPublicKeyBase64;

    private KeyPair dsaKeyPair;

    private KeyPair ecdsaKeyPair;

    private KeyPair rsaKeyPair;

    @Autowired
    private DsaKeyGenerator dsaKeyGenerator;

    @Autowired
    private EcdsaKeyGenerator ecdsaKeyGenerator;

    @Autowired
    private RsaKeyGenerator rsaKeyGenerator;

    @PostConstruct
    public void init() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        dsaKeyPair = dsaKeyGenerator.fromBase64Strings(dsaPrivateKeyBase64, dsaPublicKeyBase64);
        ecdsaKeyPair = ecdsaKeyGenerator.fromBase64Strings(ecdsaPrivateKeyBase64, ecdsaPublicKeyBase64);
        rsaKeyPair = rsaKeyGenerator.fromBase64Strings(rsaPrivateKeyBase64, rsaPublicKeyBase64);
    }

    public KeyPair getDsaKeyPair() {
        return dsaKeyPair;
    }

    public KeyPair getEcdsaKeyPair() {
        return ecdsaKeyPair;
    }

    public KeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }

}
