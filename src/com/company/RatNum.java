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
        this(n, 1);
    }

    /**
     * Sets the rational number to the String argument, allowed formats are "a/b", "-a/b", "a/-b" or "a".
     *
     * @param s (String)
     */

    public RatNum(String s) {
        this(parse(s));
    }


    private RatNum(BigInteger num, BigInteger den) {


        // Because dividing by zero should be a crime.
        if (den.equals(BigInteger.ZERO))
            throw new NumberFormatException("Denominator = 0");

        // sets numerator and denominator to the provided values, shortens with the greatest common denominator
        this.num = num.divide(num.gcd(den));
        this.den = den.divide(num.gcd(den));

        // compare to returns -1 if less than argument, it's probably an overridden method which causes warning
        if (den.compareTo(BigInteger.ZERO) == -1) {
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
     *
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
     *
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


    @Override
    public boolean equals(Object r) {
        if (r == null) {
            return false;
        }
        if (!(r instanceof RatNum)) {
            return false;
        }

        RatNum r2 = (RatNum) r;
        return (this.num.toString().equals(r2.getNum().toString())  && this.den.toString().equals(r2.getDen().toString()));
    }


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


        BigInteger sumNum,sumDen,t1,t2;

        t1 = this.num.multiply(r.getDen());
        t2 = r.getNum().multiply(this.den);

        sumNum = t1.add(t2);


        // extend denominator
        sumDen = new BigInteger(this.den.multiply(r.getDen()).toString());
        return new RatNum(sumNum, sumDen);
    }


    /**
     * @param r (RatNum)
     * @return the difference between the RatNum and r as a new RatNum.
     */
    public RatNum sub(RatNum r) {

        BigInteger sumNum,sumDen,t1,t2;

        t1 = this.num.multiply(r.getDen());
        t2 = r.getNum().multiply(this.den);
        sumNum = t1.subtract(t2);


        // extend denominator
        sumDen = new BigInteger(this.den.multiply(r.getDen()).toString());
        return new RatNum(sumNum, sumDen);
    }

    /**
     * Returns the product of The ratnum and the parameter r
     *
     * @param r (RatNum)
     * @return resulting RatNum.
     */
    public RatNum mul(RatNum r) {
        return new RatNum(r.getNum().multiply(this.num), r.getDen().multiply(this.den));
    }

    /**
     * Returns the resulting quota between the RatNum and its parameter
     *
     * @param r (RatNum)
     * @return (RatNum)
     */
    public RatNum div(RatNum r) {
        return new RatNum((this.num.multiply(r.getDen())), (this.den.multiply(r.getNum())));
    }

    /**
     * Raise to the power of the exponent.
     * @param exp - the
     * @return
     */
    public RatNum pow(int exp){
        return new RatNum(this.num.pow(exp),this.den.pow(exp));
    }


    /**
     * Returns the rational number in decimal format rounded down
     * with the number of decimals provided as parameter
     *
     * @param decimalCount (Int): Number of decimals
     */
    public String toDotString(int decimalCount) {

        boolean isNegative = this.num.compareTo(BigInteger.ZERO) == -1; // .comp returns -1 if smaller
        boolean isZero = this.num.compareTo(BigInteger.ZERO) == 0;
        BigInteger bigTemp = this.num.abs();


        String converted;

        // provides us with the digits before the decimalpoint
        if (isNegative && (!isZero)) {

            converted = "-" + bigTemp.divide(this.den).toString();

        } else {

            converted = bigTemp.divide(this.den).toString();

        }

        // Get the remainder

        if (decimalCount > 0) {
            converted += ".";
        }else{
            return converted;
        }

        bigTemp = bigTemp.mod(this.den);

        for (int i = 0; i < decimalCount; i++) {

            // if the remainder is zero all we have to do is concat decimalcount number of zeros
            if (bigTemp.equals(BigInteger.ZERO)) {
                converted += "0";
            } else {

                bigTemp = bigTemp.multiply(BigInteger.TEN);
                converted += bigTemp.divide(this.den);

                bigTemp = bigTemp.mod(this.den);
            }

        }

        return converted;
    }
}




