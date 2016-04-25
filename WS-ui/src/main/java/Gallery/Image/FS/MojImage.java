/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.Image.FS;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Image;
import org.vaadin.peter.contextmenu.ContextMenu;

/**
 *
 * @author root
 */
public class MojImage extends Image {

    private final ContextMenu cm = new ContextMenu();

    public MojImage(Resource source) {
        super(null, source);
        initCM();
    }

    private void initCM() {
        cm.addItem("Default Image");
        cm.addItem("Delete Image");
        cm.setAsContextMenuOf(this);
        // cm.setOpenAutomatically(false);
        cm.addContextMenuComponentListener((ContextMenu.ContextMenuOpenedOnComponentEvent event) -> {
            //cm.open(event.getX(), event.getY());
        });
    }

    public MojImage(float height, float width, Sizeable.Unit unit) {
        setHeight(height, unit);
        setWidth(width, unit);
        initCM();
    }

    @Override
    public void addClickListener(MouseEvents.ClickListener listener) {
        super.addClickListener((MouseEvents.ClickEvent event) -> {
            if (event.getButton().equals(MouseEventDetails.MouseButton.RIGHT)) {
                cm.open(event.getClientX(), event.getClientY());
            }
        });
    }
}
