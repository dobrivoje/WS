/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uni.Dialogs;

import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
import com.vaadin.ui.Button;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;

/**
 * Dialog for custom filtering options.
 * <p>
 * Primary use of this dialog is in the CRM panels.
 */
public class SelectorDialog extends WindowForm3 {

    /**
     *
     * @param formCustomSearch
     */
    public SelectorDialog(Form_CRUD2 formCustomSearch) {
        super("Advanced Search Dialog",
                formCustomSearch,
                "img/crm/cbt.png",
                "Search",
                formCustomSearch.getClickListener(),
                220, 202, false);

        actionButton.addClickListener((Button.ClickEvent event) -> {
            closeButton.click();
        });
    }

}
