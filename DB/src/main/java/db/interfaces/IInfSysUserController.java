/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.Salesman;
import java.util.List;
import db.ent.InfSysUser;
import enums.ISUserType;

/**
 *
 * @author root
 */
public interface IInfSysUserController {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    List<InfSysUser> getAll();

    InfSysUser getByID(long ID);

    InfSysUser getByID(String shiroUserPrincipal);

    InfSysUser getInfSysUser(Salesman salesman);

    List<InfSysUser> getSectorManagerUsers(boolean sectorManager);

    List<InfSysUser> getTopManagerUsers(boolean topManager);

    List<InfSysUser> getAdminUsers(boolean admin);

    ISUserType getInfUserType(String shiroUserPrincipal);

    ISUserType getInfSysUserType(InfSysUser infSysUser);

    Salesman getSalesman(InfSysUser user);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    //
    //</editor-fold>
}
