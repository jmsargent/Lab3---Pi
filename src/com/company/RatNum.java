package com.company;


import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.IllegalFormatException;
import java.util.Scanner;


/**
 * Creates Rational number
 */

public class RatNum {

    private int numerator;
    private int denominator;

    /**
     * Sets numerator = 0, and denominator = 1
     */
    public RatNum() {
        this.numerator = 0;
        this.denominator = 1;
    }

    /**
     * sets denominator to 1, parameter to numerator
     *
     * @param n numerator (int)
     */
    public RatNum(int n) {
        this.numerator = n;
        this.denominator = 1;
    }

    /**
     * Sets the rational number to the String argument, allowed formats are "a/b", "-a/b", "a/-b" or "a".
     *
     * @param s (String)
     */
    public RatNum(String s) {

        this(parse(s));

    }


    /**
     * Sets the rational number to the given int arguments. In the format (numerator) / (denominator)
     *
     * @param numerator
     * @param denominator
     */

    public RatNum(int numerator, int denominator) {

        if (denominator == 0)
            throw new NumberFormatException("Denominator = 0");

        if (gcd(numerator, denominator) != 1) {
            this.numerator = (numerator / gcd(numerator, denominator));
            this.denominator = (denominator / gcd(numerator, denominator));

        } else {

            this.numerator = numerator;
            this.denominator = denominator;

        }

        if (denominator < 0) {
            this.numerator = -this.numerator;
            this.denominator = Math.abs(this.denominator);
        }
    }


    /**
     * Copy constructor, creates a new ratnum with the same numerator and denominator as the given argument.
     *
     * @param ratnum
     */
    public RatNum(RatNum ratnum) {
        this.numerator = ratnum.getNumerator();
        this.denominator = ratnum.getDenominator();
    }

    /**
     * Takes String . Returns RatNum with the given parameters.
     * @param frac string parameter written in one of the following forms:  "a/b", "-a/b", "a/-b" or "a"
     */

    public static RatNum parse(String frac) {

        int pLen = frac.length();
        RatNum ratNum;
        int dashPos = frac.indexOf('/');

        if (dashPos == -1) {

            ratNum = new RatNum(Integer.parseInt(frac));

        } else {

            String sNumerator = frac.substring(0, dashPos);
            String sDenominator = frac.substring(dashPos + 1, pLen);
            ratNum = new RatNum(Integer.parseInt(sNumerator), Integer.parseInt(sDenominator));
        }

        return ratNum;
    }

    /**
     * Getter method for numerator.
     * @return numerator
     */

    public int getNumerator() {
        return this.numerator;
    }

    /**
     * Getter method for denominator.
     * @return denominator int
     */

    public int getDenominator() {
        return this.denominator;
    }

    /**
     * Setter method for numerator.
     * @param numerator int
     */



    /**
     * @param m First integer to compare
     * @param n Second integer to compare
     * @return the greatest common divisor commonly refered as the GCD(m,n)
     */

    static int gcd(int m, int n) {

        int gcd = 1;

        if (n == 0 && m == 0) {
            throw new IllegalArgumentException();
        }

        /* if anyone of the 2 integers is zero then return the absolute value of the other number
        if they're the same value, return  the absolute value of any of them*/
        if (m == 0) {
            return Math.abs(n);
        } else if (n == 0) {
            return Math.abs(m);
        } else if (n == m) {
            return Math.abs(n);
        }

        // The absolute value of the number closest to zero is the biggest possible cd for any 2 numbers
        if (Math.abs(m) > Math.abs(n)) {

            for (int i = Math.abs(m); i > 0; i--) {

                if (m % i == 0 && n % i == 0) {
                    return i;
                }
            }

        } else if (Math.abs(m) < Math.abs(n)) {
            for (int i = Math.abs(n); i > 0; i--) {
                if (m % i == 0 && n % i == 0) {
                    return i;
                }
            }
        }
        return gcd;
    }

    /**
     * @return The fraction in a String-format
     */
    @Override
    public String toString() {
        return (Integer.toString(this.numerator) + "/" + Integer.toString(this.denominator));
    }


    /**
     * @param r - object which you wanna compare to RatNum
     * @return true if the r is identical to the RatNum
     */
    @Override
    public boolean equals(Object r) {
        if (r == null) {
            return false;
        }
        if (!(r instanceof RatNum)) {
            return false;
        }

        RatNum r2 = (RatNum) r;
        return (this.numerator == r2.getNumerator() && this.denominator == r2.getDenominator());
    }


    /**
     * @param r RatNum you wanna compare
     * @return true if the parameter is smaller than the RatNum which it's being compared to.
     */
    public boolean lessThan(RatNum r) {

        int rExtnum, extNum;
        // extend fractions to the same denominators

        rExtnum = r.getNumerator() * this.getDenominator();
        extNum = this.numerator * r.getDenominator();

        if (extNum < rExtnum) {
            return true;
        }
        return false;
    }

    /**
     * @param r
     * @return RatNum sum
     */
    public RatNum add(RatNum r) {

        int sumNumerator, sumDenominator;

        // a/b + c/d = (a*d + c*b) / b*d
        sumDenominator = (this.denominator * r.getDenominator());
        sumNumerator = (this.numerator * r.getDenominator() + r.getNumerator() * this.getDenominator());

        //gcd happens here (WHOAH)
        return new RatNum(sumNumerator, sumDenominator);
    }


    /**
     * @param r (RatNum)
     * @return the difference between the RatNum and r as a new RatNum.
     */
    public RatNum sub(RatNum r) {

        int newNum, newDen;

        newNum = ((this.getNumerator() * r.getDenominator()) - (r.getNumerator() * this.getDenominator()));
        newDen = this.getDenominator() * r.getDenominator();

        return new RatNum(newNum, newDen);
    }

    /**
     * Returns the product of The ratnum and the parameter r
     * @param r (RatNum)
     * @return resulting RatNum.
     */
    public RatNum mul(RatNum r) {
        return new RatNum((r.getNumerator() * this.getNumerator()), (r.getDenominator() * this.getDenominator()));
    }

    /**
     * Returns the resulting quota between the RatNum and its parameter
     * @param r (RatNum)
     * @return (RatNum)
     */
    public RatNum div(RatNum r) {
        return new RatNum((this.numerator * r.getDenominator()), (this.denominator * r.getNumerator()));
    }


    /**
     * Returns the rational number in decimal format rounded down
     * with the number of decimals provided as parameter
     * @param decimalCount (Int): Number of decimals
     * @return (String)
     */
    public String toDotString(int decimalCount) {

        String s = "a";
        int temp;

        temp = this.numerator / this.denominator;

        if (this.numerator < 0) {
            if (temp == 0) {
                s = "-" + String.valueOf(temp);
            } else {
                s = String.valueOf(temp);
            }
        } else {
            s = String.valueOf(temp);
        }

        if (decimalCount == 0)
            return s;

        // for decimalcount > 0 we need a decimalpoint
        s += ".";

        temp = this.numerator % this.denominator;
        temp = Math.abs(temp);
        for (int i = 0; i < decimalCount; i++) {

            temp *= 10;
            s += String.valueOf(temp / this.denominator);
            temp %= this.denominator;

        }
        System.out.println(s);
        return s;
    }

}




