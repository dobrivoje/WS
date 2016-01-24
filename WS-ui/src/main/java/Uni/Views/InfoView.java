package Uni.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import Views.View_Console;

public class InfoView extends VerticalLayout implements View {

    public InfoView() {
        setMargin(true);
        setSpacing(true);

        addComponent(new Label("No need for Login Page, as you're authenticated already."));
        addComponents(new Label(""), new Button("Back to Secure page", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(View_Console.class.getSimpleName());
            }
        }));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
