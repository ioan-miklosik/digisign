package org.noip.imiklosik.digisign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.noip.imiklosik.digisign.keygen.DsaKeyGenerator;
import org.noip.imiklosik.digisign.keygen.EcdsaKeyGenerator;
import org.noip.imiklosik.digisign.keygen.RsaKeyGenerator;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;


@RestController
@RequestMapping()
public class DigiSignController {

    @Value("${digisign.charencoding}")
    private String keyCharEnc;

    @Autowired
    private DsaKeyGenerator dsaKeyGenerator;

    @Autowired
    private EcdsaKeyGenerator ecdsaKeyGenerator;

    @Autowired
    private RsaKeyGenerator rsaKeyGenerator;

    @GetMapping("${digisign.path.newkey.dsa}")
    public String newDsaKey() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPair keyPair = dsaKeyGenerator.newKeyPair();
        return keyPairToJson(keyPair);
    }

    @GetMapping("${digisign.path.newkey.ecdsa}")
    public String newEcdsaKey() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair keyPair = ecdsaKeyGenerator.newKeyPair();
        return keyPairToJson(keyPair);
    }

    @GetMapping("${digisign.path.newkey.rsa}")
    public String newRsaKey() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPair keyPair = rsaKeyGenerator.newKeyPair();
        return keyPairToJson(keyPair);
    }

    @PostMapping("${digisign.path.sign}/${digisign.subpath.addheader}/dsa")
    public String signHeaderDsa(@RequestBody String payload){
        return payload;
    }

    @PostMapping("${digisign.path.sign}/${digisign.subpath.addheader}/ecdsa")
    public String signHeaderEcdsa(@RequestBody String payload){
        return payload;
    }

    @PostMapping("${digisign.path.sign}/${digisign.subpath.addheader}/rsa")
    public String signHeaderRsa(@RequestBody String payload){
        return payload;
    }

    @PostMapping("${digisign.path.sign}/${digisign.subpath.jose}")
    public String signJose(@RequestBody String payload){
        return payload;
    }

    private String keyPairToJson(KeyPair keyPair){
        String pubKeyBase64 = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()), Charset.forName(keyCharEnc));
        String privKeyBase64 = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()), Charset.forName(keyCharEnc));
        return "{" +
                "\"pub\": \"" + pubKeyBase64 + "\"," +
                "\"priv\": \"" + privKeyBase64 + "\"," +
        "}";
    }

}
