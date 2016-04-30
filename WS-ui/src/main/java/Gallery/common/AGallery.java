/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.common;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import db.ent.Document;
import java.io.File;
import java.util.List;
import org.superbapps.utils.vaadin.MyWindows.MyWindow;
import org.superbapps.utils.vaadin.Tables.IRefreshVisualContainer;
import org.vaadin.dialogs.ConfirmDialog;

public abstract class AGallery<T> implements IDocumentGallery<T> {

    protected UI ui;
    protected final IRefreshVisualContainer refreshVisualContainer;

    protected String mainImageFilePath;
    protected Document defaultGalleryDocument;

    protected List<Image> footerImages;

    protected Window W;

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

    public void setMainImageFilePath(String filePath) {
        this.mainImageFilePath = filePath;
    }

    public void setFooterImages(List<Image> images) {
        this.footerImages = images;
    }
    //</editor-fold>

    @Override
    public VerticalLayout createMainDocLayout(Component component) {
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

        W = new MyWindow(caption, VL_Root, 0, 0);
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
        for (final Object docBean : getAllDocumentsImageRepresentations(customObject)) {
            ((IDocBean)docBean).getImageRepresentation().setHeight(40, Sizeable.Unit.PIXELS);
            ((IDocBean)docBean).getImageRepresentation().setWidth(40, Sizeable.Unit.PIXELS);
            ((IDocBean)docBean).getImageRepresentation().setDescription("Click once to open the image.");

            ((IDocBean)docBean).getImageRepresentation().addClickListener((MouseEvents.ClickEvent event) -> {
                VL_MainImage.removeAllComponents();
                Image ni = new Image(null, ((IDocBean)docBean).getImageRepresentation().getSource());
                ni.setHeight(97, Sizeable.Unit.PERCENTAGE);
                ni.setWidth(97, Sizeable.Unit.PERCENTAGE);

                ni.setDescription("Double click to make this image default for this FS.");
                ni.addClickListener((MouseEvents.ClickEvent event1) -> {
                    if (event1.isDoubleClick()) {
                        ConfirmDialog d = ConfirmDialog.show(getUI(),
                                "Default Gallery Image",
                                "Do you want this selected image to be</br> the default image for the Gallery ?",
                                "Yes",
                                "No!", (ConfirmDialog dialog) -> {
                                    if (dialog.isConfirmed()) {
                                        try {
                                            // DS.getDocumentController().setDefaultFSImage(f, di.getDocument());
                                            setUpDefaultGalleryDocument((IDocBean) docBean);
                                            
                                            AGallery.this.refreshVisualContainer.refreshVisualContainer();
                                        } catch (Exception ex) {
                                            Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                                        }
                                    }
                                });
                        d.getCancelButton().setStyleName(ValoTheme.BUTTON_DANGER);
                        d.getCancelButton().setWidth(100, Sizeable.Unit.PIXELS);
                        d.getOkButton().setStyleName(ValoTheme.BUTTON_PRIMARY);
                        d.getOkButton().setWidth(100, Sizeable.Unit.PIXELS);
                        d.setContentMode(ConfirmDialog.ContentMode.HTML);
                        d.setHeight("16em");
                    }
                });

                VL_MainImage.addComponent(ni);
                VL_MainImage.setComponentAlignment(ni, Alignment.MIDDLE_CENTER);
            });

            HL_Images.addComponent(((IDocBean)docBean).getImageRepresentation());
        }
        //</editor-fold>

        UI.getCurrent().getUI().addWindow(W);
    }

    protected abstract void setUpDefaultGalleryDocument(IDocBean docBean) throws Exception;

}
