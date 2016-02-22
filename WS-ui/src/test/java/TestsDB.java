
import static Main.MyUI.DS;
import db.ent.CrmCase;
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
    }
}
