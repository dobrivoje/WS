/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.util.List;
import db.DBHandler;
import db.ent.Salesman;
import db.ent.InfSysUser;
import db.interfaces.IInfSysUserController;
import enums.ISUserType;

/**
 *
 * @author root
 */
public class InfSysUser_Controller implements IInfSysUserController {

    private static DBHandler dbh;

    public InfSysUser_Controller(DBHandler dbh) {
        InfSysUser_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<InfSysUser> getAll() {
        return dbh.getAllUsers();
    }

    @Override
    public InfSysUser getByID(long ID) {
        return dbh.getByID(ID);
    }

    @Override
    public InfSysUser getByID(String shiroUserPrincipal) {
        return dbh.getByID(shiroUserPrincipal);
    }

    @Override
    public InfSysUser getInfSysUser(Salesman salesman) {
        return dbh.getUser(salesman, true);
    }

    @Override
    public Salesman getSalesman(InfSysUser user) {
        return dbh.getSalesman(user, true);
    }

    @Override
    public List<InfSysUser> getSectorManagerUsers(boolean sectorManager) {
        return dbh.getSectorManagerUsers(sectorManager);
    }

    @Override
    public List<InfSysUser> getTopManagerUsers(boolean topManager) {
        return dbh.getTopManagerUsers(topManager);
    }

    @Override
    public List<InfSysUser> getAdminUsers(boolean admin) {
        return dbh.getAdminUsers(admin);
    }

    @Override
    public ISUserType getInfUserType(String shiroUserPrincipal) {
        return dbh.getInfUserType(shiroUserPrincipal);
    }

    @Override
    public ISUserType getInfSysUserType(InfSysUser infSysUser) {
        return dbh.getInfSysUserType(infSysUser);
    }
    //</editor-fold>
}
