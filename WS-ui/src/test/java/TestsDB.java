
import static Main.MyUI.DS;
import db.ent.CrmCase;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.List;
import org.superb.apps.utilities.datum.Dates;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class TestsDB {

    private static final Dates dates = new Dates();

    public static void main(String[] args) {
        dates.setFrom(1, 6, 2015);
        dates.setTo(31, 12, 2015);

        for (Salesman s : DS.getSalesmanController().getAll()) {
            List<CrmCase> L = DS.getCRMController().getCRM_Salesrep_Sales_Cases(s, dates.getFrom(), dates.getTo());

            if (!L.isEmpty()) {
                for (CrmCase cc : L) {
                    List<RelSALE> RS = DS.getCRMController().getCRM_Sales(cc);

                    System.err.println("salesrep: " + s + ", crm case : " + cc);
                    System.err.println("sales: " + RS);
                }

            }
        }
    }
}
