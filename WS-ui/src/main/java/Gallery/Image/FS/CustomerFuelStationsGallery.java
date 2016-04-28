/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.Image.FS;

import Gallery.common.AGallery;
import Gallery.common.IDocBean;
import Gallery.common.Notif;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import db.ent.Document;
import db.ent.Fuelstation;
import java.io.File;
import java.util.Date;
import java.util.List;
import static Main.MyUI.DS;
import java.util.ArrayList;
import org.superbapps.utils.common.Enums.ImageTypes;
import org.superbapps.utils.common.os.OS;
import org.superbapps.utils.translators.CharsAdapter;
import org.superbapps.utils.vaadin.Tables.IRefreshVisualContainer;
import org.superbapps.utils.vaadin.Views.Layout_InlineCSS;
import org.superbapps.utils.vaadin.files.uploader.UploadReceiver;

/**
 *
 * @author root
 */
public class CustomerFuelStationsGallery extends AGallery<Fuelstation> {

    public CustomerFuelStationsGallery(UI ui, IRefreshVisualContainer refreshVisualContainer) {
        super(ui, refreshVisualContainer);
    }

    //<editor-fold defaultstate="collapsed" desc="createDocument">
    @Override
    public Image createDocument(Fuelstation f, float height, float width, boolean defaultDocument) {
        DocFs dfs = new DocFs(f);
        Image image = dfs.getImageRepresentation();

        image.setHeight(height, Unit.PIXELS);
        image.setWidth(width, Unit.PIXELS);
        image.addClickListener((MouseEvents.ClickEvent event) -> {
            if (event.isDoubleClick()) {
                openDocumentGalleryWindow("Fuelstation - " + f.getName(), f);
            }
        });

        if (defaultDocument) {
            // DS.getDocumentController().setDefaultFSImage(f, di.getDocument());
            setUpDefaultGalleryDocument(new DocFs(f, dfs.getDocument()));
        }

        return image;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createDocumentGallery">
    @Override
    public VerticalLayout createDocumentGallery(Fuelstation f, boolean uploaderOnForm) {
        final String imgGalleryLoc = DS.getGalleryController().getDefaultImageGallery().getStoreLocation();
        final String docType = DS.getDocumentTypeController().getImageDocumentType().getDocType();
        final Notif notif = new Notif();

        final String absPath = imgGalleryLoc + CharsAdapter.safeAdapt(f.getName()) + OS.getSeparator() + docType + OS.getSeparator();

        //<editor-fold defaultstate="collapsed" desc="Kreiraj glavni layout">
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSpacing(true);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Kreiraj uploader">
        UploadReceiver ur = new UploadReceiver(null,
                imgGalleryLoc
                //adaptiraj filename tako da sadrži samo slova eng. alfabeta !
                + CharsAdapter.safeAdapt(f.getName())
                + OS.getSeparator()
                + docType
                + OS.getSeparator()
        );
        // proveri da li postoji direktorijum sa imenom stanice :
        ur.checkAndMakeRootDir(imgGalleryLoc + CharsAdapter.safeAdapt(f.getName()));
        final Upload imageUploader = new Upload(null, ur);

        imageUploader.addFinishedListener((Upload.FinishedEvent event) -> {
            notif.setMsg(event.getFilename());

            Document newDocument;
            try {
                if (new File(absPath + event.getFilename()).length() < 1200L) {
                    throw new Exception("File Size Too Small !");
                }

                if (event.getFilename().trim().isEmpty()) {
                    throw new Exception("Nothing Selected !");
                }

                newDocument = DS.getDocumentController().addNewDocument(
                        DS.getGalleryController().getDefaultImageGallery(),
                        event.getFilename(),
                        null,
                        CharsAdapter.safeAdapt(f.getName()),
                        new Date(),
                        docType
                );

                int priority = DS.getDocumentController().getAllFSDocuments(f).size();
                DS.getDocumentController().addNewFSDocument(
                        f, newDocument, new Date(), true, 1 + priority);

                CustomerFuelStationsGallery.this.refreshVisualContainer.refreshVisualContainer();

                Notification.show("File name : ", notif.getMsg(), Notification.Type.HUMANIZED_MESSAGE);
            } catch (Exception ex) {
                Notification.show("Error.", "File Upload Failed !\n"
                        + ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Kreiraj sličice ako ih ima">
        CssLayout FSLowerImagesCssLayout = new Layout_InlineCSS();

        for (final Object di : getAllDocuments(f)) {
            ((IDocBean) di).getImageRepresentation().setHeight(40, Unit.PIXELS);
            ((IDocBean) di).getImageRepresentation().setWidth(40, Unit.PIXELS);

            ((IDocBean) di).getImageRepresentation().addClickListener((MouseEvents.ClickEvent event) -> {
                if (event.isDoubleClick()) {
                    openDocumentGalleryWindow("Fuelstation - " + f.getName(), f);
                }
            });

            FSLowerImagesCssLayout.addComponent(((IDocBean) di).getImageRepresentation());
        }
        //</editor-fold>

        rootLayout.addComponents(createMainDocLayout(createDocument(f, 200, 200, false)));

        if (uploaderOnForm) {
            rootLayout.addComponents(imageUploader);
        }

        rootLayout.addComponent(FSLowerImagesCssLayout);

        return rootLayout;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="openDocumentGalleryWindow">
    @Override
    public void openDocumentGalleryWindow(String caption, Fuelstation f) {
        setMainImageFilePath(DS.getDocumentController().getDefaultFSImage(f).getAbsolutePath(true));
        super.openDocumentGalleryWindow(caption, f);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllDocuments">
    //</editor-fold>
    @Override
    protected void setUpDefaultGalleryDocument(IDocBean<Fuelstation> docFSBean) {
        try {
            DS.getDocumentController().setDefaultFSImage(docFSBean.getBean(), docFSBean.getDocument());
        } catch (Exception e) {
        }
    }

    @Override
    public List getAllDocuments(Fuelstation f) {
        List<IDocBean> LI = new ArrayList<>();

        for (Document d : DS.getDocumentController().getAllFSDocuments(f)) {
            if (ImageTypes.contains(d.getName())) {
                FileResource fr = new FileResource(new File(d.getAbsolutePath(true)));

                if (fr.getSourceFile().exists()) {
                    LI.add(new DocImg(new Image("", fr), d));
                }
            }
        }

        return LI;
    }

}
