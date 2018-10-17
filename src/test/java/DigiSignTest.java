import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderDsa;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderEcdsa;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderRsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DigiSignTest.class)
@ComponentScan({
        "org.noip.imiklosik.digisign.keygen",
        "org.noip.imiklosik.digisign.signer",
        "org.noip.imiklosik.digisign.util"
})
@PropertySource("classpath:application.properties")
public class DigiSignTest {

    @Value("${digisign.header}")
    private String digiSignHeaderName;

    @Autowired
    private DigiSignerHeaderDsa signerAddHeaderDsa;

    @Autowired
    private DigiSignerHeaderEcdsa signerAddHeaderEcDsa;

    @Autowired
    private DigiSignerHeaderRsa signerAddHeaderEcRsa;

    private static CloseableHttpClient httpClient;

    @BeforeClass
    public static void initClass(){
        Security.addProvider(new BouncyCastleProvider());
        httpClient = HttpClients.createDefault();
    }

    @AfterClass
    public static void cleanupClass() throws IOException {
        httpClient.close();
    }

    @Test
    public void addHeaderDsaTest() throws Exception {
        // given
        String json = loadJsonResource("payload1.json");
        StringEntity entity = new StringEntity(json);

        // when signed ok
        HttpPost request = new HttpPost("http://localhost:8080/sign/addheader/dsa");
        request.setEntity(entity);
        request.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(request);

        // then verifies ok
        assertTrue(signerAddHeaderDsa.verify(response));

        // when change payload a bit
        response.setEntity(new StringEntity(loadJsonResource("payload2.json")));

        // then doesn't verify
        assertFalse(signerAddHeaderDsa.verify(response));
    }

    @Test
    public void addHeaderEcdsaTest() throws Exception {
        String json = loadJsonResource("payload1.json");
        StringEntity entity = new StringEntity(json);

        // when signed ok
        HttpPost request = new HttpPost("http://localhost:8080/sign/addheader/ecdsa");
        request.setEntity(entity);
        request.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(request);

        // then verifies ok
        assertTrue(signerAddHeaderEcDsa.verify(response));

        // when change payload a bit
        response.setEntity(new StringEntity(loadJsonResource("payload2.json")));

        // then doesn't verify
        assertFalse(signerAddHeaderEcDsa.verify(response));
    }

    @Test
    public void addHeaderRsaTest() throws Exception {
        String json = loadJsonResource("payload1.json");
        StringEntity entity = new StringEntity(json);

        // when signed ok
        HttpPost request = new HttpPost("http://localhost:8080/sign/addheader/rsa");
        request.setEntity(entity);
        request.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(request);

        // then verifies ok
        assertTrue(signerAddHeaderEcRsa.verify(response));

        // when change payload a bit
        response.setEntity(new StringEntity(loadJsonResource("payload2.json")));

        // then doesn't verify
        assertFalse(signerAddHeaderEcRsa.verify(response));
    }

    private String loadJsonResource(String fileName){
        return new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream(fileName)
        )).lines().collect(Collectors.joining());
    }

}
