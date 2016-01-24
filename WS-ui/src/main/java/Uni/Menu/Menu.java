/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uni.Menu;

import Uni.MainMenu.MenuDefinitions;
import static Uni.MainMenu.MenuDefinitions.CUST_CRM_MANAG;
import com.vaadin.ui.Tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Uni.MainMenu.MenuDefinitions.CUST_DATA_MANAG;
import static Uni.MainMenu.MenuDefinitions.FS_DATA_MANAG;
import static Uni.MainMenu.MenuDefinitions.SYS_NOTIF_BOARD;

/**
 *
 * @author root
 */
public class Menu {

    private static Menu instance = null;

    private static List<MenuDefinitions> mainMenuItems;
    private static Map<MenuDefinitions, List<MenuDefinitions>> mainMenuSubOptions;

    private static final List<MenuDefinitions> allMenuItems = new ArrayList<>();

    private static Tree mainMenuTree;

    private Menu() {
        mainMenuItems = new ArrayList<>(MenuDefinitions.get_MainMenuItems());
        mainMenuSubOptions = new HashMap<>();
        mainMenuTree = new Tree();

        init();
        createTreeMenu();
    }

    public static synchronized Menu getDefault() {
        return instance == null ? instance = new Menu() : instance;
    }

    private void init() {
        //<editor-fold defaultstate="collapsed" desc="construct menu!">
        // Create submenu
        mainMenuSubOptions.put(SYS_NOTIF_BOARD, MenuDefinitions.get_SYS_NOTIF_BOARD_SubItems());
        mainMenuSubOptions.put(CUST_DATA_MANAG, MenuDefinitions.get_CUSTOMER_SubItems());
        mainMenuSubOptions.put(CUST_CRM_MANAG, MenuDefinitions.get_CRM_SubItems());
        mainMenuSubOptions.put(FS_DATA_MANAG, MenuDefinitions.get_FS_SubItems());

        allMenuItems.addAll(mainMenuItems);
        allMenuItems.addAll(MenuDefinitions.get_SYS_NOTIF_BOARD_SubItems());
        allMenuItems.addAll(MenuDefinitions.get_CUSTOMER_SubItems());
        allMenuItems.addAll(MenuDefinitions.get_CRM_SubItems());
        allMenuItems.addAll(MenuDefinitions.get_SALE_SubItems());
        allMenuItems.addAll(MenuDefinitions.get_FS_SubItems());
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

    public List<MenuDefinitions> getMAIN_MENU_SUB_OPTIONS(MenuDefinitions key) {
        return mainMenuSubOptions.get(key);
    }

    public Tree getMainMenuTree() {
        return mainMenuTree;
    }

    public List<MenuDefinitions> getAllMenuItems() {
        return allMenuItems;
    }
}
