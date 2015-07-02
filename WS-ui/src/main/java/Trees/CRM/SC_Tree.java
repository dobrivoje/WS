/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM;

import Forms.CDM.CustomerForm;
import Forms.CRM.CRMCase_Form;
import Forms.CRM.SCR_Form;
import com.vaadin.event.Action;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.List;
import org.dobrivoje.auth.roles.RolesPermissions;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import ws.MyUI;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class SC_Tree extends CustomObjectTree<Salesman> {

    private static final Action ACTION_NEW_CS_RELATION = new Action("New CRM Salesman Customer Relation");
    private static final Action ACTION_NEW_CRM_CASE = new Action("New CRM Case");
    private static final Action ACTION_CUSTOMER_DATA_UPDATE = new Action("New Customer Update");
    private static final Action ACTION_CUSTOMER_ACTIVE_NO_MORE = new Action("Customer Removal from Salesman Responsibility");

    private static final Action[] SC_TREE_ACTIONS = new Action[]{
        ACTION_NEW_CS_RELATION, ACTION_NEW_CRM_CASE, ACTION_CUSTOMER_DATA_UPDATE, ACTION_CUSTOMER_ACTIVE_NO_MORE
    };

    public SC_Tree(String caption, List<Salesman> L) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, L);

        //<editor-fold defaultstate="collapsed" desc="addActionHandler">
        super.addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return SC_TREE_ACTIONS;
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                final SC_Tree source = (SC_Tree) sender;

                if (source.getValue() != null) {
                    //<editor-fold defaultstate="collapsed" desc="ACTION_NEW_CS_RELATION">
                    if (action.equals(ACTION_NEW_CS_RELATION) && (source.getValue() instanceof Customer)) {
                        Customer c = (Customer) target;
                        Salesman s = (Salesman) source.getParent(target);

                        SCR_Form scf = new SCR_Form(s, c, null, false);

                        getUI().addWindow(new WindowForm(
                                ACTION_NEW_CS_RELATION.getCaption(),
                                false,
                                scf,
                                scf.getClickListener()));
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="ACTION_CUSTOMER_DATA_UPDATE">
                    if (action.equals(ACTION_CUSTOMER_DATA_UPDATE) && (source.getValue() instanceof Customer)) {
                        Customer c = (Customer) (source.getValue());

                        CustomerForm cf = new CustomerForm(
                                c,
                                () -> {
                                    source.markAsDirtyRecursive();
                                },
                                false);

                        if (MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL)) {
                            getUI().addWindow(new WindowForm3(
                                    "Customer Update Form",
                                    cf,
                                    "img/crm/crm-user-3.png",
                                    cf.getClickListener(),
                                    250, 205)
                            );
                        }
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="ACTION_NEW_CRM_CASE">
                    if (action.equals(ACTION_NEW_CRM_CASE) && (target instanceof Customer)) {
                        Customer c = (Customer) target;
                        Salesman s = (Salesman) source.getParent(target);
                        List<CrmCase> openCustomerCases = DS.getCRMController().getCRM_Cases(c, false);

                        if (openCustomerCases.isEmpty()) {
                            CRMCase_Form ccf = new CRMCase_Form(null, null, true);

                            getUI().addWindow(new WindowForm(
                                    ACTION_NEW_CRM_CASE.getCaption(),
                                    false,
                                    ccf,
                                    ccf.getClickListener())
                            );
                        } else {
                            VerticalLayout VL_CRMCases = new VerticalLayout();
                            VL_CRMCases.setMargin(true);

                            List<CRM_SingleCase_Tree> csTrees = new ArrayList<>();

                            try {
                                for (CrmCase activeCRMCase : openCustomerCases) {
                                    CRM_SingleCase_Tree csct = new CRM_SingleCase_Tree(
                                            "Case by " + activeCRMCase.getFK_IDRSC().getFK_IDS().toString(),
                                            activeCRMCase);

                                    csTrees.add(csct);

                                    VL_CRMCases.addComponent(csct);
                                }

                                CRMCase_Form ccf = new CRMCase_Form(DS.getCRMController().getCRM_LastActive_CRMCase(c, s), false, null);

                                getUI().addWindow(
                                        new WindowFormProp(
                                                "Last Open Salesman CRM Case",
                                                false,
                                                ccf.getClickListener(),
                                                ccf,
                                                new Panel(VL_CRMCases.getComponentCount() > 0
                                                                ? "All Open Customer CRM Cases" : "No Active Customer CRM Case",
                                                        VL_CRMCases)
                                        )
                                );

                                for (CRM_SingleCase_Tree ct : csTrees) {
                                    ct.refreshVisualContainer();
                                }
                            } catch (CustomTreeNodesEmptyException | NullPointerException | IllegalArgumentException e) {
                            }
                        }
                    }
                    //</editor-fold>
                }
            }
        });
        //</editor-fold>
    }

    @Override
    protected void createSubNodes(Salesman s) {
        createNodeItems(s, DS.getCRMController().getCRM_Customers(s));
    }
}
