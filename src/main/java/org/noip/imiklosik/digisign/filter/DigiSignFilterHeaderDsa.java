package org.noip.imiklosik.digisign.filter;

import org.noip.imiklosik.digisign.signer.DigiSigner;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderDsa;

public class DigiSignFilterHeaderDsa extends DigiSignFilter {

    private DigiSignerHeaderDsa digiSigner;

    public DigiSignFilterHeaderDsa(DigiSignerHeaderDsa digiSigner) {
        this.digiSigner = digiSigner;
    }

    @Override
    protected DigiSigner getSigner() {
        return digiSigner;
    }
}
