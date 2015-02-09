/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Tables;

import com.vaadin.ui.Table;

/**
 *
 * @author root
 */
public class CustomTable extends Table implements IRefreshVisualContainer {

    public CustomTable() {
        super();
    }

    @Override
    public void refreshVisualContainer() {
        markAsDirtyRecursive();
    }
}
