/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author root
 */
public enum ISUserType {

    ADMIN(0),
    ТЕST(1),
    TOP_MANAGER(2),
    SECTOR_MANAGER(3),
    SALESMAN(4);

    private final int value;

    private ISUserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
