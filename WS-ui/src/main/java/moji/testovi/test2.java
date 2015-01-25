/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moji.testovi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author root
 */
public class test2 {

    private static List<String> mainMenuItems;
    private static Map<Integer, List<String>> subMenuButtons;

    public static void main(String[] args) {

        List<String> m = Arrays.asList("App Management", "Customers", "Tasks");
        Map<Integer, List<String>> smb = new HashMap<>();

        smb.put(0, Arrays.asList("1-11", "1-2222", "1--33333"));
        smb.put(1, Arrays.asList("2-11", "2-2222"));
        smb.put(2, Arrays.asList("3-11", "3-2", "3-333", "3-44444"));

        init(m, smb);

        for (int mainMenuItem : subMenuButtons.keySet()) {
            System.err.println("mainMenuItem: " + mainMenuItems.get(mainMenuItem));

            for (String button : subMenuButtons.get(mainMenuItem)) {
                System.err.println("|__" + button);
            }
        }

    }

    public static void init(List<String> mainMenuItems, Map<Integer, List<String>> subMenuButtons) {
        try {
            test2.mainMenuItems = new ArrayList<>(mainMenuItems);
            test2.subMenuButtons = new HashMap<>(subMenuButtons);
            createTabs();
        } catch (Exception e) {
            System.err.println("greška-" + e.toString());
        }

    }

    private static void createTabs() throws NullPointerException {
        for (int mainMenuItem : new TreeSet<>(subMenuButtons.keySet())) {
            for (String smb : subMenuButtons.get(mainMenuItem)) {
                smb = (Integer.toString((int) (Math.random() * 100)));
            }
        }
    }
}
