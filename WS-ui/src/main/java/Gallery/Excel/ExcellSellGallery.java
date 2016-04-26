/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.Excel;

import Gallery.common.AGallery;
import Gallery.common.Notif;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import db.ent.Document;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static Main.MyUI.DS;
import db.ent.TMarginWHS;
import java.util.stream.Collectors;
import org.superbapps.utils.common.os.OS;
import org.superbapps.utils.translators.CharsAdapter;
import org.superbapps.utils.vaadin.MyWindows.MyWindow;
import org.superbapps.utils.vaadin.Tables.IRefreshVisualContainer;
import org.superbapps.utils.vaadin.Views.Layout_InlineCSS;
import org.superbapps.utils.vaadin.files.uploader.UploadReceiver;

/**
 *
 * @author root
 */
public class ExcellSellGallery extends AGallery<TMarginWHS> {

    public ExcellSellGallery(UI ui, IRefreshVisualContainer refreshVisualContainer) {
        super(ui, refreshVisualContainer);
    }

    //<editor-fold defaultstate="collapsed" desc="createDocument">
    @Override
    public Image createDocument(TMarginWHS tmwhs, float height, float width) {
        Document defaultDoc = DS.getDocumentController().getByID(tmwhs.getId().longValue());

        Image image;

        try {
            image = new Image(null, new FileResource(new File(defaultDoc.getAbsolutePath(true))));
            image.setDescription("Double click to open\nan Gallery.");

            image.addClickListener((MouseEvents.ClickEvent event) -> {
                if (event.isDoubleClick()) {
                    openDocumentGalleryWindow("Document - ", tmwhs);
                }
            });
        } catch (Exception e) {
            image = new Image(null, new ThemeResource("img/fs/1.png"));
        }

        image.setHeight(height, Unit.PIXELS);
        image.setWidth(width, Unit.PIXELS);

        return image;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createDocumentGallery">
    @Override
    public VerticalLayout createDocumentGallery(TMarginWHS tmwhs, boolean uploaderOnForm) {
        final String imgGalleryLoc = DS.getGalleryController().getDefaultImageGallery().getStoreLocation();
        final String docType = DS.getDocumentTypeController().getImageDocumentType().getDocType();
        final Notif notif = new Notif();

        final String absPath = imgGalleryLoc + CharsAdapter.safeAdapt(tmwhs.getDocumentNo()) + OS.getSeparator() + docType + OS.getSeparator();

        //<editor-fold defaultstate="collapsed" desc="Kreiraj glavni layout">
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSpacing(true);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Kreiraj uploader">
        UploadReceiver ur = new UploadReceiver(null,
                imgGalleryLoc
                //adaptiraj filename tako da sadrži samo slova eng. alfabeta !
                + CharsAdapter.safeAdapt(tmwhs.getDocumentNo())
                + OS.getSeparator()
                + docType
                + OS.getSeparator()
        );
        // proveri da li postoji direktorijum sa imenom stanice :
        ur.checkAndMakeRootDir(imgGalleryLoc + CharsAdapter.safeAdapt(tmwhs.getDocumentNo()));
        final Upload imageUploader = new Upload(null, ur);

        imageUploader.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(Upload.FinishedEvent event) {
                notif.setMsg(event.getFilename());

                Document newDocument;
                try {
                    if (event.getFilename().trim().isEmpty()) {
                        throw new Exception("Nothing Selected !");
                    }

                    newDocument = DS.getDocumentController().addNewDocument(
                            DS.getGalleryController().getDefaultImageGallery(),
                            event.getFilename(),
                            null,
                            CharsAdapter.safeAdapt(tmwhs.getDocumentNo()),
                            new Date(),
                            docType
                    );

                    // int priority = DS.getDocumentController().getAllFSDocuments(tmwhs).size();
                    DS.getDocumentController().addNew(newDocument);

                    ExcellSellGallery.this.refreshVisualContainer.refreshVisualContainer();

                    Notification.show("File name : ", notif.getMsg(), Notification.Type.HUMANIZED_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error.", "File Upload Failed !\n"
                            + ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        }
        );
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Kreiraj footer sa ikonicama excel fajlova ako ih ima">
        CssLayout FSLowerImagesCssLayout = new Layout_InlineCSS();

        Image excelImg = new Image(null, new ThemeResource("img/excel1.png"));
        excelImg.setHeight(40, Unit.PIXELS);
        excelImg.setWidth(40, Unit.PIXELS);

        for (final DocFile df : getAllDocuments(tmwhs)) {
            excelImg.addClickListener((MouseEvents.ClickEvent event) -> {
                if (event.isDoubleClick()) {
                    openDocumentGalleryWindow("Excel Document - " + tmwhs.getDocumentNo(), tmwhs);
                }
            });

            FSLowerImagesCssLayout.addComponent(excelImg);
        }
        //</editor-fold>

        rootLayout.addComponents(super.createMainDocument(createDocument(tmwhs, 200, 200)));

        if (uploaderOnForm) {
            rootLayout.addComponents(imageUploader);
        }

        rootLayout.addComponent(FSLowerImagesCssLayout);

        return rootLayout;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="openDocumentGalleryWindow">
    @Override
    public void openDocumentGalleryWindow(String caption, TMarginWHS tm) {
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
        Image excelImage = new Image(null, new ThemeResource("img/excel1.png"));
        excelImage.setHeight(97, Unit.PERCENTAGE);
        excelImage.setWidth(97, Unit.PERCENTAGE);

        VL_MainImage.addComponent(excelImage);
        VL_MainImage.setComponentAlignment(excelImage, Alignment.MIDDLE_CENTER);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Sve sličice ostalih dokumenata.">
        for (final DocFile df : getAllDocuments(tm)) {
            excelImage.setHeight(40, Unit.PIXELS);
            excelImage.setWidth(40, Unit.PIXELS);
            excelImage.setDescription("Click to open an excel.");

            HL_Images.addComponent(excelImage);
        }
        //</editor-fold>

        UI.getCurrent().getUI().addWindow(W);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllDocuments">
    @Override
    public List<DocFile> getAllDocuments(TMarginWHS tm) {
        List LI = new ArrayList<>();

        for (Document d : DS.getDocumentController().getAll().stream()
                .filter((Document p) -> p.getName().contains("xls"))
                .collect(Collectors.toList())) {

            FileResource fr = new FileResource(new File(d.getAbsolutePath(true)));

            if (fr.getSourceFile().exists()) {
                LI.add(new DocFile(fr.getSourceFile(), d));
            }
        }

        return LI;
    }
    //</editor-fold>

}
