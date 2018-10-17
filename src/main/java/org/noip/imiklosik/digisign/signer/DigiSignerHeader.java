package org.noip.imiklosik.digisign.signer;

import org.apache.http.HttpResponse;
import org.noip.imiklosik.digisign.util.BufferedHttpServletResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;


public abstract class DigiSignerHeader implements DigiSigner {

    protected abstract KeyPair getKeyPair();
    protected abstract String getSignatureAlgorithm();
    protected abstract String getSignatureProvider();
    protected abstract String getHeaderName();

    @Override
    public void sign(BufferedHttpServletResponseWrapper responseWrapper)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, IOException {
        byte[] payload = responseWrapper.buffer();
        PrivateKey privateKey = getKeyPair().getPrivate();

        Signature signature = Signature.getInstance(getSignatureAlgorithm(), getSignatureProvider());

        signature.initSign(privateKey);
        signature.update(payload);
        byte[] signatureBytes = signature.sign();
        String base64Signature = new String(Base64.getEncoder().encode(signatureBytes));

        HttpServletResponse realResponse = (HttpServletResponse) responseWrapper.getResponse();
        realResponse.setHeader(getHeaderName(), base64Signature);
        try(OutputStream out = realResponse.getOutputStream()) {
            out.write(payload);
        }
    }

    @Override
    public boolean verify(HttpResponse response) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        if(!response.containsHeader(getHeaderName())){
            return false;
        }

        // get signature to check
        String base64Signature = response.getHeaders(getHeaderName())[0].getValue();
        byte[] checkSig = Base64.getDecoder().decode(base64Signature.getBytes());

        //get the pub key
        PublicKey pubKey = getKeyPair().getPublic();

        // As with signature generation, a signature is verified by using an instance of the Signature class.
        //   You need to create a Signature object that uses the same signature algorithm as was used to generate
        //   the signature. The algorithm used by the GenSig program was the SHA1withDSA algorithm from the SUN provider.
        Signature sig = Signature.getInstance(getSignatureAlgorithm(), getSignatureProvider());

        // Next, you need to initialize the Signature object. The initialization method for verification requires the public key.
        sig.initVerify(pubKey);

        // Supply the Signature Object With the Data to be Verified
        sig.update(new BufferedInputStream(response.getEntity().getContent()).readAllBytes());

        // verify the signature
        return sig.verify(checkSig);
    }

}
