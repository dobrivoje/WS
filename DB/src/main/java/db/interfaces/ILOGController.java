/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.InfSysUser;
import db.ent.Log;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public interface ILOGController {

    Log getByID(Long ID);

    List<Log> getLogByInfSysUser(InfSysUser isu);

    List<Log> getLogByInfSysUser(InfSysUser isu, Date dateFrom, Date dateTo);

    void addNew(Date logDate, String actionCode, String description, InfSysUser infSysUser) throws Exception;

    void addNew(Log newObject) throws Exception;

}
