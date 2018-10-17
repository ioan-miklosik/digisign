package org.noip.imiklosik.digisign.signer;

import org.apache.http.HttpResponse;
import org.noip.imiklosik.digisign.util.BufferedHttpServletResponseWrapper;

public interface DigiSigner {

    void sign(BufferedHttpServletResponseWrapper responseWrapper) throws Exception;

    boolean verify(HttpResponse response) throws Exception;

}
