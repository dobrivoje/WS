/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import com.vaadin.ui.Button;
import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;

/**
 * Dialog for custom filtering options.
 * <p>
 * Primary use of this dialog is in the CRM panels.
 */
public class SelectorDialog extends WindowForm3 {

    /**
     *
     * @param csd
     * @param salesman
     * @param externalButtonClickListener Listener to run upon calling action
     * button
     */
    public SelectorDialog(CustomSearchData csd, Salesman salesman, Button.ClickListener externalButtonClickListener) {
        super("Custom Search Dialog",
                new Form_CustomSearch(
                        csd,
                        salesman,
                        ""),
                "img/crm/cbt.png",
                "Search",
                externalButtonClickListener,
                true);
    }

    public SelectorDialog(Salesman salesman, IUpdateData<CustomSearchData> iUpdateData) {
        super("Custom Search Dialog",
                new Form_CustomSearch(
                        salesman,
                        iUpdateData),
                "img/crm/cbt.png",
                "Search",
                null,
                true);
    }

}
