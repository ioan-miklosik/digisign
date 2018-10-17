package org.noip.imiklosik.digisign.filter;

import org.noip.imiklosik.digisign.signer.DigiSigner;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderEcdsa;

public class DigiSignFilterHeaderEcdsa extends DigiSignFilter {

    private DigiSignerHeaderEcdsa digiSigner;

    public DigiSignFilterHeaderEcdsa(DigiSignerHeaderEcdsa digiSigner) {
        this.digiSigner = digiSigner;
    }

    @Override
    protected DigiSigner getSigner() {
        return digiSigner;
    }
}
