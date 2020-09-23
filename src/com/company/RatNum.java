package com.company;

import java.math.BigInteger;


/**
 * A class made to store Rational numbers, in the form of fractions a/b
 */

public class RatNum {


    private BigInteger num, den;


    public BigInteger getNum() {
        return num;
    }

    public BigInteger getDen() {
        return den;
    }


    /**
     * Sets numerator = 0, and denominator = 1
     */
    public RatNum() {
        this.num = BigInteger.ZERO;
        this.den = BigInteger.ONE;
    }

    /**
     * Sets numerator to n and denominator to 1
     *
     * @param n numerator
     */
    public RatNum(int n) {
        this(n,1);
    }

    /**
     * Sets the rational number to the String argument, allowed formats are "a/b", "-a/b", "a/-b" or "a".
     *
     * @param s (String)
     */

    public RatNum(String s) {
        this(parse(s));
    }



    private RatNum(BigInteger num, BigInteger den){


        // Because dividing by zero should be a crime.
        if(den.equals(BigInteger.ZERO))
            throw new NumberFormatException("Denominator = 0");

        // sets numerator and denominator to the provided values, shortens with the greatest common denominator
        this.num = num.divide(num.gcd(den));
        this.den = den.divide(num.gcd(den));

        // compare to returns -1 if less than argument, it's probably an overridden method which causes warning
        if(den.compareTo(BigInteger.ZERO) == -1){
            this.num = this.num.negate();
            this.den = this.den.negate();
        }
    }


    /**
     * Sets the rational number to the given int arguments. In the format (numerator) / (denominator)
     *
     * @param numerator
     * @param denominator
     */

    public RatNum(int numerator, int denominator) {

        this(new BigInteger(String.valueOf(numerator)),
                new BigInteger(String.valueOf(denominator)));

    }


    /**
     * Copy constructor, creates a new ratnum with the same numerator and denominator as the given argument.
     *
     * @param ratnum
     */
    public RatNum(RatNum ratnum) {
        this.num = ratnum.getNum();
        this.den = ratnum.getDen();
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



    /**
     * Getter method for denominator.
     * @return denominator int
     */

    public int getDenominator() {
        return this.den.intValue();
    }

    public int getNumerator() {
        return this.num.intValue();
    }



    /**
     * @return The fraction in a String-format
     */
    @Override
    public String toString() {
        return (this.num.toString() + "/" + this.den.toString());
    }

    /*
     * @param r - object which you wanna compare to RatNum
     * @return true if the r is identical to the RatNum
     */

    /*
    @Override
    public boolean equals(Object r) {
        if (r == null) {
            return false;
        }
        if (!(r instanceof RatNum)) {
            return false;
        }

        RatNum r2 = (RatNum) r;
        return (this.num.toString() == r2.getNum() && this.denominator == r2.getDenominator());
    }
*/

    /**
     * @param r RatNum you wanna compare
     * @return true if the parameter is smaller than the RatNum which it's being compared to.
     */
    public boolean lessThan(RatNum r) {

        int compRes;

        // extend fractions to the same denominators
        BigInteger rExtendedNum, extendedNum;

        rExtendedNum = r.getNum().multiply(this.den);
        extendedNum = this.num.multiply(r.getDen());

        compRes = extendedNum.compareTo(rExtendedNum);
        return compRes == -1;
    }

    /**
     * @param r
     * @return RatNum sum
     */
    public RatNum add(RatNum r) {

        int sumNumerator, sumDenominator;

        BigInteger sumNum, sumDen;

        // a/b + c/d = (a*d + c*b) / b*d

        // extend numerators & add them together
        sumNum = new BigInteger(this.num.multiply(r.getDen()).toString());
        sumNum = sumNum.add(r.getNum().multiply(this.num));

        // extend denominator
        sumDen = new BigInteger(this.den.multiply(r.getDen()).toString());
        return new RatNum(sumNum, sumDen);
    }


    /**
     * @param r (RatNum)
     * @return the difference between the RatNum and r as a new RatNum.
     */
    public RatNum sub(RatNum r) {

        BigInteger sumNum, sumDen;
        int newNum, newDen;

        // Extend numerators & subtract them
        sumNum = new BigInteger(this.num.multiply(r.getDen()).toString());
        sumNum = sumNum.subtract(r.getNum().multiply(this.num));

        // extend denominator
        sumDen = new BigInteger(this.den.multiply(r.getDen()).toString());

        newNum = ((this.getNumerator() * r.getDenominator()) - (r.getNumerator() * this.getDenominator()));
        newDen = this.getDenominator() * r.getDenominator();

        return new RatNum(sumNum, sumDen);
    }

    /**
     * Returns the product of The ratnum and the parameter r
     * @param r (RatNum)
     * @return resulting RatNum.
     */
    public RatNum mul(RatNum r) {
        return new RatNum(r.getNum().multiply(this.num),r.getDen().multiply(this.den));
    }

    /**
     * Returns the resulting quota between the RatNum and its parameter
     * @param r (RatNum)
     * @return (RatNum)
     */
    public RatNum div(RatNum r) {
        return new RatNum((this.num.multiply(r.getDen())), (this.den.multiply(r.getDen())));
    }


    /**
     * Returns the rational number in decimal format rounded down
     * with the number of decimals provided as parameter
     * @param decimalCount (Int): Number of decimals
     * @return (String)
     */
    public String toDotString(int decimalCount) {

        String s;
        BigInteger t;

        // t = num / den
        t = new BigInteger(this.num.divide(this.den).toString());


        // if num < 0
        if(this.num.compareTo(BigInteger.ZERO) == -1){
            // if t == 0
            if(t.compareTo(BigInteger.ZERO) == 0){
                s = "-" + t.toString();
            }else {
                s = t.toString();
            }
        }else {
            s = t.toString();
        }

        if (decimalCount == 0)
            return s;

        // for decimalcount > 0 we need a decimalpoint
        s += ".";

        t = this.num.mod(this.den).abs();

        t = this.num.mod(this.den).abs();
        for (int i = 0; i < decimalCount; i++) {
            t = t.multiply(BigInteger.TEN);
            s += t.divide(this.den).toString();
            t = t.mod(this.den);
        }

        /*
        temp = this.numerator % this.denominator;
        temp = Math.abs(temp);
        for (int i = 0; i < decimalCount; i++) {

            temp *= 10;
            s += String.valueOf(temp / this.denominator);
            temp %= this.denominator;

        }*/
        return s;
    }
}




