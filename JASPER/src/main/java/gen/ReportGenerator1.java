/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gen;

import enums.REPORTS;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author root
 */
public class ReportGenerator1 {

    //<editor-fold defaultstate="collapsed" desc="sys defs">
    private final Map<String, Object> jHashMap;
    private final List<String> listParamNames;
    private final List<Object> listParamObjects;
    private InputStream is;
    private OutputStream os;

    private static EntityManager entityManager;
    //</editor-fold>

    public ReportGenerator1() throws Exception {
        jHashMap = new HashMap<>();
        listParamNames = new ArrayList<>();
        listParamObjects = new ArrayList<>();

        entityManager = dataservice.DataService.getEM();
    }

    //<editor-fold defaultstate="collapsed" desc="setup parameters">
    public void setUpJParamNames(String... jKeys) {
        listParamNames.addAll(Arrays.asList(jKeys));
    }

    public void setUpJParamObjects(Object... jValues) {
        listParamObjects.addAll(Arrays.asList(jValues));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() throws Exception {
        if (listParamNames.size() != listParamObjects.size() || listParamNames == null || listParamObjects == null) {
            throw new Exception("Error. Parameters not valid.");
        } else {
            for (int i = 0; i < listParamNames.size(); i++) {
                jHashMap.put(listParamNames.get(i), listParamObjects.get(i));
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generateReport">
    private void genReport() {

        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    is,
                    jHashMap,
                    entityManager.unwrap(java.sql.Connection.class)
            );

            // JasperViewer.setDefaultLookAndFeelDecorated(false);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "\\\\GLADIATOR\\Users\\Public\\Downloads\\report.pdf");
            JasperViewer.viewReport(jasperPrint, false);
            entityManager.getTransaction().commit();
        } catch (NullPointerException | JRException e1) {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } catch (IllegalStateException e3) {
            entityManager.getTransaction().rollback();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Definicije Izveštaja.">
    public void generateReport(String reportPath) throws Exception {
        is = this.getClass().getClassLoader().getResourceAsStream(reportPath);

        init();
        genReport();
    }

    public void generateReport(REPORTS reports) throws Exception {
        generateReport(reports.toString());
    }

    public OutputStream exportPDF(REPORTS report) throws Exception {
        is = this.getClass().getClassLoader().getResourceAsStream(report.toString());

        init();

        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    is,
                    jHashMap,
                    entityManager.unwrap(java.sql.Connection.class)
            );
            
            JasperExportManager.exportReportToPdfStream(jasperPrint, os);

            entityManager.getTransaction().commit();
        } catch (NullPointerException | JRException e1) {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } catch (IllegalStateException e3) {
            entityManager.getTransaction().rollback();
        }

        return os;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="toString()">
    @Override
    public String toString() {
        String parameters = "";

        for (String s : listParamNames) {
            parameters += s + "->" + jHashMap.get(s) + "   ";
        }

        return parameters;
    }
//</editor-fold>
}
