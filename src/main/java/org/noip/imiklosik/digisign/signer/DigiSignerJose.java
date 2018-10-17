package org.noip.imiklosik.digisign.signer;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;
import org.noip.imiklosik.digisign.util.BufferedHttpServletResponseWrapper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

@Component
public class DigiSignerJose implements DigiSigner {

    @Override
    public void sign(BufferedHttpServletResponseWrapper responseWrapper) throws IOException, NoSuchProviderException, NoSuchAlgorithmException {

    }

    @Override
    public boolean verify(HttpResponse response) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {

        return true;
    }

}
