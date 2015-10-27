/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM;

import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMProcess;
import Forms.Form_CRUD2;
import static Menu.MenuDefinitions.CRM_MANAG_EXISTING_PROCESS;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.BussinesLine;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Salesman;
import java.util.Date;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import ws.MyUI;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Tree_SalesmanCRMCases extends CustomObjectTree<CrmCase> {

    private Form_CRUD2 crudForm;
    private Salesman salesman;

    public Tree_SalesmanCRMCases(String caption, Salesman salesman, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        this(caption, salesman, false, formAllowed);
    }

    public Tree_SalesmanCRMCases(String caption, Salesman salesman, boolean caseFinished, boolean formAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCRMController().getCRM_Cases(salesman, caseFinished), caseFinished);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        super.addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                CrmCase crmCase = (CrmCase) event.getItemId();
                                this.salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                crudForm = new Form_CRMCase(crmCase, null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                CrmProcess crmProcess = (CrmProcess) event.getItemId();
                                this.salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                Tree_SalesmanCRMCases cc = new Tree_SalesmanCRMCases("", salesman, formAllowed);
                                crudForm = new Form_CRMProcess(crmProcess, cc, false, readOnly);

                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        Tree_CRMSingleCase csct;

                        for (CrmCase ac : DS.getCRMController().getCRM_Cases(salesman, false)) {
                            if (crudForm instanceof Form_CRMProcess) {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac, crudForm);
                            } else {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac);
                            }

                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Open CRM Cases" : "No Active Salesman CRM Case", propPanel);

                        if (readOnly) {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            readOnly,
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        } else {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            crudForm.getClickListener(),
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
            }
        });
        //</editor-fold>
    }

    public Tree_SalesmanCRMCases(String caption, Salesman salesman, boolean casesFinished, boolean salesAgreed, boolean formAllowed,
            Date startDate, Date endDate) throws CustomTreeNodesEmptyException, NullPointerException {

        super(caption, DS.getCRMController().getCRM_CasesStats(salesman, casesFinished, salesAgreed, startDate, endDate), casesFinished);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        super.addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                CrmCase crmCase = (CrmCase) event.getItemId();
                                this.salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                crudForm = new Form_CRMCase(crmCase, null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                CrmProcess crmProcess = (CrmProcess) event.getItemId();
                                this.salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                Tree_SalesmanCRMCases cc = new Tree_SalesmanCRMCases("", salesman, true, false, formAllowed, startDate, endDate);
                                crudForm = new Form_CRMProcess(crmProcess, cc, false, readOnly);

                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        Tree_CRMSingleCase csct;

                        for (CrmCase ac : DS.getCRMController().getCRM_CasesStats(salesman, true, false, startDate, endDate)) {
                            if (crudForm instanceof Form_CRMProcess) {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac, crudForm);
                            } else {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac);
                            }

                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Open CRM Cases" : "No Active Salesman CRM Case", propPanel);

                        if (readOnly) {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            readOnly,
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        } else {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            crudForm.getClickListener(),
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
            }
        });
        //</editor-fold>
    }

    public Tree_SalesmanCRMCases(String caption, Salesman salesman, boolean casesFinished, boolean salesAgreed, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {

        super(caption, DS.getCRMController().getCRM_CasesStats(salesman, casesFinished, salesAgreed), casesFinished);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        super.addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                CrmCase crmCase = (CrmCase) event.getItemId();
                                this.salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                crudForm = new Form_CRMCase(crmCase, null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                CrmProcess crmProcess = (CrmProcess) event.getItemId();
                                this.salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                Tree_SalesmanCRMCases cc = new Tree_SalesmanCRMCases("", salesman, true, false, formAllowed);
                                crudForm = new Form_CRMProcess(crmProcess, cc, false, readOnly);

                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        Tree_CRMSingleCase csct;

                        for (CrmCase ac : DS.getCRMController().getCRM_CasesStats(salesman, true, false)) {
                            if (crudForm instanceof Form_CRMProcess) {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac, crudForm);
                            } else {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac);
                            }

                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Open CRM Cases" : "No Active Salesman CRM Case", propPanel);

                        if (readOnly) {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            readOnly,
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        } else {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            crudForm.getClickListener(),
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
            }
        });
        //</editor-fold>
    }

    public Tree_SalesmanCRMCases(String caption, BussinesLine bl, boolean casesFinished, boolean salesAgreed, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {

        super(caption, DS.getCRMController().getCRM_CasesStats(bl, casesFinished, salesAgreed), casesFinished);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        super.addItemClickListener((ItemClickEvent event) -> {
            propTrees.clear();
            propPanel.removeAllComponents();

            try {
                if (event.isDoubleClick()) {
                    if (formAllowed) {
                        //<editor-fold defaultstate="collapsed" desc="CRM Case">
                        if (event.getItemId() instanceof CrmCase) {
                            try {
                                CrmCase crmCase = (CrmCase) event.getItemId();
                                this.salesman = crmCase.getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                crudForm = new Form_CRMCase(crmCase, null, false, readOnly);

                                winFormCaption = "Existing CRM Case";
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CRM Process...">
                        if (event.getItemId() instanceof CrmProcess) {
                            try {
                                CrmProcess crmProcess = (CrmProcess) event.getItemId();
                                this.salesman = crmProcess.getFK_IDCA().getFK_IDRSC().getFK_IDS();

                                readOnly = !this.salesman.equals(MyUI.get().getLoggedSalesman());

                                Tree_SalesmanCRMCases cc = new Tree_SalesmanCRMCases("", salesman, formAllowed);
                                crudForm = new Form_CRMProcess(crmProcess, cc, false, readOnly);

                                winFormCaption = CRM_MANAG_EXISTING_PROCESS.toString();
                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Open form">
                        Tree_CRMSingleCase csct;

                        for (CrmCase ac : DS.getCRMController().getCRM_Cases(salesman, false)) {
                            if (crudForm instanceof Form_CRMProcess) {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac, crudForm);
                            } else {
                                csct = new Tree_CRMSingleCase(this.salesman.toString(), ac);
                            }

                            propTrees.add(csct);

                            propPanel.addComponent(csct);
                        }

                        propTrees.stream().forEach((ct) -> {
                            ((Tree_CRMSingleCase) ct).refreshVisualContainer();
                        });

                        winFormPropPanel = new Panel(propPanel.getComponentCount() > 0 ? "Open CRM Cases" : "No Active Salesman CRM Case", propPanel);

                        if (readOnly) {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            readOnly,
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        } else {
                            getUI().addWindow(
                                    new WindowFormProp(
                                            winFormCaption,
                                            false,
                                            crudForm.getClickListener(),
                                            crudForm,
                                            winFormPropPanel
                                    )
                            );
                        }
                        //</editor-fold>
                    } else {
                        Notification.show("User Rights Error", "You don't have rights for \ncustomer cases/processes !",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }

            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
            }
        });
        //</editor-fold>
    }

    @Override
    protected void createSubNodes(CrmCase cc) {
        createNodeItems(cc, DS.getCRMController().getCRM_Processes(cc));
    }
}
