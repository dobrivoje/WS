/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public interface ICRUD2<T> extends ICRUD<T>, IDelete {

    public void addAll(List<T> list) throws Exception;

}
