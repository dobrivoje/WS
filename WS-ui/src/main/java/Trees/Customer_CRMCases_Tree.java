/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import Forms.CRM.CRMCase_Form;
import Forms.CRM.CRMProcess_Form;
import static Menu.MenuDefinitions.CRM_MANAG_EXISTING_PROCESS;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.List;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Customer_CRMCases_Tree extends CustomObjectTree<CrmCase> {

    private final Customer customer;
    private final boolean formAllowed;

    public Customer_CRMCases_Tree(String caption, final Customer customer, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCrmController().getCRM_Cases(customer, false));

        this.customer = customer;
        this.formAllowed = formAllowed;

        addItemClickListener((ItemClickEvent event) -> {
        });

        refreshVisualContainer();
    }

    @Override
    public final void refreshVisualContainer() {
        for (CrmCase c : elements) {
            if (!c.isFinished()) {
                createSubItems(c, DS.getCrmController().getCRM_Processes(c, false));
            }
        }
    }

    @Override
    public final void addItemClickListener(ItemClickEvent.ItemClickListener listener) {
        super.addItemClickListener((ItemClickEvent event) -> {
            try {
                final Customer_CRMCases_Tree ccct = new Customer_CRMCases_Tree("", customer, formAllowed);

                VerticalLayout VL_CRMCases = new VerticalLayout();
                VL_CRMCases.setMargin(true);

                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            CrmCase crmCase = (CrmCase) event.getItemId();
                            List<CRM_SingleCase_Tree> csTrees = new ArrayList<>();

                            try {
                                Salesman s = crmCase.getFK_IDRSC().getFK_IDS();
                                for (CrmCase activeCRMCase : DS.getCrmController().getCRM_Cases(s, false)) {
                                    CRM_SingleCase_Tree csct = new CRM_SingleCase_Tree("Case by " + s.toString(), activeCRMCase);
                                    csTrees.add(csct);

                                    VL_CRMCases.addComponent(csct);
                                }

                                getUI().addWindow(
                                        new WindowFormProp(
                                                "Existing CRM Case",
                                                false,
                                                new CRMCase_Form(crmCase, null),
                                                new Panel(VL_CRMCases.getComponentCount() > 0
                                                                ? "Open CRM Cases" : "No Active Salesman CRM Case",
                                                        VL_CRMCases)
                                        )
                                );

                                for (CRM_SingleCase_Tree ct : csTrees) {
                                    ct.refreshVisualContainer();
                                }
                            } catch (CustomTreeNodesEmptyException | NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                    //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            CrmProcess crmProcess = (CrmProcess) event.getItemId();

                            try {
                                Salesman s = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();
                                List<CRM_SingleCase_Tree> crmTrees = new ArrayList<>();

                                for (CrmCase activeCRMCase : DS.getCrmController().getCRM_Cases(s, false)) {
                                    CRM_SingleCase_Tree csct = new CRM_SingleCase_Tree("Case by " + s.toString(), activeCRMCase);
                                    crmTrees.add(csct);

                                    VL_CRMCases.addComponent(csct);
                                }

                                getUI().addWindow(
                                        new WindowFormProp(
                                                CRM_MANAG_EXISTING_PROCESS.toString(),
                                                false,
                                                new CRMProcess_Form(crmProcess, ccct),
                                                new Panel(VL_CRMCases.getComponentCount() > 0
                                                                ? "Open CRM Cases" : "No Active Salesman CRM Case",
                                                        VL_CRMCases)
                                        )
                                );

                                for (CRM_SingleCase_Tree ct : crmTrees) {
                                    ct.refreshVisualContainer();
                                }

                            } catch (CustomTreeNodesEmptyException | NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !", Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
            }
        });
    }
}
