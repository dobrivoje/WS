/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import static enums.REPORTS.REPORT1;
import gen.ReportGenerator1;

/**
 *
 * @author root
 */
public class NewMain {

    // private static dataservice.DataService DS = DataService.getDefault();
    public static void main(String[] args) {

        try {
            new ReportGenerator1().generateReport(REPORT1);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }

}
