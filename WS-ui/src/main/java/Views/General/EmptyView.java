package Views.General;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class EmptyView extends VerticalLayout implements View {
    
    public EmptyView() {
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
