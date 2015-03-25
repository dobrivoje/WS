/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import db.ent.Document;
import db.ent.Fuelstation;
import java.io.File;
import java.util.List;
import org.superb.apps.utilities.files.uploader.UploadReceiver;
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

    protected Image createImage(final Fuelstation fuelstation, float height, float width) {
        Document defaultImage = DS.getDocumentController().getFSImage(fuelstation);

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
                    openFSGalleryWindow("Fuelstation - " + fuelstation.getName(), image.getSource());
                }
            }
        });

        return image;
    }

    protected VerticalLayout createImageGallery(final Fuelstation f) {

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSpacing(true);

        Image defaultFSImage = createImage(f, 210, 210);
        Upload imageUploader = new Upload(null,
                new UploadReceiver(
                        null,
                        DS.getGalleryController().getDefaultImageGallery().getStoreLocation()
                        + f.getName() + "\\img\\"
                )
        );

        VerticalLayout mainImageLayout = new VerticalLayout(defaultFSImage, imageUploader);
        mainImageLayout.setSpacing(true);

        rootLayout.addComponents(mainImageLayout);

        CssLayout fsImagesCssLayout = new CssLayout() {
            @Override
            protected String getCss(Component c) {
                return "display: inline-block;";
            }
        };

        for (Document d : DS.getDocumentController().getAllFSDocuments(f)) {
            if (d.getName().contains("jpg")
                    || d.getName().contains("jpeg")
                    || d.getName().contains("gif")
                    || d.getName().contains("png")
                    || d.getDocType().equals("img")) {

                final Image image = new Image(null, new FileResource(new File(d.getAbsolutePath(true))));
                image.setHeight(70, Unit.PIXELS);
                image.setWidth(70, Unit.PIXELS);

                image.addClickListener(new MouseEvents.ClickListener() {
                    @Override
                    public void click(MouseEvents.ClickEvent event) {
                        if (event.isDoubleClick()) {
                            openFSGalleryWindow("Fuelstation - " + f.getName(), image.getSource());
                        }
                    }
                });

                fsImagesCssLayout.addComponent(image);
            }
        }

        rootLayout.addComponent(fsImagesCssLayout);

        return rootLayout;
    }

    private void openFSGalleryWindow(String caption, Resource resource) {
        VerticalLayout VLI = new VerticalLayout();
        VLI.setSizeFull();

        Image img = new Image();
        img.setSource(resource);

        img.setHeight(97, Unit.PERCENTAGE);
        img.setWidth(97, Unit.PERCENTAGE);

        VLI.addComponent(img);
        VLI.setComponentAlignment(img, Alignment.MIDDLE_CENTER);

        getUI().addWindow(new MyWindow(VLI, caption, 0, 0));
    }
}
