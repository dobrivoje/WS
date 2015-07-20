package Views.General;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@PreserveOnRefresh
public class EmptyView extends VerticalLayout implements View {
    
    public EmptyView() {
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
