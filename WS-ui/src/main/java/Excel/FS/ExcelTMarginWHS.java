package Excel.FS;

import db.ent.TMarginWHS;
import org.apache.poi.ss.usermodel.Row;
import org.superbapps.utils.excel.ExcelDobri;
import static org.superbapps.utils.excel.IExcelSupport.RESULTLIST;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author root
 */
public class ExcelTMarginWHS extends ExcelDobri {

    /**
     * Excel koji se generiše iz NAV-a, preskače prva dva reda !
     *
     * @param filePath
     * @throws Exception
     */
    public ExcelTMarginWHS(String filePath) throws Exception {
        super(filePath, 2, (Row r) -> {
            RESULTLIST.add(new TMarginWHS(
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
        });
    }

}
