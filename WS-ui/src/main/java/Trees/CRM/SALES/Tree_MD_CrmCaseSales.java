package Trees.CRM.SALES;

import Trees.*;
import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMSell;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.ent.CrmCase;
import db.ent.Salesman;
import org.superbapps.utils.vaadin.MyWindows.WindowFormProp;
import static org.superbapps.utils.vaadin.MyWindows.WindowFormProp.WINDOW_HEIGHT_DEFAULT_BIG;
import Main.MyUI;
import Trees.CRM.Tree_CRM_Sell_SingleCase;
import static Uni.MainMenu.MenuDefinitions.CRM_EXISTING_CASE;
import static Uni.MainMenu.MenuDefinitions.CRM_EXISTING_SELL_CASES;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Component;
import db.ent.RelSALE;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.superbapps.utils.vaadin.Exceptions.CustomTreeNodesEmptyException;
import org.superbapps.utils.vaadin.MyWindows.WindowForm3;

/**
 *
 * @author root
 */
public class Tree_MD_CrmCaseSales extends Tree_MasterDetail {

    private String imageLocation;

    public Tree_MD_CrmCaseSales(Map<Object, List> treeModel, boolean newCase, boolean formAllowed, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super("", treeModel, expandRootNodes);

        addItemClickListener(getItemClickListener(newCase, formAllowed));
    }

    /**
     * Kreiraj stablo sa jednim crm case-om, i njegovim prodajama.
     *
     * @param crmCase
     * @param newCase
     * @param formAllowed
     * @param expandRootNodes
     * @throws CustomTreeNodesEmptyException
     * @throws NullPointerException
     */
    public Tree_MD_CrmCaseSales(CrmCase crmCase, boolean newCase, boolean formAllowed, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super("");

        this.expandRootNodes = expandRootNodes;

        addItems(crmCase);

        Map<Object, List> M = new HashMap<>();
        M.put(crmCase, crmCase.getRelSALEList());

        createMasterDetail(M, expandRootNodes);

        addItemClickListener(getItemClickListener(newCase, formAllowed));
    }

    //<editor-fold defaultstate="collapsed" desc="ItemClickListener">
    private ItemClickListener getItemClickListener(boolean newCase, boolean formAllowed) {
        return (ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            Salesman salesman = null;
            CrmCase crmCase = null;

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">

                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                crmCase = (CrmCase) event.getItemId();
                                salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());
                                crudForm = new Form_CRMCase(crmCase, null, newCase, readOnly);
                                winFormCaption = CRM_EXISTING_CASE.toString();
                                imageLocation = "img/crm/crmCase.png";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Sell...">
                        if (event.getItemId() instanceof RelSALE) {
                            try {
                                RelSALE crmSell = (RelSALE) event.getItemId();
                                crmCase = crmSell.getFK_IDCA();
                                salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !salesman.equals(MyUI.get().getLoggedSalesman());
                                // Tree_CRMSingleCase cc = new Tree_CRMSingleCase("", crmSell.getFK_IDCA());
                                crudForm = new Form_CRMSell(crmSell, newCase, readOnly);
                                winFormCaption = CRM_EXISTING_SELL_CASES.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        Tree_CRM_Sell_SingleCase csct;

                        // kreiraj stablo s desne strane samo za ovaj crm slučaj
                        try {
                            if (crudForm instanceof Form_CRMSell) {
                                csct = new Tree_CRM_Sell_SingleCase(salesman.toString(), crmCase, crudForm, true);
                            } else {
                                csct = new Tree_CRM_Sell_SingleCase(salesman.toString(), crmCase, true);
                            }

                            propTrees.add(csct);
                            propPanel.addComponent((Component) csct);
                        } catch (NullPointerException | CustomTreeNodesEmptyException npe1) {
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRM_Sell_SingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0
                                ? "Existing Sells" : "No Sells For This Case", propPanel);

                        if (crudForm instanceof Form_CRMSell) {
                            // Make window bigger in height to accomodate all form fields.
                            winFormHeight = WINDOW_HEIGHT_DEFAULT_BIG;

                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            570, 890, Unit.PIXELS,
                                            readOnly,
                                            crudForm.getClickListener(),
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );

                        } else {
                            getUI().addWindow(new WindowForm3(
                                    winFormCaption,
                                    crudForm,
                                    495, 750, Unit.PIXELS,
                                    imageLocation, "Save",
                                    crudForm.getClickListener(), 196, 236, false)
                            );

                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/sells !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (NullPointerException e) {
            }
        };
    }
    //</editor-fold>
}
