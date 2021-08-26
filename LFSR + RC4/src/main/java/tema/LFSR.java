package tema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;

public class LFSR {
    BitSet state;

    public LFSR(){
        init();
        generate();
    }

    private void init(){
        state = new BitSet(32);
        Random rand = new Random();
        int aux;
        int  counter= 0;
        while(counter == 0){
            counter = 0;
            for (int i = 0; i < 32; i++) {
                aux = rand.nextInt(2);
                if(aux == 1){
                    state.set(i);
                    counter++;
                }
            }
        }
        SystemOut(state);
    }

    private void createFile() {

        try {
            File myObj = new File("LFSR.txt");
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
            String filename= "LFSR.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(s);//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public String bitSetToString (BitSet bitSet){
        StringBuilder s = new StringBuilder();
        int length = (int) Math.pow(2,20);
        for( int i = 0; i < length;  i++ )
        {
            s.append( bitSet.get( i ) == true ? 1: 0 );
        }
        return String.valueOf(s);
    }

    public void SystemOut (BitSet bitSet){
        StringBuilder s = new StringBuilder();
        for( int i = 0; i < 32;  i++ )
        {
            s.append( bitSet.get( i ) == true ? 1: 0 );
        }
        System.out.println(s);
    }

    private void generate(){
        BitSet aux = new BitSet(4);
        int length = (int) Math.pow(2,20);
        BitSet response = new BitSet(length);
        for (int i = 0; i < length; i++) {
            response.set(i,state.get(0));
            aux.set(0,state.get(31));
            aux.set(1,state.get(27));
            aux.set(2,state.get(26));
            aux.set(3, (((response.get(i)^aux.get(0))^aux.get(1))^aux.get(2)) );
            state = state.get(1,32);
            state.set(31, (aux.get(3)));
        }
        createFile();
        writeToFile(bitSetToString(response));
    }
}
