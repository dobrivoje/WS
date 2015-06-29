/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import Forms.CRUDForm2;
import Forms.FSM.FSOWNER_Form;
import Trees.CRM.CRM_SingleCase_Tree;
import Trees.CRM.Customer_CRMCases_Tree;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
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
public class FSOwner_Tree extends CustomDateTree<Owner> {

    private CRUDForm2 crudForm;
    private Owner owner;

    public FSOwner_Tree(String caption, Customer customer, boolean justActive, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCustomerController().getAllFSOwnedByCustomer(customer, justActive), formAllowed);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="Fuelstation">
                        if (event.getItemId() instanceof Owner) {

                            try {
                                owner = (Owner) event.getItemId();
                                crudForm = new FSOWNER_Form(owner, null, false);

                                winFormCaption = "Fuelstation Owner Form";

                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                            //</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="Open form">
                            propTrees.stream().forEach((ct) -> {
                                ((CRM_SingleCase_Tree) ct).refreshVisualContainer();
                            });

                            winFormPropPanel = new Panel(propPanel.getComponentCount() > 0
                                    ? "FS Owners" : "No FS Owner.", propPanel);

                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            crudForm.getClickListener(),
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException ex) {
                Logger.getLogger(Customer_CRMCases_Tree.class.getName()).log(Level.SEVERE, null, ex);
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
