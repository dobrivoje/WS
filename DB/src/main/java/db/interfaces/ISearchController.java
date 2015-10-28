/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.RelSALE;
import java.util.List;

public interface ISearchController<T> {

    List<RelSALE> getAllSales(T t);

    List<CrmCase> getAllCrmCases(T t);

    List<CrmProcess> getAllCrmProcesses(T t);

}
