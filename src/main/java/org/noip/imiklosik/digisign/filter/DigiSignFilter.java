package org.noip.imiklosik.digisign.filter;

import org.noip.imiklosik.digisign.signer.DigiSigner;
import org.noip.imiklosik.digisign.util.BufferedHttpServletResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class DigiSignFilter implements Filter {

    protected abstract DigiSigner getSigner();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        // wrap response to buffer written body
        BufferedHttpServletResponseWrapper wrapper = new BufferedHttpServletResponseWrapper((HttpServletResponse)response);

        // send request to the controller
        chain.doFilter(request, wrapper);

        // sign the response
        try {
            getSigner().sign(wrapper);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
    }

}


