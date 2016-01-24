/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.datum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.dobrivoje.utils.date.formats.DateFormat;

public class Dates {

    private Date from;
    private Date to;
    private DateFormat dateFormat;

    /**
     * Pozivanje ovog konstruktora postavlja interval datuma : <br>
     * od 1. dana prethodnog meseca do današnjeg datuma.
     */
    public Dates() {
        this(-1);
    }

    /**
     * Pozivanje ovog konstruktora postavlja interval datuma : <br>
     * od 1. dana prethodnog meseca do današnjeg datuma (justThatMonth=false).
     * od 1. dana prethodnog meseca do posledenjeg dana tog meseca
     * (justThatMonth=true).
     *
     * @param justThatMonth Do poslednjeg dana u mesecu.
     */
    public Dates(boolean justThatMonth) {
        this(-1, justThatMonth);
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
        this.dateFormat = DateFormat.DATE_FORMAT_ENG;
        setMonthsBF(months, false);
    }

    public Dates(int months, DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        setMonthsBF(months, false);
    }

    /**
     *
     * @param months Broj meseci - podešavanje intervala.
     * <p>
     * months > 0 : Od danas do poslednjeg dana za <u>months</u> unapred. <br>
     * months < 0 : Od 1. dana za <u>months</u> unazad, do dana određenog
     * parametrom justThatMonth. <br>
     *
     * @param justThatMonth=true: do poslednjeg dana tog meseca <br>
     * justThatMonth=false : do danas.<br>
     */
    public Dates(int months, boolean justThatMonth) {
        this.dateFormat = DateFormat.DATE_FORMAT_ENG;
        setMonthsBF(months, justThatMonth);
    }

    public Dates(int months, boolean justThatMonth, DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        setMonthsBF(months, justThatMonth);
    }

    /**
     * @param months Broj meseci - podešavanje intervala.<br>
     * months &lt 0 : Interval je : Od 1. dana za <u>months</u> unazad, do
     * danas, za justThatMonth=false.<br>
     *
     * months &lt 0 : Interval je : Od 1. dana za <u>months</u> unazad, do
     * poslednjeg dana u tom mesecu, za justThatMonth=true.<br>
     *
     * months > 0 : Interval je : Od danas do poslednjeg dana za <u>months</u>
     * unapred. <br>
     *
     * @param justThatMonth
     */
    public void setMonthsBackForth(int months, boolean justThatMonth) {
        setMonthsBF(months, justThatMonth);
    }

    public void setMonthsBackForth(int months) {
        setMonthsBF(months, false);
    }

    //<editor-fold defaultstate="collapsed" desc="interni metodi">
    private Date setDMY(int month, int day, int year) {
        Calendar c1 = new GregorianCalendar();

        c1.set(Calendar.MONTH, month - 1);
        c1.set(Calendar.DAY_OF_MONTH, day);
        c1.set(Calendar.YEAR, year);

        return c1.getTime();
    }

    private void setMonthsBF(int months, boolean justThatMonth) {
        Calendar c1 = new GregorianCalendar();

        c1.add(Calendar.MONTH, months);

        if (months < 0) {
            c1.set(Calendar.DAY_OF_MONTH, 1);

            this.from = setHMS(c1, 0, 0, 0);

            if (justThatMonth) {
                c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));
                this.to = setHMS(c1, 23, 59, 59);
            } else {
                this.to = new Date();
            }
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
    public Date getFrom() {
        return from;
    }

    public String getFrom(DateFormat dateFormat) {
        return new SimpleDateFormat(dateFormat.toString()).format(from);
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setFrom(int day, int month, int year) {
        this.from = setDMY(month, day, year);
    }

    public Date getTo() {
        return to;
    }

    public String getTo(DateFormat dateFormat) {
        return new SimpleDateFormat(dateFormat.toString()).format(to);
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public void setTo(int day, int month, int year) {
        this.to = setDMY(month, day, year);
    }

    /**
     * Default english format (yyyy-MM-dd)
     *
     * @return
     */
    public String getFromStr() {
        return getFromStr(dateFormat.toString());
    }

    /**
     * Default english format (yyyy-MM-dd)
     *
     * @return
     */
    public String getToStr() {
        return getToStr(dateFormat.toString());
    }

    /**
     * Custom format
     *
     * @param format Custom date format
     * @return
     */
    public String getFromStr(String format) {
        return new SimpleDateFormat(format).format(from);
    }

    /**
     * Custom format
     *
     * @param format Custom date format
     * @return
     */
    public String getToStr(String format) {
        return new SimpleDateFormat(format).format(to);
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "["
                + from + ", " + to
                + "]";
    }

}
