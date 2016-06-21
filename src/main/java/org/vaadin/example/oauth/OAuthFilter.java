package org.vaadin.example.oauth;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;

public class OAuthFilter implements Filter {

    private final CurrentUser currentUser;
    private final OAuthProperties oauthProperties;

    public OAuthFilter(CurrentUser currentUser, OAuthProperties oauthProperties) {
        this.currentUser = currentUser;
        this.oauthProperties = oauthProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().equals(oauthProperties.getRedirectPath())) {
            StringBuffer fullUrlBuf = request.getRequestURL();
            if (request.getQueryString() != null) {
                fullUrlBuf.append('?').append(request.getQueryString());
            }
            AuthorizationCodeResponseUrl authResponse = new AuthorizationCodeResponseUrl(fullUrlBuf.toString());
            currentUser.setAuthorizationCode(authResponse.getCode());
            if (currentUser.isAuthenticated()) {
                if (authResponse.getState() != null) {
                    response.sendRedirect(authResponse.getState());
                } else {
                    response.sendRedirect("/ui");
                }
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sorry, you don't have access to this resource");
            }
        } else if (currentUser.isAuthenticated()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String authUrl = new AuthorizationCodeRequestUrl(oauthProperties.getUserAuthorizationUri(),
                oauthProperties.getClientId()).setScopes(oauthProperties.getScopes())
                    .setRedirectUri(oauthProperties.getRedirectUri()).setState(request.getRequestURL().toString())
                    .build();
            response.sendRedirect(authUrl);
        }
    }

    @Override
    public void destroy() {
    }
}
