package com.typewars.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PrivateAuthorizationFilter implements Filter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String KEY = "OPF1A1r3f23f2OPmspJFSAPjFAKJQIWFfewJIOijoafoOIwfweOJasj10re";
    private static final String PRIVATE = "private";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doHttpFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    private void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!shouldApply(request)) {
            filterChain.doFilter(request, response);

            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        String expectedAuthorizationHeader = String.format("Basic %s", KEY);

        if (authorizationHeader != null && authorizationHeader.equals(expectedAuthorizationHeader)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean shouldApply(HttpServletRequest request) {
        return request.getRequestURI().contains(PRIVATE);
    }

    @Override
    public void destroy() {

    }
}
