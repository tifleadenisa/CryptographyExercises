package rsa;

import Utils.RSAUtils;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    //modulus
    private BigInteger n;
    //encryption exponent
    private BigInteger e;
    //encryption exponent
    private BigInteger d;
    private BigInteger message;
    private BigInteger ciphertext;
    private BigInteger decryptedMessageRSA;
    private BigInteger decryptedMessageCRT;

    public RSA(){
        keyGenerator();
        messageGenerator();
        ciphertext = encryption();
        long t1 = System.currentTimeMillis();
        decryptedMessageRSA = decryptionRSA();
        long t2 = System.currentTimeMillis();
        long t3 = System.currentTimeMillis();
        decryptedMessageCRT = decryptionCRT();
        long t4 = System.currentTimeMillis();
        System.out.println("p is:");
        System.out.println(p);
        System.out.println("q is:");
        System.out.println(q);
        System.out.println("n is:");
        System.out.println(n);
        System.out.println("e is:");
        System.out.println(e);
        System.out.println("d is:");
        System.out.println(d);
        System.out.println("The message is:");
        System.out.println(message);
        System.out.println("The ciphertext is:");
        System.out.println(ciphertext);
        System.out.println("The decrypted message with RSA is:");
        System.out.println(decryptedMessageRSA);
        if(message.equals(decryptedMessageRSA)){
            System.out.println("CRIPTARE REUSITA CU RSA");
        }else{
            System.out.println("CRIPTARE NEREUSITA CU RSA");
        }
        System.out.println("Durata decriptarii cu RSA este: " + (t2-t1) + " ms");
        System.out.println("The decrypted message with CRT is:");
        System.out.println(decryptedMessageCRT);
        if(message.equals(decryptedMessageCRT)){
            System.out.println("CRIPTARE REUSITA CU CRT");
        }else{
            System.out.println("CRIPTARE NEREUSITA CU CRT");
        }
        System.out.println("Durata decriptarii cu CRT este: " + (t4-t3) + " ms");
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    private void keyGenerator(){
        p = BigInteger.probablePrime(512, new Random());
        q = BigInteger.probablePrime(512, new Random());
        while(p.equals(q)){
            p = BigInteger.probablePrime(512, new Random());
            q = BigInteger.probablePrime(512, new Random());
        }

        n = p.multiply(q);

        BigInteger phi = (p.subtract(BigInteger.valueOf(1))).multiply((q.subtract(BigInteger.valueOf(1))));

        BigInteger result;
        do{
            e = BigInteger.probablePrime(32, new Random());
            while(e.compareTo(phi) >= 0){
                e = BigInteger.probablePrime(32, new Random());
            }
            result = e.gcd(phi);
        }while (!result.equals(BigInteger.ONE) && (e.compareTo(phi) >= 0));

        System.out.println("phi is:");
        System.out.println(phi);

        d = new RSAUtils().modInverse(e, phi);
    }

    private void messageGenerator(){
        BigInteger maxLimit = n.subtract(BigInteger.ONE);
        BigInteger minLimit = BigInteger.ZERO;
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        message = new BigInteger(len, randNum);
        if (message.compareTo(minLimit) < 0)
            message = message.add(minLimit);
        if (message.compareTo(bigInteger) >= 0)
            message = message.mod(bigInteger).add(minLimit);
    }

    public BigInteger encryption(){
        return new RSAUtils().power(message, e, n);
    }

    public BigInteger decryptionRSA() {
        return new RSAUtils().mod2PrimeExp(ciphertext, d, p, q);
    }

    public BigInteger decryptionCRT(){
        return new RSAUtils().power(ciphertext, d, n);
    }

}
