
import dataservice.DataService;
import db.DBHandler;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;

public class test2 {

    public static final DataService DS = DataService.getDefault();
    static ICRMController CC = DataService.getDefault().getCRMController();
    static ISalesmanController SC = DataService.getDefault().getSalesmanController();
    static IInfSysUserController IS = DataService.getDefault().getINFSYSUSERController();
    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {
        CustomSearchData csd4 = new CustomSearchData();
        csd4.setCaseFinished(true);
        csd4.setSaleAgreeded(true);
        //csd4.setMonthsBackForth(-1);
        csd4.setBussinesLine(SC.getByID(4L).getFkIdbl());

        System.err.println("S: " + SC.getByID(4L));
        for (Salesman s : DS.getSearchController().getSalesreps(csd4)) {
            csd4.setSalesman(s);
            System.err.println("SR :" + s);
            System.err.println("|____SC :" + DS.getSearchController().getCRMCases(csd4));
        }
    }
}
