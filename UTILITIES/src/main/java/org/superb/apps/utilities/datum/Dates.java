/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.datum;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Dates {

    private Date from;
    private Date to;

    /**
     * Pozivanje ovog konstruktora postavlja interval datuma : <br>
     * od 1. dana prethodnog meseca do današnjeg datuma.
     */
    public Dates() {
        this(-1);
    }

    public Dates(Dates d) {
        this.from = d.getFrom();
        this.to = d.getTo();
    }

    public Dates(Date from, Date to) {
        this.from = from;
        this.to = to;

        if (this.from.after(this.to)) {
            Date z = this.from;
            this.from = this.to;
            this.to = z;
        }
    }

    /**
     *
     * @param months Broj meseci - podešavanje intervala.
     * <p>
     * months > 0 : Od danas do poslednjeg dana za <u>months</u> unapred. <br>
     * months < 0 : Od 1. dana za <u>months</u> unazad, do danas. <br>
     */
    public Dates(int months) {
        setMonthsBF(months);
    }

    /**
     * @param months Broj meseci - podešavanje intervala.<br>
     * months &lt 0 : Interval je : Od 1. dana za <u>months</u> unazad, do
     * danas. <br>
     * months > 0 : Interval je : Od danas do poslednjeg dana za <u>months</u>
     * unapred. <br>
     */
    public synchronized void setMonthsBackForth(int months) {
        setMonthsBF(months);
    }

    //<editor-fold defaultstate="collapsed" desc="interni metodi">
    private Date setDMY(int month, int day, int year) {
        Calendar c1 = new GregorianCalendar();

        c1.set(Calendar.MONTH, month - 1);
        c1.set(Calendar.DAY_OF_MONTH, day);
        c1.set(Calendar.YEAR, year);

        return c1.getTime();
    }

    private void setMonthsBF(int months) {
        Calendar c1 = new GregorianCalendar();

        c1.add(Calendar.MONTH, months);

        if (months < 0) {
            c1.set(Calendar.DAY_OF_MONTH, 1);

            this.from = setHMS(c1, 0, 0, 0);
            this.to = new Date();
        } else {
            c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));

            this.from = new Date();
            this.to = setHMS(c1, 23, 59, 59);
        }

        if (from.after(to)) {
            Date z = from;
            from = to;
            to = z;
        }
    }

    private Date setHMS(Calendar c, int h, int m, int s) {
        c.set(Calendar.HOUR_OF_DAY, h);
        c.set(Calendar.MINUTE, m);
        c.set(Calendar.SECOND, s);

        return c.getTime();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter/setter">
    public synchronized Date getFrom() {
        return from;
    }

    public synchronized void setFrom(Date from) {
        this.from = from;
    }

    public void setFrom(int day, int month, int year) {
        this.from = setDMY(month, day, year);
    }

    public synchronized Date getTo() {
        return to;
    }

    public synchronized void setTo(Date to) {
        this.to = to;
    }

    public void setTo(int day, int month, int year) {
        this.to = setDMY(month, day, year);
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "["
                + from + ", " + to
                + "]";
    }

}
