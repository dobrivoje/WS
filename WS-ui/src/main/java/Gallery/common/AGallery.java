/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.common;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import java.io.File;
import java.util.List;
import org.superbapps.utils.vaadin.MyWindows.MyWindow;
import org.superbapps.utils.vaadin.Tables.IRefreshVisualContainer;

public abstract class AGallery<T> implements IDocumentGallery<T> {

    protected UI ui;
    protected final IRefreshVisualContainer refreshVisualContainer;
    protected String mainImageFilePath;
    protected List<Image> footerImages;

    //<editor-fold defaultstate="collapsed" desc="konstruktor i getter/setter">
    public AGallery(UI ui, IRefreshVisualContainer refreshVisualContainer) {
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

    @Override
    public void openDocumentGalleryWindow(String caption, T customObject) {
        VerticalLayout VL_Root = new VerticalLayout();
        VL_Root.setSpacing(true);

        final Window W = new MyWindow(caption, VL_Root, 0, 0);
        W.setModal(true);
        W.setStyleName(Reindeer.WINDOW_BLACK);

        final VerticalLayout VL_MainImage = new VerticalLayout();
        VL_MainImage.setSizeFull();

        final HorizontalLayout HL_Footer = new HorizontalLayout();
        final HorizontalLayout HL_Images = new HorizontalLayout();
        HL_Footer.addComponents(HL_Images);

        // na kraj dodaj i new VerticalLayout(), da sličice ne bi otišle na "dno" footer-a,...
        VL_Root.addComponents(VL_MainImage, HL_Footer, new VerticalLayout());
        VL_Root.setExpandRatio(VL_MainImage, 1);
        VL_Root.setComponentAlignment(VL_MainImage, Alignment.MIDDLE_CENTER);
        VL_Root.setComponentAlignment(HL_Footer, Alignment.MIDDLE_CENTER);

        //<editor-fold defaultstate="collapsed" desc="Glavna slika">
        Image defaultImage = new Image(null, new FileResource(new File(mainImageFilePath)));
        defaultImage.setHeight(97, Sizeable.Unit.PERCENTAGE);
        defaultImage.setWidth(97, Sizeable.Unit.PERCENTAGE);

        VL_MainImage.addComponent(defaultImage);
        VL_MainImage.setComponentAlignment(defaultImage, Alignment.MIDDLE_CENTER);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Sve slike u footer-u.">
        
        //</editor-fold>
        UI.getCurrent().getUI().addWindow(W);
    }

    public void setMainImageFilePath(String filePath) {
        this.mainImageFilePath = filePath;
    }

    public void setFooterImages(List<Image> images) {
        this.footerImages = images;
    }

}
