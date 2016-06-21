# Vaadin and Google OAuth 2.0 Sample Application

This is a small example that demonstrates how to use Google OAuth 2.0 in a Vaadin 7 (and Spring Boot) application.

After authentication, the user's Google+ profile is fetched and some details are shown in the Vaadin UI.

This application will not work with websocket based server push.

## Before you start

You have to enable Google authentication and APIs before you can try out this application:

* Create a new project in the [Google Developer Console](https://console.developers.google.com). 
* Create new credentials for a *web application* and make a note of the *Client ID* and *Client Secret* values.
* Enable the Google+ API for your project.
* Open [application.properties](src/main/resources/application.properties) and fill in the *Client ID* and *Client Secret*.

# Running the application

You can start up the application using Maven: `$ mvn spring-boot:run`

Then point your browser to [http://localhost:8080/ui](http://localhost:8080/ui).

