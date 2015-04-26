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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
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
import org.vaadin.dialogs.ConfirmDialog;
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

    //<editor-fold defaultstate="collapsed" desc="DocImg">
    class DocImg {

        private final Image image;
        private final Document doc;

        public DocImg(Image image, Document doc) {
            this.image = image;
            this.doc = doc;
        }

        public Image getImage() {
            return image;
        }

        public Document getDoc() {
            return doc;
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
    protected Image createFSImage(final Fuelstation f, float height, float width) {
        Document defaultImage = DS.getDocumentController().getDefaultFSImage(f);

        final Image image;

        if (defaultImage != null) {
            image = new Image(null, new FileResource(new File(defaultImage.getAbsolutePath(true))));
            image.setDescription("Double click to open\nan Image Gallery.");
        } else {
            image = new Image(null, new ThemeResource("img/fs/1.png"));
        }

        image.setHeight(height, Unit.PIXELS);
        image.setWidth(width, Unit.PIXELS);

        image.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                if (event.isDoubleClick()) {
                    openFSGalleryWindow("Fuelstation - " + f.getName(), f);
                }
            }
        });

        return image;
    }

    protected VerticalLayout createMainImage(final Fuelstation f) {
        return new VerticalLayout(createFSImage(f, 200, 200));
    }

    protected VerticalLayout createImageGallery(final Fuelstation f, boolean uploaderOnForm) {
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

        //<editor-fold defaultstate="collapsed" desc="Kreiraj sličice ako ih ima">
        CssLayout FSLowerImagesCssLayout = new CssLayout() {
            @Override
            protected String getCss(Component c) {
                return "display: inline-block;";
            }
        };

        for (final DocImg di : getAllFSImages(f)) {
            di.getImage().setHeight(40, Unit.PIXELS);
            di.getImage().setWidth(40, Unit.PIXELS);

            di.getImage().addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    if (event.isDoubleClick()) {
                        openFSGalleryWindow("Fuelstation - " + f.getName(), f);
                    }
                }
            });

            FSLowerImagesCssLayout.addComponent(di.getImage());
        }
        //</editor-fold>

        rootLayout.addComponents(createMainImage(f));

        if (uploaderOnForm) {
            rootLayout.addComponents(imageUploader);
        }
        
        rootLayout.addComponent(FSLowerImagesCssLayout);

        return rootLayout;
    }

    protected void openFSGalleryWindow(String caption, final Fuelstation f) {
        VerticalLayout VL_Root = new VerticalLayout();
        VL_Root.setSpacing(true);

        final Window W = new MyWindow(VL_Root, caption, 0, 0);
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
        Image defaultImage = new Image(null, new FileResource(new File(DS.getDocumentController().getDefaultFSImage(f).getAbsolutePath(true))));
        defaultImage.setHeight(97, Unit.PERCENTAGE);
        defaultImage.setWidth(97, Unit.PERCENTAGE);

        VL_MainImage.addComponent(defaultImage);
        VL_MainImage.setComponentAlignment(defaultImage, Alignment.MIDDLE_CENTER);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Sve slike stanice.">
        for (final DocImg di : getAllFSImages(f)) {
            di.getImage().setHeight(40, Unit.PIXELS);
            di.getImage().setWidth(40, Unit.PIXELS);
            di.getImage().setDescription("Click to open the image.");

            di.getImage().addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    VL_MainImage.removeAllComponents();
                    Image ni = new Image(null, di.getImage().getSource());
                    ni.setHeight(97, Unit.PERCENTAGE);
                    ni.setWidth(97, Unit.PERCENTAGE);

                    ni.setDescription("Double click to make this image default for this FS.");
                    ni.addClickListener(new MouseEvents.ClickListener() {
                        @Override
                        public void click(MouseEvents.ClickEvent event) {
                            if (event.isDoubleClick()) {
                                ConfirmDialog d = ConfirmDialog.show(
                                        getUI(),
                                        "Default FS Gallery Image",
                                        "Do you want this selected image to be</br> the FS's default one ?",
                                        "Yes",
                                        "No!",
                                        new ConfirmDialog.Listener() {
                                            @Override
                                            public void onClose(ConfirmDialog dialog) {
                                                if (dialog.isConfirmed()) {
                                                    try {
                                                        DS.getDocumentController().setDefaultFSImage(f, di.getDoc());
                                                        refreshVisualContainer();
                                                    } catch (Exception ex) {
                                                        Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                                                    }
                                                }
                                            }
                                        });
                                d.getCancelButton().setStyleName(ValoTheme.BUTTON_DANGER);
                                d.getCancelButton().setWidth(100, Unit.PIXELS);
                                d.getOkButton().setStyleName(ValoTheme.BUTTON_PRIMARY);
                                d.getOkButton().setWidth(100, Unit.PIXELS);
                                d.setContentMode(ConfirmDialog.ContentMode.HTML);
                                d.setHeight("16em");
                            }
                        }
                    });

                    VL_MainImage.addComponent(ni);
                    VL_MainImage.setComponentAlignment(ni, Alignment.MIDDLE_CENTER);
                }
            });

            HL_Images.addComponent(di.getImage());
        }
        //</editor-fold>

        getUI().addWindow(W);
    }

    private List<DocImg> getAllFSImages(Fuelstation f) {
        List<DocImg> LI = new ArrayList<>();

        for (Document d : DS.getDocumentController().getAllFSDocuments(f)) {
            if (ImageTypes.contains(d.getName())) {
                FileResource fr = new FileResource(new File(d.getAbsolutePath(true)));

                if (fr.getSourceFile().exists()) {
                    final Image image = new Image(null, fr);

                    LI.add(new DocImg(image, d));
                }
            }
        }

        return LI;
    }
    //</editor-fold>
}
