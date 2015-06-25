/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageGallery;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public abstract class DocumentGallery<T> implements IDocumentGallery<T> {

    protected UI ui;
    protected final IRefreshVisualContainer refreshVisualContainer;

    //<editor-fold defaultstate="collapsed" desc="konstruktor i getter/setter">
    public DocumentGallery(UI ui, IRefreshVisualContainer refreshVisualContainer) {
        this.ui = ui;
        this.refreshVisualContainer = refreshVisualContainer;
    }
    
    public UI getUI() {
        return this.ui;
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
    }
    //</editor-fold>

    @Override
    public VerticalLayout createMainDocument(Component component) {
        HorizontalLayout HL = new HorizontalLayout(component);
        HL.setComponentAlignment(component, Alignment.MIDDLE_CENTER);

        VerticalLayout VL = new VerticalLayout(HL);
        VL.setComponentAlignment(HL, Alignment.MIDDLE_CENTER);

        return VL;
    }
}
