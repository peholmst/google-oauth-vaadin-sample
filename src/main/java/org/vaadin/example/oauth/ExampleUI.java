package org.vaadin.example.oauth;

import com.google.api.services.plus.model.Person;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringUI(path = "/ui")
public class ExampleUI extends UI {

    @Autowired
    CurrentUser currentUser;

    @Override
    protected void init(VaadinRequest request) {
        Optional<Person> profile = currentUser.getProfile();
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        setContent(layout);
        if (!profile.isPresent()) {
            layout.addComponent(new Label("No profile available"));
        } else {
            Person p = profile.get();
            if (p.getImage() != null) {
                Image photo = new Image();
                photo.setSource(new ExternalResource(p.getImage().getUrl()));
                layout.addComponent(photo);
            }
            layout.addComponent(new Label(p.getDisplayName()));
        }
        Button logout = new Button("Logout", evt -> {
            getSession().getSession().invalidate();
            getPage().reload();
        });
        layout.addComponent(logout);
    }
}
