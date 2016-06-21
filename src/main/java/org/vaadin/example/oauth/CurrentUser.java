package org.vaadin.example.oauth;

import java.io.Serializable;
import java.util.Optional;

import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;

public class CurrentUser implements Serializable {

    private final OAuthProperties oauthProperties;

    public CurrentUser(OAuthProperties oauthProperties) {
        this.oauthProperties = oauthProperties;
    }

    private String authorizationCode;
    private String accessToken;

    private transient GoogleCredential googleCredential;
    private transient Person profile;

    public boolean isAuthenticated() {
        return authorizationCode != null;
    }

    public void setAuthorizationCode(String authorizationCode) {
        if (authorizationCode != null) {
            try {
                TokenResponse response = new AuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(),
                    new GenericUrl(oauthProperties.getAccessTokenUri()), authorizationCode)
                        .setClientAuthentication(new ClientParametersAuthentication(oauthProperties.getClientId(),
                            oauthProperties.getClientSecret()))
                        .setRedirectUri(oauthProperties.getRedirectUri()).execute();
                this.accessToken = response.getAccessToken();
                this.authorizationCode = authorizationCode;
            } catch (Exception ex) {
                LoggerFactory.getLogger(getClass()).error("Error retrieving access token", ex);
            }
        } else {
            this.accessToken = null;
            this.authorizationCode = null;
            this.googleCredential = null;
            this.profile = null;
        }
    }

    public Optional<GoogleCredential> getCredential() {
        if (googleCredential == null && accessToken != null) {
            googleCredential = new GoogleCredential().setAccessToken(accessToken);
        }
        return Optional.ofNullable(googleCredential);
    }

    public Optional<Person> getProfile() {
        if (profile == null) {
            profile = getCredential().map(credential -> {
                Plus plus = new Plus(new NetHttpTransport(), new JacksonFactory(), credential);
                try {
                    return plus.people().get("me").execute();
                } catch (Exception ex) {
                    LoggerFactory.getLogger(getClass()).error("Error retrieving user profile", ex);
                    return null;
                }
            }).orElse(null);
        }
        return Optional.ofNullable(profile);
    }
}
