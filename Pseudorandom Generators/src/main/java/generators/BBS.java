package generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

public class BBS {
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;

    public BBS() {
        p = generateNumber();
        q = generateNumber();
        N = p.multiply(q);
    }

    private BigInteger generateNumber() {

        BigInteger b;

        do {
            b = BigInteger.probablePrime(1024, new Random());
        }while( ! b.mod(new BigInteger("4")).equals(new BigInteger("3")) );

        return b;
    }

    private BigInteger generateNext( BigInteger x ) {
        return (x.multiply(x)).mod(N);
    }

    private void createFile() {

        try {
            File myObj = new File("randomBBS.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void writeToFile(String s){
        try
        {
            String filename= "randomBBS.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(s);//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void generator() {

        BigInteger max = new BigInteger("2").pow(20);
        BigInteger x = BigInteger.valueOf(System.currentTimeMillis());

        String numbers = "";
        numbers = numbers.concat((x.mod( new BigInteger("2"))).toString(10));

        for (int i = 1; BigInteger.valueOf(i).compareTo(max) < 0; i++) {

            //System.out.println(i);
            x = generateNext(x);
            numbers = numbers.concat((x.mod( new BigInteger("2"))).toString(10));

        }

        createFile();
        writeToFile(numbers);
    }

}
