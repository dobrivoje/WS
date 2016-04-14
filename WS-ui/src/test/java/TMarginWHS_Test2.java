
import Excel.FS.ExcelTMarginWHS;
import dataservice.DataService;
import db.ent.TMarginWHS;
import db.interfaces.ICRUD2;

public class TMarginWHS_Test2 {

    //@Test
    public void test2() {
        String path = "D:\\Users\\dprtenjak\\Desktop\\Projekti\\SOFTWARE\\WS\\Vanja Potrosnje\\vanja izvestaj NAV.xlsx";

        ICRUD2<TMarginWHS> TMC = DataService.getDefault().getTMarginWHSController();

        System.err.println("TEST : TMC.getAll()");
        System.err.println(TMC.getAll());

        try {
            ExcelTMarginWHS ETMW = new ExcelTMarginWHS(path);

            System.err.println("BRISANJE IZ BAZE.");
            System.err.println("");

            try {
                TMC.deleteAll();
            } catch (Exception ex) {
            }

            System.err.println("UPIS U BAZU.");
            System.err.println("");

            try {
                TMC.addAll(ETMW.getExcelProcessedList());
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
        }

    }
}
