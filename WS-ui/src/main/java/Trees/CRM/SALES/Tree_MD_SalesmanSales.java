/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM.SALES;

import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMSell;
import Main.MyUI;
import static Main.MyUI.DS;
import Trees.CRM.Tree_CRMSingleCase;
import db.Exceptions.CustomTreeNodesEmptyException;
import Trees.Tree_MasterDetail;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;
import db.ent.CrmCase;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;

/**
 *
 * @author root
 */
public class Tree_MD_SalesmanSales extends Tree_MasterDetail {

    private Salesman salesman;
    private RelSALE relSale;
    private CrmCase crmCase;

    private String imageLocation;

    public Tree_MD_SalesmanSales(Map<Object, List> salesRepSales, boolean formAllowed, boolean expandRootNodes)
            throws CustomTreeNodesEmptyException, NullPointerException {

        super("", salesRepSales, expandRootNodes);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {

                            try {
                                crmCase = (CrmCase) event.getItemId();
                                salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                winFormCaption = "Existing CRM Case";
                                imageLocation = "img/crm/crmCase.png";

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMSell(relSale, readOnly);
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="RelSale">
                        if (event.getItemId() instanceof RelSALE) {

                            try {
                                relSale = (RelSALE) event.getItemId();
                                crmCase = relSale.getFK_IDCA();
                                salesman = relSale.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                winFormCaption = "Existing Sell Case";
                                imageLocation = "img/crm/sell.png";

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMSell(relSale, readOnly);
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        for (RelSALE rs : DS.getCRMController().getCRM_Sales(relSale.getFK_IDCA())) {
                            Tree_CRMSingleCase csct = new Tree_CRMSingleCase("Sales by " + salesman.toString(), rs.getFK_IDCA());
                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Sales" : "No Active Salesman CRM Case", propPanel);

                        if (crudForm instanceof Form_CRMSell) {
                            getUI().addWindow(
                                    new WindowForm3(
                                            winFormCaption,
                                            crudForm,
                                            imageLocation,
                                            crudForm.getClickListener(),
                                            196, 236, readOnly, ValoTheme.BUTTON_FRIENDLY,
                                            new Button("Sell's CRM Case", (Button.ClickEvent event1) -> {
                                                Form_CRMCase f = new Form_CRMCase(crmCase, null, false, readOnly);

                                                getUI().addWindow(
                                                        new WindowForm3(
                                                                "CRM Case",
                                                                f,
                                                                "img/crm/crmCase.png",
                                                                f.getClickListener(),
                                                                196, 236, readOnly
                                                        )
                                                );
                                            })
                                    )
                            );
                        } else if (crudForm instanceof Form_CRMCase) {
                            getUI().addWindow(
                                    new WindowForm3(
                                            winFormCaption,
                                            crudForm,
                                            imageLocation,
                                            crudForm.getClickListener(),
                                            196, 236, readOnly)
                            );
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException | CustomTreeNodesEmptyException ex) {
            }
        });
        //</editor-fold>
    }

}
