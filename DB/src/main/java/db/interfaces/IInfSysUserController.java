/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.Salesman;
import java.util.List;
import db.ent.InfSysUser;

/**
 *
 * @author dprtenjak
 */
public interface IInfSysUserController {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    List<InfSysUser> getAll();

    InfSysUser getByID(long ID);

    InfSysUser getInfSysUser(Salesman salesman);

    Salesman getSalesman(InfSysUser user);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    //
    //</editor-fold>
}
