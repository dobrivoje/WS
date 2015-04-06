/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import Forms.CDM.CustomerForm;
import Forms.CRM.CRMProcess_Form;
import Forms.CRM.SCR_Form;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.List;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm2;
import org.superb.apps.utilities.vaadin.Trees.CustomSCTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class SC_Tree extends CustomSCTree<Salesman> {

    private static final Action ACTION_NEW_CS_RELATION = new Action("New CRM Salesman Customer Relation");
    private static final Action ACTION_NEW_CRM_PROCESS = new Action("New CRM process");
    private static final Action ACTION_CUSTOMER_DATA_UPDATE = new Action("New Customer Update");
    private static final Action ACTION_CUSTOMER_ACTIVE_NO_MORE = new Action("Customer Removal from Salesman Responsibility");

    public SC_Tree(String caption, List<Salesman> L) {
        super(caption, L);
        createSubItems();
        addActionHandler(getActionManager());
    }

    public SC_Tree(String caption, BeanItemContainer<Salesman> bic) {
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
                    ACTION_NEW_CS_RELATION, ACTION_NEW_CRM_PROCESS, ACTION_CUSTOMER_DATA_UPDATE, ACTION_CUSTOMER_ACTIVE_NO_MORE};
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
                        getUI().addWindow(new WindowForm2(ACTION_CUSTOMER_DATA_UPDATE.getCaption(), new CustomerForm((Customer) source.getValue(), null)));
                    }

                    if (action.equals(ACTION_NEW_CRM_PROCESS) && (source.getValue() instanceof Customer)) {
                        Customer c = (Customer) target;
                        Salesman s = (Salesman) source.getParent(target);

                        getUI().addWindow(new WindowForm(ACTION_NEW_CRM_PROCESS.getCaption(), false, new CRMProcess_Form(s, c, null)));
                    }
                }
            }
        });
    }
}
