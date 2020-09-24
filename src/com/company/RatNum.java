package com.company;


import java.io.File;
import java.io.IOException;
import java.math.*;
import java.sql.SQLOutput;
import java.util.IllegalFormatException;
import java.util.Scanner;


public class RatNum {

    private BigInteger numerator;
    private BigInteger denominator;

    private RatNum(BigInteger n, BigInteger d) {
    BigInteger numerator = new BigInteger(String.valueOf(n));
    BigInteger denominator = new BigInteger(String.valueOf(d));
    }

    public RatNum(int n, int d) {
        this(new BigInteger(String.valueOf(n)),
                new BigInteger(String.valueOf(d)));
    }

    public RatNum() {
        numerator = BigInteger.ZERO;
        denominator = BigInteger.ONE;
    }

    public RatNum(BigInteger numerator) {
        numerator = new BigInteger(String.valueOf(numerator));
        denominator = BigInteger.ONE;
    }

    public RatNum(RatNum ratNum) {

        this.parse(ratNum);

    }

/*
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
*/

    /**
     * Copy constructor, creates a new ratnum with the same numerator and denominator as the given argument.
     *
     * @param ratnum
     */
    public RatNum(RatNum ratnum) {
        numerator = ratnum.numerator;
        denominator = ratnum.denominator;
    }

    /**
     * Takes String . Returns RatNum with the given parameters.
     *
     * @param frac string parameter written in one of the following forms:  "a/b", "-a/b", "a/-b" or "a"
     */

    public static RatNum parse(RatNum frac){
        String tal = frac.toString();
        RatNum ratNum;
        int pLen = tal.length();
        int dashPos = tal.indexOf('/');

        if (dashPos == -1) {

            ratNum = new RatNum(frac);

        } else {

            String sNumerator = tal.substring(0, dashPos);
            String sDenominator = tal.substring(dashPos + 1, pLen);
            ratNum = new RatNum(Integer.parseInt(sNumerator), Integer.parseInt(sDenominator));
        }

        return ratNum;
    }

//    public int getNumerator() {
//        return this.numerator;
//    }


//    public int getDenominator() {
//        return this.denominator;
//    }


/*    static int gcd(int m, int n) {

        int gcd = 1;

        if (n == 0 && m == 0) {
            throw new IllegalArgumentException();
        }

        //if either of the 2 integers is zero then return the absolute value of the other number
        //if they're the same value, return  the absolute value of any of them
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
    } */

    /**
     * @return The fraction in a String-format
     */
    @Override
    public String toString() {
        return (numerator.toString()) + "/" + (denominator.toString());
    }


    /**
     * @param r - object which you wanna compare to RatNum
     * @return true if the r is identical to the RatNum
     */
/*    @Override
    public boolean equals(Object r) {
        if (r == null) {
            return false;
        }
        if (!(r instanceof RatNum)) {
            return false;
        }
        int s = numerator.equals(RatNum.numerator);
        RatNum r2 = (RatNum) r;
        return (this.numerator == r2.getNumerator() && this.denominator == r2.getDenominator());
    }

*/
    /**
     * @param r RatNum you wanna compare
     * @return true if the parameter is smaller than the RatNum which it's being compared to.
     */
    public boolean lessThan(RatNum r) {

       BigInteger rExtnum, extNum;
        // extend fractions to the same denominators
        rExtnum = denominator.multiply(r.numerator);
        extNum = numerator.multiply(r.denominator);

       // rExtnum = r.getNumerator() * this.getDenominator();
       // extNum = this.numerator * r.getDenominator();


        return (extNum.compareTo(rExtnum)) == -1;
    }

    /**
     * @param r
     * @return RatNum sum
     */
    public RatNum add(RatNum r) {

        BigInteger sumNumerator, sumDenominator;

        // a/b + c/d = (a*d + c*b) / b*d
        sumDenominator = (denominator.multiply(r.denominator));
        sumNumerator = (numerator.multiply(r.denominator)).add(r.numerator.multiply(denominator));

        //gcd happens here (WHOAH)
        return new RatNum(sumNumerator, sumDenominator);
    }


    /**
     * @param r (RatNum)
     * @return the difference between the RatNum and r as a new RatNum.
     */
    public RatNum sub(RatNum r) {

        BigInteger newNum, newDen;

        newNum = ((numerator.multiply(r.denominator)).subtract((r.numerator.multiply(denominator))));
        newDen = denominator.multiply(r.denominator);

        return new RatNum(newNum, newDen);
    }

    /**
     * Returns the product of The ratnum and the parameter r
     *
     * @param r (RatNum)
     * @return resulting RatNum.
     */
    public RatNum mul(RatNum r) {
        return new RatNum((r.numerator.multiply(numerator)), (r.denominator.multiply(denominator)));
    }

    /**
     * Returns the resulting quota between the RatNum and its parameter
     *
     * @param r (RatNum)
     * @return (RatNum)
     */
    public RatNum div(RatNum r) {
        return new RatNum((numerator.multiply(r.denominator)), (denominator.multiply(r.numerator)));
    }


    /**
     * Returns the rational number in decimal format rounded down
     * with the number of decimals provided as parameter
     *
     * @param decimalCount (Int): Number of decimals
     * @return (String)
     */
    public String toDotString(int decimalCount) {

        String s = "a";
        BigInteger temp;

        temp = numerator.divide(denominator);

        if (numerator.signum() < 0) {
            if (temp.signum() == 0) {
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

        temp = numerator.mod(denominator);
        temp = temp.abs();
        for (int i = 0; i < decimalCount; i++) {

            temp = temp.multiply(BigInteger.TEN);
            s += String.valueOf(temp.divide(denominator));
            temp = temp.mod(denominator);

        }
        System.out.println(s);
        return s;
    }

}




