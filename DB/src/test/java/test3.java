/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author root
 */
public class test3 {

    private static Integer mask;
    private static Integer options;

    public static void main(String[] args) {
        mask = (int) Math.pow(2, 9);
        options = 0b111100000;

        System.err.println("options=" + Integer.toBinaryString(mask));

        System.err.println("opt : " + Integer.toBinaryString(options));
        for (int i = 0; i < 8; i++) {

            switch (options & mask) {
                case 256:
                    System.err.println("startDate");
                    break;
                case 128:
                    System.err.println("endDate");
                    break;
                case 0b1000000:
                    System.err.println("salesman");
                    break;
                case 0b100000:
                    System.err.println("customer");
                    break;
                case 0b10000:
                    System.err.println("product");
                    break;
                case 0b1000:
                    System.err.println("quantity");
                    break;
                case 0b100:
                    System.err.println("moneyAmount");
                    break;
                case 0b10:
                    System.err.println("caseFinished");
                    break;
                case 0b1:
                    System.err.println("saleAgreeded");
                    break;
                default:
                    System.err.println("slučaj - nula.");

            }

            mask >>= 1;
        }

    }

}
