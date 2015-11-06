/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import Forms.Form_CRUD2;
import Forms.FSM.Form_FSOwner;
import ImageGallery.CustomerFuelStationsGallery;
import ImageGallery.IDocumentGallery;
import Trees.CRM.Tree_CRMSingleCase;
import Trees.CRM.Tree_CustomerCRMCases;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;
import db.ent.Owner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomDateTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Tree_FSOwner extends CustomDateTree<Owner> {

    private Form_CRUD2 crudForm;
    private Owner owner;

    private IDocumentGallery IG;

    public Tree_FSOwner(String caption, Customer customer, boolean justActive, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCustomerController().getAllFSOwnedByCustomer(customer, justActive), formAllowed);

        IG = new CustomerFuelStationsGallery(UI.getCurrent().getUI(), Tree_FSOwner.this::refreshVisualContainer);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        if (event.getItemId() instanceof Owner) {

                            try {
                                owner = (Owner) event.getItemId();

                                crudForm = new Form_FSOwner(owner, null, false);
                                winFormCaption = "Fuelstation Owner Form";

                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }

                            propTrees.stream().forEach((ct) -> {
                                ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                            });

                            winFormPropPanel = new Panel(propPanel.getComponentCount() > 0
                                    ? "FS Owners" : "No FS Owner.", propPanel);

                            getUI().addWindow(new WindowFormProp(
                                    winFormCaption,
                                    false,
                                    crudForm.getClickListener(),
                                    crudForm,
                                    IG.createMainDocument(IG.createDocument(owner.getFkIdFs(), 240, 240))
                            ));
                        }
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException ex) {
                Logger.getLogger(Tree_FSOwner.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //</editor-fold>
    }

    /**
     * <p>
     * Kreiraj stablo sa čvorovima koji se dobijaju iz liste.</p>
     * Postoje tačno dva podčvora svakog čvora, i predastavljaju datume od - do.
     *
     * @param owner Čvor
     */
    @Override
    protected void createSubNodes(Owner owner) {
        iterateAllNodesForDates(owner, owner.getDateFrom(), owner.getDateTo());
    }
}
