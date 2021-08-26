package generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Jacobi {
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;

    public Jacobi() {
        p = generateNumber();
        q = generateNumber();
        N = p.multiply(q);
        System.out.println("N-ul este: " + N);
    }

    private BigInteger generateNumber() {

        BigInteger b;

        do {
            b = BigInteger.probablePrime(1024, new Random());
        }while( ! b.mod(new BigInteger("4")).equals(new BigInteger("3")) );

        return b;
    }

    private void createFile() {

        try {
            File myObj = new File("randomJacobi.txt");
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
            String filename= "randomJacobi.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(s);//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }


    private BigInteger jacobiSymbol(BigInteger a) {
        BigInteger b = a.mod(N);
        BigInteger c = N;
        BigInteger s = BigInteger.valueOf(1);

        //b>2 && b == 2
        while(b.compareTo(BigInteger.valueOf(2)) != -1) {
            while(b.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(0))) {
                b = b.divide(BigInteger.valueOf(4));
            }
            if( b.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0)) ) {
                if( c.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(3))
                        || c.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5)))   {
                    s = s.multiply(BigInteger.valueOf(-1));
                }
                b = b.divide(BigInteger.valueOf(2));
            }
            if( b.equals(BigInteger.valueOf(1)) ) {
                break;
            }
            if( b.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))
                    && c.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                s = s.multiply(BigInteger.valueOf(-1));
            }
            BigInteger aux = b;
            b = c.mod(b);
            c = aux;
        }
        return s.multiply(b);
    }

    private String getDigit (BigInteger x) {
        if(jacobiSymbol(x).equals(BigInteger.valueOf(1)) ) {
            return "1";
        }
        else {
            return "0";
        }
    }

    public void generator() {

        BigInteger max = new BigInteger("2").pow(20);
        BigInteger a = (BigInteger.valueOf(System.currentTimeMillis())).mod(N);

        String numbers = "";
        numbers = numbers.concat(getDigit(a.mod(N)));

        for (int i = 1; BigInteger.valueOf(i).compareTo(max) < 0; i++) {

            //System.out.println(i);
            numbers = numbers.concat( getDigit((a.add(BigInteger.valueOf(i))).mod(N)) );

        }

        createFile();
        writeToFile(numbers);
    }



}
