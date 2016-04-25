/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.common;

/**
 *
 * @author root
 * @param <B1>
 * @param <B2>
 */
public interface IDocBeans<B1, B2> {

    void setBean1(B1 bean);

    B1 getBean1();

    void setBean2(B2 bean);

    B2 getBean2();

}
