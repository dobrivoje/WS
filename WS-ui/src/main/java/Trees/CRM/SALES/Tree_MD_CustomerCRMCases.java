/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM.SALES;

import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMProcess;
import Main.MyUI;
import static Main.MyUI.DS;
import Trees.CRM.Tree_CRMSingleCase;
import Trees.CRM.Tree_SalesmanCRMCases;
import db.Exceptions.CustomTreeNodesEmptyException;
import Trees.Tree_MasterDetail;
import static Uni.MainMenu.MenuDefinitions.CRM_MANAG_EXISTING_PROCESS;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Salesman;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import static org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp.WINDOW_HEIGHT_DEFAULT_BIG;

/**
 *
 * @author root
 */
public class Tree_MD_CustomerCRMCases extends Tree_MasterDetail {

    private Salesman salesman;
    private String imageLocation;

    public Tree_MD_CustomerCRMCases(Map<Object, List> customerCRMCases, boolean formAllowed, boolean expandRootNodes)
            throws CustomTreeNodesEmptyException, NullPointerException {

        super("", customerCRMCases, expandRootNodes);

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

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMCase(crmCase, null, false, readOnly);
                                winFormCaption = "Existing CRM Case";
                                imageLocation = "img/crm/crmCase.png";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                CrmProcess crmProcess = (CrmProcess) event.getItemId();
                                this.salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());
                                Tree_SalesmanCRMCases cc = new Tree_SalesmanCRMCases(
                                        DS.getCRMController().getCRM_Cases(salesman, false)
                                );
                                crudForm = new Form_CRMProcess(crmProcess, cc, false, readOnly);
                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        Tree_CRMSingleCase csct;

                        for (CrmCase ac : DS.getCRMController().getCRM_Cases(salesman, false)) {
                            if (crudForm instanceof Form_CRMProcess) {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac, crudForm);
                            } else {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac);
                            }

                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Open CRM Cases" : "No Active Salesman CRM Case", propPanel);

                        if (crudForm instanceof Form_CRMProcess) {
                            // Make window bigger in height to accomodate all form fields.
                            winFormHeight = WINDOW_HEIGHT_DEFAULT_BIG;
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            winFormHeight, winFormWidth,
                                            readOnly,
                                            crudForm.getClickListener(),
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        } else {
                            getUI().addWindow(
                                    new WindowForm3(
                                            winFormCaption,
                                            crudForm,
                                            imageLocation,
                                            crudForm.getClickListener(),
                                            196, 236, readOnly
                                    )
                            );
                        }
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

}
