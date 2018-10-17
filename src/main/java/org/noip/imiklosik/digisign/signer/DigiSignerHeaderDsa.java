package org.noip.imiklosik.digisign.signer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.noip.imiklosik.digisign.util.KeyStore;

import java.security.KeyPair;

@Component
public class DigiSignerHeaderDsa extends DigiSignerHeader {

    @Value("${digisign.dsa.signature.algorithm}")
    private String signatureAlgorithm;

    @Value("${digisign.dsa.signature.provider}")
    private String signatureProvider;

    @Value("${digisign.header}")
    private String digiSignHeaderName;

    @Autowired
    private KeyStore keyStore;

    @Override
    protected KeyPair getKeyPair() {
        return keyStore.getDsaKeyPair();
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
