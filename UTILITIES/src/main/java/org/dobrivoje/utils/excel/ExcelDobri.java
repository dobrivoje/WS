/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dobrivoje.utils.excel.exceptions.ExcelGenException;
import org.dobrivoje.utils.excel.exceptions.NoExcelFileException;

/**
 *
 * @author root
 */
public abstract class ExcelDobri {

    //<editor-fold defaultstate="collapsed" desc="Low-level Support">
    // pomocna varijabla
    private long ind;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="File Support">
    private String filePath;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Excel Support">
    private final Sheet sheet;

    private final int skipHeaderLines;
    private final long skipBottomLines;
    // private final int skipBottomLines;
    //</editor-fold>

    private final IExcelSupport excelSupport;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Single excel file with one indexed sheet,<br>
     * with indexes for skipping header and footer<br>
     * and custom interface for mapping custom columns.
     *
     * @param filePath
     * @param sheetIndex
     * @param skipHeaderLines Ako je -1, nema preskakanja !<br>
     * @param skipBottomLines Ako je -1, nema izostavljanja začelja.<br>
     * @param excelSupport
     * @throws Exception
     */
    public ExcelDobri(String filePath, int sheetIndex, int skipHeaderLines, long skipBottomLines, IExcelSupport excelSupport) throws Exception {
        this.filePath = filePath;
        this.skipHeaderLines = skipHeaderLines == -1 ? 0 : skipHeaderLines;
        this.skipBottomLines = skipBottomLines == -1L ? Long.MAX_VALUE : skipBottomLines;

        this.excelSupport = excelSupport;

        this.sheet = getWorkbook(filePath).getSheetAt(sheetIndex);
    }

    /**
     * Single excel file with one indexed sheet,<br>
     * with index for skipping header records,<br>
     * and custom interface for mapping custom columns.
     *
     * @param filePath
     * @param sheetIndex
     * @param skipHeaderLines Ako je -1, nema preskakanja !<br>
     * @param excelSupport
     * @throws Exception
     */
    public ExcelDobri(String filePath, int sheetIndex, int skipHeaderLines, IExcelSupport excelSupport) throws Exception {
        this(filePath, sheetIndex, skipHeaderLines, -1, excelSupport);
    }

    /**
     * Single excel file with one sheet<br>
     * with index for skipping header records,<br>
     * and custom interface for mapping custom columns.
     *
     * @param filePath
     * @param skipHeaderLines Ako je -1, nema preskakanja !<br>
     * @param excelSupport
     * @throws Exception
     */
    public ExcelDobri(String filePath, int skipHeaderLines, IExcelSupport excelSupport) throws Exception {
        this(filePath, 0, skipHeaderLines, excelSupport);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter/Setter">
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Support methods">
    private Workbook getWorkbook(String filePath) throws Exception {
        File f = new File(filePath);
        String ext = FilenameUtils.getExtension(filePath);
        Workbook wb;

        switch (ext.toLowerCase()) {
            case "xlsx":
                wb = new XSSFWorkbook(f);
                break;

            case "xls":
                wb = new HSSFWorkbook(new FileInputStream(f));
                break;

            default:
                throw new NoExcelFileException();
        }

        return wb;
    }

    protected void setUpBeanList() throws Exception {
        if (excelSupport == null) {
            throw new ExcelGenException();
        }

        if (sheet == null) {
            throw new NoExcelFileException();
        }

        for (Row r : sheet) {
            if (++ind > skipHeaderLines && ++ind < skipBottomLines) {
                excelSupport.setUpAndGenerate(r);
            }
        }
    }

    /**
     * Processed real excel file results.
     *
     * @return
     */
    public synchronized List getExcelProcessedList() {
        return IExcelSupport.resultList;
    }
    //</editor-fold>

}
