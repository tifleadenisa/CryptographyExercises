package tema;

public class Main {
    public static void main(String[] args){
        long t1 = System.currentTimeMillis();
        LFSR lfsr = new LFSR();
        long t2 = System.currentTimeMillis();
        System.out.println("Durata rularii este: " + (t2-t1) + " ms");

        RC4 rc4 = new RC4();
    }
}
