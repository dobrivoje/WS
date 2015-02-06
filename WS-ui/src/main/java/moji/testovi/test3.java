/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moji.testovi;

/**
 *
 * @author root
 */
public class test3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int fileSize = 5000;
        for (int i = 1; i < fileSize; i++) {
            System.err.println(((float)i / fileSize));
        }
    }
}
