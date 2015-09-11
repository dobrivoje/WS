/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM.SALES;

import Forms.CRM.Form_CRMSell;
import Forms.Form_CRUD2;
import Trees.CRM.Tree_CRMSingleCase;
import Trees.CRM.Tree_CustomerCRMCases;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import ws.MyUI;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Tree_SalesmanSales extends CustomObjectTree<CrmCase> {

    private Date dateFrom;
    private Date dateTo;
    private Salesman salesman;
    private Form_CRUD2 crudForm;

    public Tree_SalesmanSales(String caption, Salesman salesman, Date dateFrom, Date dateTo, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCRMController().getCRM_CompletedCases(salesman, true, true));

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="RelSale">
                        if (event.getItemId() instanceof RelSALE) {

                            try {
                                this.salesman = ((RelSALE) event.getItemId()).getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMSell((RelSALE) event.getItemId(), readOnly);

                                winFormCaption = "Existing Sale Case";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        for (RelSALE rs : DS.getCRMController().getCRM_Sales(salesman, this.dateFrom, this.dateTo)) {
                            Tree_CRMSingleCase csct = new Tree_CRMSingleCase("Sales by " + salesman.toString(), rs.getFK_IDCA());
                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Sales" : "No Active Salesman CRM Case", propPanel);

                        if (readOnly) {
                            getUI().addWindow(
                                    new WindowForm3(
                                            winFormCaption,
                                            crudForm,
                                            "img/crm/crm-new-sale.png",
                                            crudForm.getClickListener(),
                                            236, 177
                                    )
                            );
                        } else {
                            getUI().addWindow(
                                    new WindowForm3(
                                            winFormCaption,
                                            crudForm,
                                            "img/crm/crm-new-sale.png",
                                            crudForm.getClickListener(),
                                            236, 177
                                    )
                            );
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException | CustomTreeNodesEmptyException ex) {
                Logger.getLogger(Tree_CustomerCRMCases.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //</editor-fold>
    }

    public void setDateFrom(Date dateFrom, Date dateTo) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    protected void createSubNodes(CrmCase cc) {
        createNodeItems(cc, DS.getCRMController().getCRM_Sales(cc, dateFrom, dateTo));
    }

    @Override
    public void refreshVisualContainer() {
        super.refreshVisualContainer();
    }

}
