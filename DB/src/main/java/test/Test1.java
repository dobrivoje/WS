/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.CrmCase;
import db.ent.CrmStatus;
import db.interfaces.ICRMController;
import db.interfaces.ISalesmanController;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author root
 */
public class Test1 {

    static ICRMController CC = DataService.getDefault().getCRMController();
    static ISalesmanController SC = DataService.getDefault().getSalesmanController();

    static final Set<CrmStatus> S_ALL = new HashSet(CC.getCRM_AllStatuses());

    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {

        List<CrmStatus> START_END = CC.getCRM_Statuses("start", "end");
        Set<CrmStatus> S_START_END = new HashSet<>(START_END);

        CrmStatus START = CC.getCRM_Statuses("start").get(0);
        CrmStatus CURRENT = CC.getCRM_Statuses("current").get(0);
        CrmStatus END = CC.getCRM_Statuses("end").get(0);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<CrmCase> cc1 = CC.getCRM_Cases(SC.getByID(1L), false);
        System.err.println("active crm cases, " + cc1);
        for (CrmCase c : cc1) {
            System.err.println(c.getDescription());
            System.err.println("|____" + c.getIdca() + ", " + Arrays.toString(c.getCrmProcessList().toArray()));
        }

        CrmCase c2 = CC.getCRM_Case(5);
        List<CrmStatus> statusi2 = dbh.getCRM_CaseStatuses(c2);
        Set<CrmStatus> ss2 = new HashSet<>(statusi2);
        System.err.println("crm case : " + c2.getDescription() + "-> statuses : " + statusi2);

        CrmCase Dekic_Case1 = CC.getCRM_Case(4);
        List<CrmStatus> statusi3 = CC.getCRM_CaseStatuses(Dekic_Case1);
        Set<CrmStatus> ss3 = new HashSet<>(statusi3);
        System.err.println("crm case : " + Dekic_Case1.getDescription() + "-> statuses : " + statusi3);

        System.err.println("ss3 = " + ss3);
        System.err.println("preostaju sledeći statusi : " + CC.getCRM_AvailableStatuses(Dekic_Case1));
    }
}
