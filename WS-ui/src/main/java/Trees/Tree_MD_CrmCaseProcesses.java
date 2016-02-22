package Trees;

import Trees.CRM.*;
import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMProcess;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import Main.MyUI;
import static Main.MyUI.DS;
import static Uni.MainMenu.MenuDefinitions.CRM_MANAG_EXISTING_PROCESS;
import db.ent.custom.CustomSearchData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import static org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp.WINDOW_HEIGHT_DEFAULT_BIG;

/**
 *
 * @author root
 */
public class Tree_MD_CrmCaseProcesses extends Tree_MasterDetail {

    private String imageLocation;

    private CrmCase crmCase;
    private CrmProcess crmProcess;
    private Salesman salesman;

    public Tree_MD_CrmCaseProcesses(Map<Object, List> treeModel, boolean newCase, boolean formAllowed, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super("", treeModel, expandRootNodes);

        addItemClickListener(getItemClickListener(newCase, formAllowed));
    }

    public Tree_MD_CrmCaseProcesses(CustomSearchData csd, boolean newCase, boolean formAllowed, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super("");

        this.expandRootNodes = expandRootNodes;

        List crmCases = DS.getSearchController().getCRMCases(csd);
        addItems(crmCases);

        Map<Object, List> M = new HashMap<>();

        for (CrmCase cc : (List<CrmCase>) crmCases) {
            M.put(cc, cc.getCrmProcessList());
            createMasterDetail(M, expandRootNodes);
        }

        addItemClickListener(getItemClickListener(newCase, formAllowed));
    }

    //<editor-fold defaultstate="collapsed" desc="ItemClickListener">
    private ItemClickEvent.ItemClickListener getItemClickListener(boolean newCase, boolean formAllowed) {
        return (ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            salesman = new Salesman();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                crmCase = (CrmCase) event.getItemId();
                                salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMCase(crmCase, null, newCase, readOnly);
                                winFormCaption = "Existing CRM Case";
                                imageLocation = "img/crm/crmCase.png";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                crmProcess = (CrmProcess) event.getItemId();
                                salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());
                                // Tree_CRMSingleCase cc = new Tree_CRMSingleCase("", crmProcess.getFK_IDCA());
                                crudForm = new Form_CRMProcess(crmProcess, null, false, readOnly);
                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        if (crudForm instanceof Form_CRMProcess) {
                            Tree_CRMSingleCase csct;
                            crmCase = crmCase == null ? crmProcess.getFK_IDCA() : crmCase;

                            csct = new Tree_CRMSingleCase(crmCase.getFK_IDRSC().getFK_IDC().toString(), crmCase, crudForm);

                            propTrees.add(csct);
                            propPanel.addComponent(csct);

                            propTrees.stream().forEach((ct) -> {
                                ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                            });
                        }

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
        };
    }
    //</editor-fold>

}
