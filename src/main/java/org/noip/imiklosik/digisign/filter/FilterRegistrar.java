package org.noip.imiklosik.digisign.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderDsa;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderEcdsa;
import org.noip.imiklosik.digisign.signer.DigiSignerHeaderRsa;
import org.noip.imiklosik.digisign.signer.DigiSignerJose;

@Configuration
public class FilterRegistrar {

    @Value("${digisign.path.sign}")
    String signPath;

    @Value("${digisign.subpath.addheader}")
    String addHeaderSubPath;

    @Value("${digisign.subpath.jose}")
    String joseSubPath;

    @Autowired
    private DigiSignerHeaderDsa signerAddHeaderDsa;

    @Autowired
    private DigiSignerHeaderEcdsa signerAddHeaderEcdsa;

    @Autowired
    private DigiSignerHeaderRsa signerAddHeaderRsa;

    @Autowired
    private DigiSignerJose signerJose;

    @Bean
    public FilterRegistrationBean<DigiSignFilter> registerDigiSignHeaderDsaFilter(){
        String mapToPath = "/" + signPath + "/" + addHeaderSubPath + "/dsa";
        return getRegistrarBean(mapToPath, new DigiSignFilterHeaderDsa(signerAddHeaderDsa));
    }

    @Bean
    public FilterRegistrationBean<DigiSignFilter> registerDigiSignHeaderEcdsaFilter(){
        String mapToPath = "/" + signPath + "/" + addHeaderSubPath + "/ecdsa";
        return getRegistrarBean(mapToPath, new DigiSignFilterHeaderEcdsa(signerAddHeaderEcdsa));
    }

    @Bean
    public FilterRegistrationBean<DigiSignFilter> registerDigiSignHeaderRsaFilter(){
        String mapToPath = "/" + signPath + "/" + addHeaderSubPath + "/rsa";
        return getRegistrarBean(mapToPath, new DigiSignFilterHeaderRsa(signerAddHeaderRsa));
    }

    @Bean
    public FilterRegistrationBean<DigiSignFilter> registerDigiSignJoseFilter(){
        String mapToPath = "/" + signPath + "/" + joseSubPath;
        return getRegistrarBean(mapToPath, new DigiSignFilterJose(signerJose));
    }

    private FilterRegistrationBean<DigiSignFilter> getRegistrarBean(String mapToPath, DigiSignFilter filter){
        FilterRegistrationBean<DigiSignFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(mapToPath);
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registrationBean;
    }
}
