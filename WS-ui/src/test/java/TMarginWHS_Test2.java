


import Excel.FS.ExcelTMarginWHS;
import dataservice.DataService;
import db.ent.TMarginWHS;
import db.interfaces.ICRUD2;

public class TMarginWHS_Test2 {

    static String path = "D:\\Users\\dprtenjak\\Desktop\\Projekti\\SOFTWARE\\WS\\Vanja Potrosnje\\vanja izvestaj NAV.xlsx";
    static String pathKodKuce = "D:\\DEVEL\\MOL\\WS\\Vanja Potrosnje\\vanja izvestaj NAV.xlsx";

    public static void main(String[] args) {
        test2(path);
    }

    public static void test2(String path) {
        ICRUD2<TMarginWHS> TMC = DataService.getDefault().getTMarginWHSController();

        try {
            ExcelTMarginWHS ETMW = new ExcelTMarginWHS(path);

            System.err.println("test 1 - ispis pariranog excel-a");
            System.err.println("----------------------------------------------------------------------");
            System.err.println(ETMW.getExcelProcessedList());
            System.err.println("");
            System.err.println("----------------------------------------------------------------------");

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
            } catch (Exception e1) {
                System.err.println("greška Upis u bazu :");
                System.err.println(e1.toString());
            }
        } catch (Exception ex) {
            System.err.println("greška:");
            System.err.println(ex.toString());
        }

    }
}
