/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import db.ent.Document;
import db.ent.Fuelstation;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dobrivoje.utils.chars.translators.CharsAdapter;
import org.superb.apps.utilities.Enums.ImageTypes;
import org.superb.apps.utilities.files.uploader.UploadReceiver;
import org.superb.apps.utilities.os.OS;
import org.superb.apps.utilities.vaadin.MyWindows.MyWindow;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

/**
 *
 * @author root
 * @param <T>
 */
public abstract class GENTable<T> extends Table implements IRefreshVisualContainer {

    protected List<String> tableColumnsID;
    protected final BeanItemContainer<T> beanContainer;
    protected List list;

    //<editor-fold defaultstate="collapsed" desc="Notif">
    class Notif {

        private String msg;

        public Notif() {
            msg = "";
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    };
    //</editor-fold>

    public GENTable(BeanItemContainer<T> beanContainer, List list) {
        this.beanContainer = beanContainer;
        this.list = list;

        updateBeanItemContainer(list);
        setContainerDataSource(beanContainer);

        setSizeFull();

        setPageLength(20);
        setCacheRate(4);
        setSelectable(true);
        setColumnCollapsingAllowed(true);
        setImmediate(true);
    }

    protected final void updateBeanItemContainer(List list) {
        if (this.beanContainer.size() > 0) {
            this.beanContainer.removeAllItems();
        }
        this.beanContainer.addAll(list);
    }

    @Override
    public void refreshVisualContainer() {
        updateBeanItemContainer(this.list);
        markAsDirtyRecursive();
    }

    /**
     * Metod za kreiranje pogleda na tabelu. Biranjem kolona, biramo i prikaz
     * kolona tabele, čime pravimo različite "poglede" nad istom tabelom. Ovim
     * metodom osim biznis funkcionalnosti, možemo kontrolisati i performanse
     * iscrtavanja tabele sa velikom količinom podataka.
     *
     * @param columns
     */
    protected void setTableView(String... columns) {
        for (String c : tableColumnsID) {
            setColumnCollapsed(c, true);
        }

        for (String c : columns) {
            setColumnCollapsed(c, false);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Image i Gallery">
    protected Image createImage(final Fuelstation fuelstation, float height, float width) {
        Document defaultImage = DS.getDocumentController().getDefaultFSImage(fuelstation);

        final Image image;

        if (defaultImage != null) {
            image = new Image(null, new FileResource(new File(defaultImage.getAbsolutePath(true))));
        } else {
            image = new Image(null, new ThemeResource("img/fs/1.png"));
        }

        image.setDescription("Double click to open the image.");

        image.setHeight(height, Unit.PIXELS);
        image.setWidth(width, Unit.PIXELS);

        image.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                if (event.isDoubleClick()) {
                    openFSGalleryWindow2("Fuelstation - " + fuelstation.getName(), fuelstation);
                }
            }
        });

        return image;
    }

    protected VerticalLayout createImageGallery(final Fuelstation f) {
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

        imageUploader.addFinishedListener(
                new Upload.FinishedListener() {
                    @Override
                    public void uploadFinished(Upload.FinishedEvent event) {
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

                            refreshVisualContainer();

                            Notification.show("File name : ", notif.getMsg(), Notification.Type.HUMANIZED_MESSAGE);
                        } catch (Exception ex) {
                            Notification.show("Error.", "File Upload Failed !\n"
                                    + ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                        }
                    }
                }
        );
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Kreiraj glavnu sliku">
        Image defaultFSImage = createImage(f, 200, 200);
        VerticalLayout mainImageLayout = new VerticalLayout(defaultFSImage, imageUploader, imageUploader);

        mainImageLayout.setSpacing(true);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Kreiraj sličice ako ih ima">
        CssLayout FSLowerImagesCssLayout = new CssLayout() {
            @Override
            protected String getCss(Component c) {
                return "display: inline-block;";
            }
        };

        for (final Image si : getAllFSImages(f)) {
            si.setHeight(40, Unit.PIXELS);
            si.setWidth(40, Unit.PIXELS);

            si.addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    if (event.isDoubleClick()) {
                        openFSGalleryWindow2("Fuelstation - " + f.getName(), f);
                    }
                }
            });

            FSLowerImagesCssLayout.addComponent(si);
        }
        //</editor-fold>

        rootLayout.addComponents(mainImageLayout);
        rootLayout.addComponent(FSLowerImagesCssLayout);

        return rootLayout;
    }

    private void openFSGalleryWindow2(String caption, Fuelstation f) {
        VerticalLayout VLI = new VerticalLayout();
        VLI.setSpacing(true);

        final VerticalLayout VL_MainImage = new VerticalLayout();
        VL_MainImage.setSizeFull();

        final CssLayout CSS_SubImages = new CssLayout() {
            @Override
            public void setSizeFull() {
                super.setSizeFull();
            }
            
            @Override
            protected String getCss(Component c) {
                return "display: inline-block;";
            }
        };
        
        VLI.addComponents(VL_MainImage, CSS_SubImages);
        VLI.setExpandRatio(VL_MainImage, 2);
        VLI.setComponentAlignment(VL_MainImage, Alignment.MIDDLE_CENTER);
        VLI.setComponentAlignment(CSS_SubImages, Alignment.MIDDLE_CENTER);

        //<editor-fold defaultstate="collapsed" desc="Glavna slika">
        final Image defaultImage = new Image(null, new FileResource(new File(DS.getDocumentController().getDefaultFSImage(f).getAbsolutePath(true))));
        defaultImage.setHeight(97, Unit.PERCENTAGE);
        defaultImage.setWidth(97, Unit.PERCENTAGE);

        VL_MainImage.addComponent(defaultImage);
        VL_MainImage.setComponentAlignment(defaultImage, Alignment.MIDDLE_CENTER);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Sve slike stanice,...">
        for (final Image si : getAllFSImages(f)) {
            si.setHeight(40, Unit.PIXELS);
            si.setWidth(40, Unit.PIXELS);

            si.addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    if (event.isDoubleClick()) {
                        VL_MainImage.removeAllComponents();

                        si.setHeight(97, Unit.PERCENTAGE);
                        si.setWidth(97, Unit.PERCENTAGE);

                        VL_MainImage.addComponent(si);
                        VL_MainImage.setComponentAlignment(si, Alignment.MIDDLE_CENTER);
                    }
                }
            });

            CSS_SubImages.addComponent(si);
        }
        //</editor-fold>

        getUI().addWindow(new MyWindow(VLI, caption, 0, 0));
    }

    private List<Image> getAllFSImages(Fuelstation f) {
        List<Image> LI = new ArrayList<>();

        for (Document d : DS.getDocumentController().getAllFSDocuments(f)) {
            if (ImageTypes.contains(d.getName())) {

                FileResource fr = new FileResource(new File(d.getAbsolutePath(true)));

                if (fr.getSourceFile().exists()) {
                    final Image image = new Image(null, fr);

                    LI.add(image);
                }
            }
        }

        return LI;
    }
    //</editor-fold>
}
