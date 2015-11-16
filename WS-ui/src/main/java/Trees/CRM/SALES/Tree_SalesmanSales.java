/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM.SALES;

import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMSell;
import Trees.CRM.Tree_CRMSingleCase;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.Date;
import java.util.List;
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

    private Salesman salesman;
    private String imageLocation;

    public Tree_SalesmanSales(String caption, Salesman salesman, Date dateFrom, Date dateTo, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCRMController().getCRM_Salesrep_Sales_Cases(salesman, dateFrom, dateTo), dateFrom, dateTo);

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
                                imageLocation = "img/crm/sell.png";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CrmCase">
                        if (event.getItemId() instanceof CrmCase) {

                            try {
                                this.salesman = ((CrmCase) event.getItemId()).getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMCase((CrmCase) event.getItemId(), null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                                imageLocation = "img/crm/crmCase.png";
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

                        getUI().addWindow(
                                new WindowForm3(
                                        winFormCaption,
                                        crudForm,
                                        imageLocation,
                                        crudForm.getClickListener(),
                                        236, 196, readOnly
                                )
                        );
                        //</editor-fold>

                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException | CustomTreeNodesEmptyException ex) {
                Logger.getLogger(Tree_SalesmanSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //</editor-fold>
    }

    public Tree_SalesmanSales(String caption, List crmCases, Date dateFrom, Date dateTo, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, crmCases, dateFrom, dateTo);

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
                                imageLocation = "img/crm/sell.png";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CrmCase">
                        if (event.getItemId() instanceof CrmCase) {

                            try {
                                this.salesman = ((CrmCase) event.getItemId()).getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMCase((CrmCase) event.getItemId(), null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                                imageLocation = "img/crm/crmCase.png";
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

                        getUI().addWindow(
                                new WindowForm3(
                                        winFormCaption,
                                        crudForm,
                                        imageLocation,
                                        crudForm.getClickListener(),
                                        236, 196, readOnly
                                )
                        );
                        //</editor-fold>

                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException | CustomTreeNodesEmptyException ex) {
                Logger.getLogger(Tree_SalesmanSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //</editor-fold>
    }

    public Tree_SalesmanSales(String caption, List crmCases, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, crmCases);

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
                                imageLocation = "img/crm/sell.png";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CrmCase">
                        if (event.getItemId() instanceof CrmCase) {

                            try {
                                this.salesman = ((CrmCase) event.getItemId()).getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMCase((CrmCase) event.getItemId(), null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                                imageLocation = "img/crm/crmCase.png";
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

                        getUI().addWindow(
                                new WindowForm3(
                                        winFormCaption,
                                        crudForm,
                                        imageLocation,
                                        crudForm.getClickListener(),
                                        236, 196, readOnly
                                )
                        );
                        //</editor-fold>

                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException | CustomTreeNodesEmptyException ex) {
                Logger.getLogger(Tree_SalesmanSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //</editor-fold>
    }

    @Override
    protected void createSubNodes(CrmCase cc) {
        /*
         CustomSearchData csd = new CustomSearchData();
         csd.setCrmCase(cc);
         csd.setCaseFinished(true);
         csd.setSaleAgreeded(true);
         */
        // createSingleRootChildNodes(cc, DS.getSearchController().getSales(csd));
        // createSingleRootChildNodes(cc, cc.getCrmProcessList());

        createSingleRootChildNodes(cc, DS.getCRMController().getCRM_Sales(cc, dateFrom, dateTo));
    }

}
