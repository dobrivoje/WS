package Trees;

import Trees.CRM.*;
import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMProcess;
import static Uni.MainMenu.MenuDefinitions.CRM_MANAG_EXISTING_PROCESS;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Salesman;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import static org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp.WINDOW_HEIGHT_DEFAULT_BIG;
import Main.MyUI;
import static Main.MyUI.DS;

/**
 *
 * @author root
 */
public class Tree_MD_CrmCaseProcesses extends Tree_MasterDetail {

    public Tree_MD_CrmCaseProcesses(Map<Object, List> treeModel, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        this(treeModel, formAllowed, false);
    }

    public Tree_MD_CrmCaseProcesses(Map<Object, List> treeModel, boolean formAllowed, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super("", treeModel, expandRootNodes);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        super.addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            Salesman salesman = new Salesman();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                CrmCase crmCase = (CrmCase) event.getItemId();
                                salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());

                                crudForm = new Form_CRMCase(crmCase, null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                CrmProcess crmProcess = (CrmProcess) event.getItemId();
                                salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());

                                Tree_CRMSingleCase cc = new Tree_CRMSingleCase("", crmProcess.getFK_IDCA());
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
                                csct = new Tree_CRMSingleCase(salesman.toString(), ac, crudForm);
                            } else {
                                csct = new Tree_CRMSingleCase(salesman.toString(), ac);
                            }

                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Open CRM Cases" : "No Active Salesman CRM Case", propPanel);

                        if (readOnly) {
                            if (crudForm instanceof Form_CRMProcess) {
                                // Make window bigger in height to accomodate all form fields.
                                winFormHeight = WINDOW_HEIGHT_DEFAULT_BIG;
                            }

                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            winFormHeight, winFormWidth,
                                            false,
                                            readOnly,
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        } else {
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

            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
            }
        });
        //</editor-fold>

    }

}
