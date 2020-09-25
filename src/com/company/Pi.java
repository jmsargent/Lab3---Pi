package com.company;
import java.lang.Math;
import java.math.BigInteger;

public class Pi {

    public static void main(String[] args) {
        int decimalCount = 5;
        if (args.length > 0) {
            decimalCount = Integer.valueOf(args[0]);
        }



        RatNum res3 = new RatNum(0,1);
        // ersätt följande två rader med din lösning
        for (int i = 0; i < decimalCount; i++) {
            //res2 = res2.pow(res2,i);
            RatNum res = new RatNum(0, 1);
            RatNum res2 = new RatNum(1,16);
            res = res.add(new RatNum(4, 8*i+1));
            res = res.sub(new RatNum(2, 8*i+4));
            res = res.sub(new RatNum(1, 8*i+5));
            res = res.sub(new RatNum(1, 8*i+6));
            res = res.mul(res2.pow(i));

            res3 = res3.add(res);



        }





        System.out.print("pi = ");
        System.out.print(res3.toDotString(decimalCount));
        System.out.println("...");
    }

}

