/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 *
 * @author root
 */
public class Tests {

    public static void main(String[] args) {
        Sha256Hash s = new Sha256Hash("dedaMocika2001");
        Sha256Hash d = new Sha256Hash("123");
        System.err.println(s.toHex());
        System.err.println(d.toHex());
    }
}
