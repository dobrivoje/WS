/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import Forms.CDM.Form_RELCBT;
import Forms.Form_CRUD2;
import Trees.CRM.Tree_CRMSingleCase;
import Trees.CRM.Tree_CustomerCRMCases;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;
import db.ent.RelCBType;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomDateTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Tree_RelCBT extends CustomDateTree<RelCBType> {

    private Form_CRUD2 crudForm;
    private RelCBType rcbt;

    public Tree_RelCBT(String caption, Customer customer, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCustomerController().getAllCustomerBussinesTypes(customer), formAllowed);

        addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="Fuelstation">
                        if (event.getItemId() instanceof RelCBType) {

                            try {
                                this.rcbt = (RelCBType) event.getItemId();
                                crudForm = new Form_RELCBT(rcbt, null, false);

                                winFormCaption = "Existing Customer Bussines Type Form";

                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                            //</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="Open form">
                            propTrees.stream().forEach((ct) -> {
                                ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                            });

                            winFormPropPanel = new Panel(propPanel.getComponentCount() > 0
                                    ? "Customer Bussines Types" : "No Customer Bussines Type.", propPanel);

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
                Logger.getLogger(Tree_CustomerCRMCases.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @Override
    protected void createSubNodes(RelCBType rcbt) {
        iterateAllNodesForDates(rcbt, rcbt.getDateFrom(), rcbt.getDateTo());
    }
}