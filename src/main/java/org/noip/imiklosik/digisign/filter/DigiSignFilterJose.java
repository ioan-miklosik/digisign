package org.noip.imiklosik.digisign.filter;

import org.noip.imiklosik.digisign.signer.DigiSigner;

public class DigiSignFilterJose extends DigiSignFilter {

    private DigiSigner digiSigner;

    public DigiSignFilterJose(DigiSigner digiSigner) {
        this.digiSigner = digiSigner;
    }

    @Override
    protected DigiSigner getSigner() {
        return digiSigner;
    }

}
