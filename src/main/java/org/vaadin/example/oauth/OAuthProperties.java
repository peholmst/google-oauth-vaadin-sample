package org.vaadin.example.oauth;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = true)
public class OAuthProperties {

    private String clientId;
    private String clientSecret;
    private String userAuthorizationUri;
    private String scopes;
    private String redirectUri;
    private String redirectPath;
    private String accessTokenUri;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUserAuthorizationUri() {
        return userAuthorizationUri;
    }

    public void setUserAuthorizationUri(String userAuthorizationUri) {
        this.userAuthorizationUri = userAuthorizationUri;
    }

    public Collection<String> getScopes() {
        return scopes == null ? Collections.emptyList() : Arrays.asList(scopes.split(","));
    }

    public void setScopes(Collection<String> scopes) {
        this.scopes = scopes == null ? null : String.join(",", scopes);
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }
}
