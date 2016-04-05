
import static Main.MyUI.DS;
import Trees.Tree_MD_CrmCaseProcesses;
import com.vaadin.ui.Panel;
import db.ent.CrmCase;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class TestsDB {

    public static void main(String[] args) {

        CustomSearchData csd = new CustomSearchData();
        csd.setCaseFinished(false);
        csd.setSaleAgreeded(false);
        csd.setSalesman(DS.getSalesmanController().getByID(1L));

        System.err.println("test 1 : DS.getSearchController().getCRMCases(csd)");
        for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd)) {
            System.err.println("CRM : " + cc + ", ID=" + cc.getIdca());
            System.err.println(" |____ " + cc.getCrmProcessList());

            System.err.println("");
            System.err.println("");
        }

        csd.setCaseFinished(true);
        csd.setSaleAgreeded(true);

        System.err.println("test 2 : DS.getSearchController().getCRMCases(csd)");
        for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd)) {

            if (!cc.getRelSALEList().isEmpty()) {
                System.err.println("CRM : " + cc + ", ID=" + cc.getIdca());
                System.err.println(" |____ " + cc.getRelSALEList());
            }

            System.err.println("");
        }

        System.err.println("");
        System.err.println("");

        Salesman SS = DS.getSalesmanController().getByID(4L);

        CustomSearchData csd4 = new CustomSearchData();
        csd4.setCaseFinished(false);
        csd4.setSaleAgreeded(false);
        csd4.setBussinesLine(SS.getFkIdbl());

        for (Salesman S : DS.getSearchController().getSalesreps(csd4)) {
            csd4.setSalesman(S);

            System.err.println("----------------------------------------------------------");
            System.err.println("");
            System.err.println("Salesman : " + S);
            System.err.println("Cases : ");
            List crmCases = DS.getSearchController().getCRMCases(csd4);
            System.err.println(crmCases);
        }
    }
}
