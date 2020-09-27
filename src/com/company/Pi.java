package com.company;


import java.math.BigInteger;

public class Pi {

    public static void main(String[] args){
        int decimalCount = 70;
        if (args.length > 0) {
            decimalCount = Integer.valueOf(args[0]);
        }
        final int laps = 100;

        RatNum res = new RatNum(0,1);


        RatNum factor0,factor1,t1,t2,t3,t0;

        // ersätt följande två rader med din lösning
        for (int i = 0; i < laps; i++) {


            // create components
            t0 = new RatNum(4,(8*i)+1);
            t1 = new RatNum(2,(8*i)+4);
            t2 = new RatNum(1,(8*i)+5);
            t3 = new RatNum(1,(8*i)+6);

            factor0 = new RatNum(1,16);  // blir fel här
            factor0 = factor0.pow(i);

            // get the difference
            factor1 = t0.sub(t1);
            factor1 = factor1.sub(t2);
            factor1 = factor1.sub(t3);

            // mult factors
            factor0 = factor0.mul(factor1);

            res = res.add(factor0);
        }

        System.out.print("pi = ");
        System.out.print(res.toDotString(decimalCount));
        System.out.println("...");

    }

}
