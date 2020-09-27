package com.company;
import java.io.*;
import java.util.*;

 class RatNumTest3 {

    public static String ratNumToString(RatNum r) {
        int num = r.getNumerator();
        int denom = r.getDenominator();
        if (num == 0) {
            return "0";
        }
        String s = "";
        int whole = num / denom;
        int fraction = num % denom;
        if (whole != 0) {
            s = whole + " ";
            fraction = Math.abs(fraction);
        }
        if (fraction != 0)
            s =  s + fraction + "/" + denom;
        return s;
    }

    private static void divTester() {
        // Testar equals och clone
        RatNum x = new RatNum(6,2);
        RatNum y = new RatNum(0);
        RatNum z = new RatNum(0,1);
        RatNum w = new RatNum(75,25);
        Object v = new RatNum(75,25);
        String str = new String("TEST");

        System.out.println();
        System.out.println(">>>> Test av equals: Vi har inte gått igenom equals ännu ");
        System.out.println("så du behöver inte klara dessa tester än");
        System.out.println("equals test 1 ");
        if (x.equals(y) || !y.equals(z) || !x.equals(w)) {
            System.out.println("RatNumTest3: FEL 1 i equals!!");
        }
        System.out.println("equals test 2 ");
        if ( !w.equals(v)  ) { //  w skall vara lika med v
            // med equals(RatNum r) så väljs dock objects equals
            // eftersom parameterprofilen stämmer där och då blir dom olika
            System.out.println("RatNumTest3: FEL 2 i equals!!");
        }
        System.out.println("equals test 3 ");
        if ( !v.equals(w) ) { // dyn. bindningen ger RatNums equals
            // men med equals(RatNum r) så blir det som ovan
            System.out.println("RatNumTest3: FEL 3 i equals!!");
        }
        System.out.println("equals test 4 ");
        try {
            if ( w.equals(null)  ) { //skall inte vara lika
                System.out.println("RatNumTest3: FEL 4.1 i equals!!");
            }
        } catch (NullPointerException e) { // men skall klara null
            System.out.println("RatNumTest3: FEL 4.2 i equals!!");
        }
        System.out.println("equals test 5 ");
        if ( w.equals(str) ) { // skall ge false
            // med equals(RatNum r) får man återigen Objects equals
            // och den ger rätt svar här
            System.out.println("RatNumTest3: FEL 5 i equals!!");
        }
        System.out.println("<<<< Slut på equals tester");
        RatNum r = new RatNum(1);
        String expected = "";
        int k = 0;
        for (int i=0; i<7; i++) {
            if (i == 0) {
                expected = "0.500"; k = 3; r = new RatNum(1,2);
            } else if (i == 1) {
                expected = "-20.0"; k = 1; r = new RatNum(-20,1);
            } else if (i == 2) {
                expected = "0.666"; k = 3; r = new RatNum(2,3);
            } else if (i == 3) {
                expected = "0.0001"; k = 4; r = new RatNum(1,10000);
            } else if (i == 4) {
                expected = "-0.001"; k = 3; r = new RatNum(-11,10000);
            } else if (i == 5) {
                expected = "104.477"; k = 3; r = new RatNum(7000,67);
            } else if (i == 6) {
                expected = "0.89"; k = 2; r = new RatNum(89,99);
            }
            String output = r.toDotString(k);
            if (!output.equals(expected)) {
                System.out.println("RatNumTest3 FEL: toDotString(" + k + ") visar fel för " + r.toString() +
                                   "; gav: "+output+"; skulle vara: " + expected);
            }
        }
        /*
          try {
          y = (RatNum) x.clone();
          }
          catch (Exception ce) {}
          if (!y.equals(x) || y==x)
          System.out.println("RatNumTest3: FEL I clone!!");
        */
    } // end divTester

    public static String testExpr(String s) {
        // @require s.length > 0
        //String result = "";
        Scanner sc = new Scanner(s);
        String[] a = new String[3];
        int i;
        for(i=0; i<3 && sc.hasNext(); i++) {
            a[i] = sc.next();
        }
        if (i != 3 || sc.hasNext())
            return("Felaktigt uttryck!");
        else {
            try {
                RatNum r1 = RatNum.parse(a[0]);
                String op = a[1];
                char c = op.charAt(0);
                RatNum r2 = new RatNum(a[2]);
                if (op.length() != 1 || "+-*/=<".indexOf(c) < 0)
                    return("Felaktig operator!");
                else {
                    RatNum res = null;
                    if (c == '+')
                        res = r1.add(r2);
                    else if (c == '-')
                        res = r1.sub(r2);
                    else if (c == '*')
                        res = r1.mul(r2);
                    else  if (c == '/')
                        res = r1.div(r2);
                    else if (c == '=')
                        return( Boolean.toString(r1.equals(r2)) );
                    else if (c == '<')
                        return( Boolean.toString(r1.lessThan(r2)) );
                    if ("+-*/".indexOf(c) >= 0)
                        if (res == null)
                            return("Fel i add, sub, mul eller div");
                        else
                            return(res.toString());
                }
            }
            catch (NumberFormatException e) {
                return("NumberFormatException");
            }
        }
        return ("Okänt fel");
    } // end testExpr

    public static void main(String[] arg) throws IOException {
        RatNumTest2.divTester();
        divTester();
        StringBuilder stringToPrint;
        String correctAnswer;
        // om det finns argumet så är det testfilen => auto test
        boolean machine; // keep track of if testing is human or machine
        String filename = "";
        Scanner in;
        //System.exit(0); // debug
        if (arg.length > 0) {
            machine = true;
            filename = arg[0];
            in = new Scanner(new File(filename));
            System.out.println("automatic testing - reading " + filename);
            System.out.println("given expression\t given result");
        } else {
            machine = false;
            in = new Scanner(System.in);
            System.out.println("Skriv uttryck på formen a/b ? c/d, där ? är något av tecknen + - * / = <");
        }
        // read input
        while (true) {
            if (!machine) {System.out.print("> ");  System.out.flush();}
            if (!in.hasNext()) {System.out.println(); System.exit(0);} // no input left
            String s = in.nextLine();
            if ( s == null || s.length()==0 ) {
                break;
            }
            correctAnswer = "";
            stringToPrint = new StringBuilder();
            if (machine) {
                // split input in question - correct answer
                int i = s.indexOf("-->");
                if (i<1) {
                    System.out.println("##### Error - No answers found in file - cannot correct");
                    System.exit(0);
                }
                correctAnswer = s.substring(i+4);
                s = s.substring(0,i);
                //System.out.println("\t##s= " + s + "* correct answer=" + correctAnswer + " debug"); // debug
            }
            String givenAnswer = testExpr(s);
            stringToPrint.append(s + "\t--> " + givenAnswer );
            if (machine && !correctAnswer.equals(givenAnswer)) {
                //System.out.println( "====================================================" );
                //System.out.println("\t##*"+givenAnswer+"* " + "*"+correctAnswer+ "*" + ratNumToString(new RatNum(givenAnswer)) + "* debug"); // debug
                // test if answer given as a/B +
                // strängen till NumberFormatException kan vara olika
                if ( correctAnswer.equals(ratNumToString(new RatNum(givenAnswer)))
                     || ( correctAnswer.indexOf("NumberFormatException") != -1
                          && givenAnswer.indexOf("NumberFormatException") != -1 )
                     ) {
                    ; // do nothing
                } else {
                    stringToPrint.append(" ###### Error, correct answer is= " + correctAnswer);
                }
            }
            System.out.println(stringToPrint.toString());
        }
    }

}
class RatNumTest2 {

