/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.dobrivoje.utils.excel.exceptions.ExcelGenException;

/**
 *
 * @author root
 */
public interface IExcelSupport {

    /**
     * Synchronized list !
     */
    final List resultList = Collections.synchronizedList(new ArrayList());

    /**
     * U zavisnosti od samog bean-a <br>
     * napraviti preslikavanje podataka. <br>
     *
     * Npr. <br>
     * resultList.add(new TMarginWHS( <br>
     * ... <br>
     * row.getCell(7).getDateCellValue() <br>
     * ...
     *
     * @param row Pošto se radi iteracija kroz sve vrste<br>
     * metod mora imati <b>row</b> kao parametar.
     * @throws ExcelGenException
     */
    void setUpAndGenerate(Row row) throws ExcelGenException;
}
