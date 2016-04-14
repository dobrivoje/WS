
import dataservice.DataService;
import db.ent.TMarginWHS;
import db.interfaces.ICRUD2;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TMarginWHS_Test {

    //@Test
    public void test() {
        ICRUD2<TMarginWHS> TMC = DataService.getDefault().getTMarginWHSController();

        System.err.println("pre pokretanja testa (prazno) : ");
        System.err.println(TMC.getAll());

        List<TMarginWHS> L = new ArrayList<>();

        String fName = "D:\\Users\\dprtenjak\\Desktop\\Projekti\\SOFTWARE\\WS\\Vanja Potrosnje\\vanja izvestaj NAV.xlsx";
        int indeks = 0;

        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(new File(fName)));
            Sheet sheet = workbook.getSheetAt(0);

            for (Row r : sheet) {
                if (++indeks > 2) {
                    try {
                        L.add(new TMarginWHS(
                                r.getCell(0).getStringCellValue(),
                                r.getCell(1).getStringCellValue(),
                                r.getCell(2).getStringCellValue(),
                                r.getCell(3).getStringCellValue(),
                                r.getCell(4).getStringCellValue(),
                                r.getCell(5).getStringCellValue(),
                                r.getCell(6).getStringCellValue(),
                                r.getCell(7).getDateCellValue(),
                                r.getCell(8).getStringCellValue(),
                                r.getCell(9).getStringCellValue(),
                                r.getCell(10).getNumericCellValue(),
                                r.getCell(11).getStringCellValue(),
                                r.getCell(12).getNumericCellValue(),
                                r.getCell(13).getNumericCellValue(),
                                r.getCell(14).getNumericCellValue(),
                                r.getCell(15).getNumericCellValue(),
                                r.getCell(16).getStringCellValue(),
                                r.getCell(17).getStringCellValue(),
                                r.getCell(18).getStringCellValue()
                        )
                        );

                    } catch (Exception e) {
                        System.err.println("greška u for row petlji : " + e.toString());
                        System.err.println("indeks : " + indeks);
                    }
                }
            }

        } catch (Exception ex) {
            System.err.println("greška !");
            System.err.println(ex.toString());
        }

        System.err.println("BRISANJE IZ BAZE.");
        System.err.println("");

        try {
            TMC.deleteAll();
        } catch (Exception ex) {
        }

        System.err.println("UPIS U BAZU.");
        System.err.println("");

        try {
            TMC.addAll(L);
        } catch (Exception ex) {
        }

        /*
         System.err.println("TEST : sadržaj baze :");
         System.err.println("");
        
         TMC.getAll().stream().forEach(t -> {
         System.err.println(t);
         });
         */
    }
}
