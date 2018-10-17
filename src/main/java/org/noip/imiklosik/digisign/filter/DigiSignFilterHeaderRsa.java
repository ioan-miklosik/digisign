package org.noip.imiklosik.digisign.filter;

import org.noip.imiklosik.digisign.signer.DigiSigner;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderRsa;

public class DigiSignFilterHeaderRsa extends DigiSignFilter {

    private DigiSignerHeaderRsa digiSigner;

    public DigiSignFilterHeaderRsa(DigiSignerHeaderRsa digiSigner) {
        this.digiSigner = digiSigner;
    }

    @Override
    protected DigiSigner getSigner() {
        return digiSigner;
    }
}
