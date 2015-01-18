package org.superb.apps.ws.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends VerticalLayout implements View {

    public ErrorView() {
        setMargin(true);
        setSpacing(true);

        addComponent(new Label("Oops. The view you tried to navigate to doesn't exist."));
        addComponents(new Label(""), new Button("Main page", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(ConsoleView.class.getSimpleName());
            }
        }));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
