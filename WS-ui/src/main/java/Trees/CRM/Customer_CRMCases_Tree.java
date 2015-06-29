/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM;

import Forms.CRM.CRMCase_Form;
import Forms.CRM.CRMProcess_Form;
import Forms.CRUDForm2;
import static Menu.MenuDefinitions.CRM_MANAG_EXISTING_PROCESS;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Customer_CRMCases_Tree extends CustomObjectTree<CrmCase> {

    private CRUDForm2 crudForm;
    private Salesman salesman;

    public Customer_CRMCases_Tree(String caption, final Customer customer, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCRMController().getCRM_Cases(customer, false));

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                CrmCase crmCase = (CrmCase) event.getItemId();
                                this.salesman = crmCase.getFK_IDRSC().getFK_IDS();
                                crudForm = new CRMCase_Form(crmCase, null, false);

                                winFormCaption = "Existing CRM Case";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                CrmProcess crmProcess = (CrmProcess) event.getItemId();
                                this.salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();
                                Customer_CRMCases_Tree ccct = new Customer_CRMCases_Tree("", customer, formAllowed);
                                crudForm = new CRMProcess_Form(crmProcess, ccct, false);

                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (CustomTreeNodesEmptyException | NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        for (CrmCase ac : DS.getCRMController().getCRM_Cases(salesman, false)) {
                            CRM_SingleCase_Tree csct = new CRM_SingleCase_Tree("Case by " + salesman.toString(), ac, crudForm);
                            propTrees.add(csct);
                            
                            propPanel.addComponent(csct);
                        }
                        
                        propTrees.stream().forEach((ct) -> {
                            ((CRM_SingleCase_Tree) ct).refreshVisualContainer();
                        });
                        
                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Open CRM Cases" : "No Active Salesman CRM Case", propPanel);
                        
                        getUI().addWindow(
                                new WindowFormProp(
                                        winFormCaption,
                                        false,
                                        crudForm.getClickListener(),
                                        crudForm,
                                        winFormPropPanel
                                )
                        );
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException | CustomTreeNodesEmptyException ex) {
                Logger.getLogger(Customer_CRMCases_Tree.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //</editor-fold>
    }

    @Override
    protected void createSubNodes(CrmCase c) {
        if (!c.getFinished()) {
            createNodeItems(c, DS.getCRMController().getCRM_Processes(c, false));
        }
    }
}
