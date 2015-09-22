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

    private final Calendar c = new GregorianCalendar();

    /**
     * pozivanje ovog konstruktora postavlja interval datuma od dva meseca
     * unazad do danas.
     */
    public Dates() {
        this(-2);
    }

    public Dates(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    /**
     *
     * @param months from months back/forth, to the current date
     */
    public Dates(int months) {
        setFrom1(months);
    }

    /**
     *
     * @param months from months back/forth, to the current date
     */
    public void setFrom(int months) {
        setFrom1(months);
    }

    private void setFrom1(int months) {
        this.to = new Date();

        synchronized (this) {
            c.add(Calendar.MONTH, months);
            c.set(Calendar.DAY_OF_MONTH, 1);

            this.from = c.getTime();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getter/setter">
    public synchronized Date getFrom() {
        return from;
    }

    public synchronized void setFrom(Date from) {
        this.from = from;
    }

    public void setFrom(int day, int month, int year) {
        synchronized (this) {
            c.set(Calendar.MONTH, month - 1);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.YEAR, year);
        }

        this.from = c.getTime();
    }

    public synchronized Date getTo() {
        return to;
    }

    public synchronized void setTo(Date to) {
        this.to = to;
    }

    public void setTo(int day, int month, int year) {
        synchronized (this) {
            c.set(Calendar.MONTH, month - 1);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.YEAR, year);
        }

        this.to = c.getTime();
    }
    //</editor-fold>
}
