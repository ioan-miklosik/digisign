package org.noip.imiklosik.digisign;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.security.Security;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application {

    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());

        SpringApplication.run(Application.class);
    }

}
