/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moji.testovi;

import com.vaadin.ui.Tree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.superb.apps.ws.MainMenu.MenuDefinitions;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_CRM_MANAG;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_DATA_MANAG;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_DATA_MANAG_ALL_CUST;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_DATA_MANAG_CBT_LIST;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_DATA_MANAG_CUST_DOCS;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_DATA_MANAG_CUST_FS;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_DATA_MANAG_NEW_CBT;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.CUST_DATA_MANAG_NEW_CUST;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.FS_DATA_MANAG;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.FS_DATA_MANAG_DOCS;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.FS_DATA_MANAG_IMAGES;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.SYS_NOTIF_BOARD;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST;
import static org.superb.apps.ws.MainMenu.MenuDefinitions.SYS_NOTIF_BOARD_LICENCES_OVERDUE;

/**
 *
 * @author root
 */
public class test {

    private static List<MenuDefinitions> mainMenuItems;
    private static Map<MenuDefinitions, List<MenuDefinitions>> mainMenuSubOptions;
    private static Tree mainMenuTree;

    public static void main(String[] args) {
        mainMenuItems = new ArrayList<>();
        mainMenuSubOptions = new HashMap<>();
        mainMenuTree = new Tree();

        init();
        createTreeMenu();

        System.err.println(mainMenuItems.toString());
        System.err.println(mainMenuSubOptions.values().toString());

        List<MenuDefinitions> L = new ArrayList<>();

        for (Map.Entry<MenuDefinitions, List<MenuDefinitions>> entrySet : mainMenuSubOptions.entrySet()) {
            L.addAll(entrySet.getValue());
        }
        System.err.println(L.toString());

    }

    private static void init() {
        //<editor-fold defaultstate="collapsed" desc="construct menu!">
        mainMenuItems.add(SYS_NOTIF_BOARD);
        mainMenuItems.add(CUST_DATA_MANAG);
        mainMenuItems.add(CUST_CRM_MANAG);
        mainMenuItems.add(FS_DATA_MANAG);

        // Create submenu
        List<MenuDefinitions> sys_notif_board = new ArrayList<>(Arrays.asList(
                SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST,
                SYS_NOTIF_BOARD_LICENCES_OVERDUE));
        mainMenuSubOptions.put(SYS_NOTIF_BOARD, sys_notif_board);
        //
        List<MenuDefinitions> cust_data_manag = new ArrayList<>(Arrays.asList(
                CUST_DATA_MANAG_ALL_CUST,
                CUST_DATA_MANAG_NEW_CUST,
                CUST_DATA_MANAG_NEW_CBT,
                CUST_DATA_MANAG_CBT_LIST,
                CUST_DATA_MANAG_CUST_FS,
                CUST_DATA_MANAG_CUST_DOCS
        ));
        mainMenuSubOptions.put(CUST_DATA_MANAG, cust_data_manag);
        //
        List<MenuDefinitions> fs_data_manag = new ArrayList<>(Arrays.asList(
                FS_DATA_MANAG_IMAGES,
                FS_DATA_MANAG_DOCS
        ));
        mainMenuSubOptions.put(FS_DATA_MANAG, fs_data_manag);
        //</editor-fold>
    }

    private static void createTreeMenu() {
        mainMenuTree.addItems(mainMenuItems, mainMenuSubOptions.values());

        for (MenuDefinitions mainMenuItem : mainMenuItems) {
            mainMenuTree.setChildrenAllowed(mainMenuItem, true);

            if (mainMenuSubOptions.containsKey(mainMenuItem)) {
                List<MenuDefinitions> subMenuItems = mainMenuSubOptions.get(mainMenuItem);

                for (MenuDefinitions subMenuItem : subMenuItems) {
                    mainMenuTree.setParent(subMenuItem, mainMenuItem);
                    mainMenuTree.setChildrenAllowed(subMenuItem, false);
                    mainMenuTree.expandItemsRecursively(mainMenuItem);
                }
            }
        }
    }

}
