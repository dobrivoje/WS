/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.os;

/**
 *
 * @author Dobri
 */
public class OS {

    public static boolean isWindows() {
        return System.getProperty("os.name").trim().toLowerCase().contains("windows");
    }

    public static boolean isUnix() {
        return System.getProperty("os.name").trim().toLowerCase().contains("unix");
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").trim().toLowerCase().contains("linux");
    }

    public static String getWinSeparator() {
        return "\\";
    }

    public static String getLinuxSeparator() {
        return "/";
    }

    public static String getSeparator() {
        if (isLinux()) {
            return getLinuxSeparator();
        } else if (isWindows()) {
            return getWinSeparator();
        } else {
            return "";
        }
    }
}