    private static void fel(int nr) {
        System.out.println("RatNumTest2: Fel nummer " + nr);
        System.exit(1);
    }

    public static void divTester() {
        RatNum r;

        // test av konstruktor
        r = new RatNum(9);
        if (r.getNumerator() != 9 || r.getDenominator() != 1)
            fel(1);
        r = new RatNum(4, 9);
        if (r.getNumerator() != 4 || r.getDenominator() != 9)
            fel(2);
        r = new RatNum(49, 168);
        if (r.getNumerator() != 7 || r.getDenominator() != 24)
            fel(3);
        RatNum r2 = new RatNum(r);
        if (r2.getNumerator() != 7 || r2.getDenominator() != 24)
            fel(4);
        RatNum x = new RatNum();
        if (x.getNumerator() != 0 || x.getDenominator() != 1)
            fel(5);
        if (r2.getNumerator() == 0 || r2.getDenominator() == 1)
            fel(6);
        RatNum y = new RatNum(5);
        if (y.getNumerator() != 5 || y.getDenominator() != 1)
            fel(7);
        RatNum z = new RatNum(20, 4);
        if (z.getNumerator() != 5 || z.getDenominator() != 1)
            fel(8);
        RatNum w = new RatNum(0,1);
        if (w.getNumerator() != 0 || w.getDenominator() != 1)
            fel(9);
        RatNum q = new RatNum(y);
        if (q.getNumerator() != 5 || q.getDenominator() != 1)
            fel(10);

        // test av negativa parametrar
        r = new RatNum(-49, 168);
        if (r.getNumerator() != -7 || r.getDenominator() != 24)
            fel(11);
        r = new RatNum(49, -168);
        if (r.getNumerator() != -7 || r.getDenominator() != 24)
            fel(12);
        r = new RatNum(-49, -168);
        if (r.getNumerator() != 7 || r.getDenominator() != 24)
            fel(13);

        // Test av exception
        boolean ok = false;
        try {
            q = new RatNum(5,0);
        }
        catch (NumberFormatException e1) {ok = true;}
        catch (Exception e2) {}
        if (!ok)
            fel(14);
    }

    public static void main(String[] arg) {
        divTester();
        System.out.println("Inga fel!");
    }
}

