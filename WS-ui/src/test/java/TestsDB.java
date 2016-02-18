
import static Main.MyUI.DS;
import db.ent.CrmCase;
import db.ent.custom.CustomSearchData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class TestsDB {

    public static void main(String[] args) {

        CustomSearchData csd2 = new CustomSearchData();
        csd2.setSalesman(DS.getSalesmanController().getByID(1L));

        for (CrmCase cc : DS.getSearchController().getCRMCases(csd2)) {
            System.err.println("CRM : " + cc);
            System.err.println(" |____ " + cc.getRelSALEList());

            System.err.println("");
            System.err.println("");
        }
    }
}
