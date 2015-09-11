package Views.MainMenu.CRM;

import Trees.CRM.SALES.Tree_SalesmanSales;
import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.Tree_SalesmanCRMCases;
import Views.View_Dashboard;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import ws.MyUI;
import static ws.MyUI.DS;

public class View_CRM extends View_Dashboard {
    
    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);
    private final Component salesCasesPanelComponent;
    
    //<editor-fold defaultstate="collapsed" desc="pomoćna klasa Dates">
    class Dates {
        
        private Date from;
        private Date to;
        
        public Dates(Date from, Date to) {
            this.from = from;
            this.to = to;
        }
        
        public Date getFrom() {
            return from;
        }
        
        public void setFrom(Date from) {
            this.from = from;
        }
        
        public Date getTo() {
            return to;
        }
        
        public void setTo(Date to) {
            this.to = to;
        }
        
    }
    //</editor-fold>
    
    private final Dates dates;
    
    public View_CRM() {
        super("Customer Relationship Management");
        this.dates = new Dates(null, null);
        
        buildContentWithComponents(
                activeCasesBySalesmanPanel(),
                activeCasesByCustomerPanel(),
                salesCasesPanelComponent = salesCasesPanel(),
                createNotesPanel("")
        );
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component activeCasesBySalesmanPanel() {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                // listom ispod, kontrolišemo not null vrednosti,
                // da bi se stablo kreiralo, inače zbog null, stablo se neće kreirati.
                List<CrmCase> L = DS.getCRMController().getCRM_Cases(S, false);
                if (!L.isEmpty()) {
                    Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases("", S, formAllowed);
                    subPanels.add(new Panel(S.toString(), csct));
                }
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }
        
        return createPanelComponent("Active Salesmen CRM Cases", subPanels, formAllowed);
    }
    
    private Component activeCasesByCustomerPanel() {
        try {
            for (Customer C : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                Tree_CustomerCRMCases ccct = new Tree_CustomerCRMCases("", C, formAllowed);
                subPanels.add(new Panel(C.toString(), ccct));
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }
        
        return createPanelComponent("Active Customers CRM Cases", subPanels, formAllowed);
    }
    
    private Component salesCasesPanel() {
        panelCommands.put("Sales For Last Two Months", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dates.setFrom(null);
            dates.setTo(null);
            
            subPanels.clear();
        });
        
        panelCommands.put("Sales For Last Three Months", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.MONTH, -2);
                c.set(Calendar.DAY_OF_MONTH, 1);
                
                dates.setFrom(c.getTime());
                dates.setTo(new Date());
                
                subPanels.clear();
            }
        });
        
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {
                
                List<RelSALE> L = DS.getCRMController().getCRM_Sales(S, dates.getFrom(), dates.getTo());
                
                if (!L.isEmpty()) {
                    Tree_SalesmanSales tss = new Tree_SalesmanSales("", S, dates.getFrom(), dates.getTo(), formAllowed);
                    subPanels.add(new Panel(S.toString(), tss));
                }
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }
        
        return createPanelComponent("Curent/Last Month Sales", subPanels, formAllowed, panelCommands);
    }
    //</editor-fold>
}
