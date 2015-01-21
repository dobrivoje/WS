/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moji.testovi;

import com.vaadin.ui.Button;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author root
 */
public class test1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> mainMenuItems = new ArrayList<>(Arrays.asList(new String[]{"New Customer", "Bussines Types", "Tasks"}));
        Map<String, List<String>> subMenuItems = new HashMap<>();

        Map<String, List<String>> subMenuButtonCaptionsTest = new HashMap<>();

        subMenuItems.put(mainMenuItems.get(0), new ArrayList<>(Arrays.asList(new String[]{"Option 1-1", "Option 1-2", "Option 1-3"})));
        subMenuItems.put(mainMenuItems.get(1), new ArrayList<>(Arrays.asList(new String[]{"Option 2-1", "Option 2-2"})));
        subMenuItems.put(mainMenuItems.get(2), new ArrayList<>(Arrays.asList(new String[]{"Option 3-1", "Option 3-2", "Option 3-3", "Option 3-4", "Option 3-5"})));

        for (String mainMenuItem : mainMenuItems) {
            subMenuButtonCaptionsTest.put(mainMenuItem, subMenuItems.get(mainMenuItem));
        }

        for (String mainMenuItem : mainMenuItems) {
            System.err.println("mainMenuItem: " + mainMenuItem);

            for (String button : subMenuButtonCaptionsTest.get(mainMenuItem)) {
                System.err.println("|__" + button);
            }
        }
    }
}
