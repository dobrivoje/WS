/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.MainMenu;

import com.vaadin.ui.Tree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class MainMenu {

    private static MainMenu instance = null;

    private static List<MenuDefinitions> mainMenuItems;
    private static Map<MenuDefinitions, List<MenuDefinitions>> mainMenuSubOptions;
    
    private static final List<MenuDefinitions> allMenuItems = new ArrayList<>();

    private static Tree mainMenuTree;

    private MainMenu() {
        mainMenuItems = new ArrayList<>();
        mainMenuSubOptions = new HashMap<>();
        mainMenuTree = new Tree();

        init();
        createTreeMenu();
    }

    public static MainMenu getDefault() {
        return instance == null ? instance = new MainMenu() : instance;
    }

    private void init() {
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
        
        allMenuItems.addAll(mainMenuItems);
        allMenuItems.addAll(sys_notif_board);
        allMenuItems.addAll(cust_data_manag);
        allMenuItems.addAll(fs_data_manag);
        //</editor-fold>
    }

    private void createTreeMenu() {
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

    public List<MenuDefinitions> getMainMenuItems() {
        return mainMenuItems;
    }

    public List<MenuDefinitions> getMAINMENU_SUB_OPTIONS(MenuDefinitions key) {
        return mainMenuSubOptions.get(key);
    }

    public Tree getMainMenuTree() {
        return mainMenuTree;
    }

    public List<MenuDefinitions> getAllMenuItems() {
        return allMenuItems;
    }
}
