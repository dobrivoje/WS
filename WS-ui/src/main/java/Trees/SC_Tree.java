/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import Forms.CDM.CustomerForm;
import Forms.CRM.CRMCase_Form;
import Forms.CRM.SCR_Form;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.List;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomSCTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class SC_Tree extends CustomSCTree<Salesman> {

    private static final Action ACTION_NEW_CS_RELATION = new Action("New CRM Salesman Customer Relation");
    private static final Action ACTION_NEW_CRM_CASE = new Action("New CRM Case");
    private static final Action ACTION_CUSTOMER_DATA_UPDATE = new Action("New Customer Update");
    private static final Action ACTION_CUSTOMER_ACTIVE_NO_MORE = new Action("Customer Removal from Salesman Responsibility");

    public SC_Tree(String caption, List<Salesman> L) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, L);
        createSubItems();
        addActionHandler(getActionManager());
    }

    public SC_Tree(String caption, BeanItemContainer<Salesman> bic) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, bic);
        createSubItems();
        addActionHandler(getActionManager());
    }

    private void createSubItems() {
        for (Salesman s : elements) {
            createSubItems(s, DS.getCrmController().getCRM_Customers(s));
        }
    }

    @Override
    public final void addActionHandler(Action.Handler actionHandler) {
        super.addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{
                    ACTION_NEW_CS_RELATION, ACTION_NEW_CRM_CASE, ACTION_CUSTOMER_DATA_UPDATE, ACTION_CUSTOMER_ACTIVE_NO_MORE};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                final SC_Tree source = (SC_Tree) sender;

                if (source.getValue() != null) {
                    if (action.equals(ACTION_NEW_CS_RELATION) && (source.getValue() instanceof Customer)) {
                        Customer c = (Customer) target;
                        Salesman s = (Salesman) source.getParent(target);

                        getUI().addWindow(new WindowForm(ACTION_NEW_CS_RELATION.getCaption(), false, new SCR_Form(s, c, null)));
                    }

                    if (action.equals(ACTION_CUSTOMER_DATA_UPDATE) && (source.getValue() instanceof Customer)) {

                        CustomerForm customerForm = new CustomerForm((Customer) source.getValue(), null);

                        getUI().addWindow(new WindowForm3(
                                ACTION_CUSTOMER_DATA_UPDATE.getCaption(),
                                customerForm,
                                null,
                                customerForm.getClickListener())
                        );
                    }

                    if (action.equals(ACTION_NEW_CRM_CASE) && (target instanceof Customer)) {
                        Customer c = (Customer) target;
                        Salesman s = (Salesman) source.getParent(target);
                        List<CrmCase> openCustomerCases = DS.getCrmController().getCRM_Cases(c, false);

                        if (openCustomerCases.isEmpty()) {
                            getUI().addWindow(new WindowForm(
                                    ACTION_NEW_CRM_CASE.getCaption(),
                                    false,
                                    new CRMCase_Form(s, c, null, true)));
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

                                getUI().addWindow(
                                        new WindowFormProp(
                                                "Last Open Salesman CRM Case",
                                                false,
                                                new CRMCase_Form(DS.getCrmController().getCRM_LastActive_CRMCase(c, s), null),
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
                }
            }
        });
    }
}
