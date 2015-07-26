/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM;

import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMProcess;
import Forms.Form_CRUD2;
import static Menu.MenuDefinitions.CRM_MANAG_EXISTING_PROCESS;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Tree_SalesmanCRMCases extends CustomObjectTree<CrmCase> {

    private Form_CRUD2 crudForm;
    private Salesman salesman;

    /**
     *
     * @param caption
     * @param salesman
     * @param formAllowed Da li korisnik ima prava da otvara formu
     * @throws CustomTreeNodesEmptyException
     * @throws NullPointerException
     */
    public Tree_SalesmanCRMCases(String caption, Salesman salesman, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCRMController().getCRM_Cases(salesman, false));

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        super.addItemClickListener((ItemClickEvent event) -> {
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
                                crudForm = new Form_CRMCase(crmCase, null, false);

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
                                Tree_SalesmanCRMCases cc = new Tree_SalesmanCRMCases("", salesman, formAllowed);
                                crudForm = new Form_CRMProcess(crmProcess, cc, false);

                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        for (CrmCase ac : DS.getCRMController().getCRM_Cases(salesman, false)) {
                            Tree_CRMSingleCase csct = new Tree_CRMSingleCase("Case by " + this.salesman.toString(), ac);
                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
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

            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
            }
        });
        //</editor-fold>
    }

    @Override
    protected void createSubNodes(CrmCase cc) {
        createNodeItems(cc, DS.getCRMController().getCRM_Processes(cc, false));
    }
}
