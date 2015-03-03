/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import com.vaadin.data.Container;
import db.ent.Customer;
import db.ent.RelCBType;
import java.util.List;
import org.superb.apps.utilities.vaadin.Trees.CustomTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class RELCBT_Tree extends CustomTree<RelCBType> {

    public RELCBT_Tree(String caption, List treeItems) {
        super(caption, treeItems);
        createSubItems();
    }

    public RELCBT_Tree(String caption, Customer customer) {
        super(caption, DS.getCustomerController().getAllCustomerBussinesTypes(customer));
        createSubItems();
    }

    public RELCBT_Tree(String caption, Container container) {
        super(caption, container);
    }

    public final void createSubItems() {
        for (RelCBType r : elements) {
            createSubItems(r, r.getDateFrom(), r.getDateTo());
        }
    }
}
