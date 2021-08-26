package tema;

import java.util.Random;

public class RC4 {
    private char[] S;
    private char[] K;

    public RC4(){
        test();
    }

    //generate a random key
    private char[] preInit(){
        Random rand = new Random();
        int keyLength = rand.nextInt(17);
        while(keyLength <5){
            keyLength = rand.nextInt(17);
        }
        char[] key = new char[keyLength];
        int aux;
        for (int i = 0; i < keyLength; i++) {
            aux = rand.nextInt(256);
            key[i] = (char)aux;
        }
        return key;
    }

    private void init(){
        S = new char[256];
        char[] key = preInit();
        for (int i = 0; i < 256; i++) {
            S[i] = (char) i;
        }
        int j = 0;
        char tmp;
        for (int i = 0; i < 256; i++) {
            j = (j+ S[i] + key[i% key.length] ) % 256;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
        }
    }

    private void transition(){
        int i = 0;
        int j = 0;
        char tmp;
        K = new char[256];
        for (int counter = 0; counter < 256; counter++) {
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
            K[counter] = S[(S[i] + S[j]) % 256];
        }
    }


    public void test(){
        double nrTests = 10000000.0;
        int[] counter = new int[256];
        double normalProbability = 1.0/256;
        System.out.println("The normal probability is: " + normalProbability);
        double expectedProbability = 1.0/128;
        System.out.println("The expected probability is: " + expectedProbability);
        int aux;
        for (int i = 0; i < nrTests; i++) {
            init();
            transition();
            for (int j = 0; j < S.length; j++) {
                aux = K[j];
                if(aux == 0){
                    counter[j]++;
                }
            }
        }
        for (int i = 0; i < 256; i++) {
            System.out.println("P" + i + ": " + counter[i]/nrTests);
        }
    }


}
