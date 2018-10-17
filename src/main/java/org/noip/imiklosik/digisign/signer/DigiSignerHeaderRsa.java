package org.noip.imiklosik.digisign.signer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.noip.imiklosik.digisign.util.KeyStore;

import java.security.KeyPair;

@Component
public class DigiSignerHeaderRsa extends DigiSignerHeader {

    @Value("${digisign.rsa.signature.algorithm}")
    private String signatureAlgorithm;

    @Value("${digisign.rsa.signature.provider}")
    private String signatureProvider;

    @Value("${digisign.header}")
    private String digiSignHeaderName;

    @Autowired
    private KeyStore keyStore;

    @Override
    protected KeyPair getKeyPair() {
        return keyStore.getRsaKeyPair();
    }

    @Override
    protected String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    @Override
    protected String getSignatureProvider() {
        return signatureProvider;
    }

    @Override
    protected String getHeaderName() {
        return digiSignHeaderName;
    }
}
