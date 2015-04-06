/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.auth.roles;

import java.io.Serializable;

/**
 *
 * @author dobri
 */
public class Roles implements Serializable {

    public static final String ROLE1 = "stampac_print_pdf";
    public static final String ROLE2 = "stampac_print_all";
    public static final String ROLE_ADMIN = "admin";

    public static final String PERMISSION2 = "xerox5225:print:*";
    public static final String PERMISSION1 = "xerox5225:print:pdf";

}
