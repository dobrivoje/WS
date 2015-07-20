/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

public enum REPORTS {

    REPORT1("rep1/report1.jasper");

    private final String name;

    private REPORTS(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
