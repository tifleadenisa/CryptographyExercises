package generators;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {

        long t1 = System.currentTimeMillis();
        BBS b1 = new BBS();
        b1.generator();
        long t2 = System.currentTimeMillis();
        System.out.println("Durata rularii este: " + (t2-t1) + " ms");

        long t3 = System.currentTimeMillis();
        Jacobi j1 = new Jacobi();
        j1.generator();
        long t4 = System.currentTimeMillis();
        System.out.println("Durata rularii este: " + (t4-t3) + " ms");
    }
}
