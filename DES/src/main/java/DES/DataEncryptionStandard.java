package DES;


import java.util.ArrayList;
import java.util.List;

public class DataEncryptionStandard {
    private String plaintext;
    private String cryptotext;
    private String key;
    private List<String> encKeys;

    public DataEncryptionStandard(String plaintext, String key) {
        this.plaintext = plaintext;
        this.key = key;
    }

    private String hexToBin(String text){
        int size = text.length() * 4;
        text = Long.toBinaryString(Long.parseUnsignedLong(text, 16));
        while (text.length() < size) {
            text = "0" + text;
        }
        return text;
    }

    private String binToHex(String text) {
        int n = (int)text.length() / 4;
        text = Long.toHexString(
                Long.parseUnsignedLong(text, 2));
        while (text.length() < n)
            text = "0" + text;
        return text;
    }

    private String firstHalf(String text) {
        return text.substring(0, text.length() / 2);
    }

    private String secondHalf(String text) {
        return text.substring(text.length() / 2, text.length());
    }

    public String leftShift(String text)
    {
        return text.substring(1,text.length()) + text.charAt(0);
    }

    private String computeKey(String cString, String dString) {
        int[][] PC2= { { 14,    17,   11,    24,     1,    5 },
                {   3,    28,   15,     6,    21,   10},
                {  23,    19,   12,     4,    26,    8},
                {  16,     7,   27,    20,    13,    2},
                {  41,    52,   31,    37,    47,   55},
                {  30,    40,   51,    45,    33,   48},
                {  44,    49,   39,    56,    34,   53},
                {  46,    42,   50,    36,    29,   32}
        };
        cString += dString;
        String newKey = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                newKey += cString.charAt(PC2[i][j]-1);
            }
        }
        return newKey;

    }

    private String xor (char first, char second){
            if(first == second){
                return "0";
            }else{
                return "1";
            }
    }

    private String fFunction(String a, String j, int iteration) {

        int[][] S1 = { { 14,  4,  13,  1,   2, 15,  11,  8,   3, 10,   6, 12,   5,  9,   0,  7 },
            { 0,  15,   7,  4,  14,  2,  13,  1,  10,  6,  12, 11,   9,  5,   3,  8},
            { 4,  1,  14,  8,  13,  6,   2, 11,  15, 12,   9,  7,   3, 10,   5,  0},
            { 15, 12,   8,  2,   4,  9,   1,  7,   5, 11,   3, 14,  10,  0,   6, 13},
        };

        int[][] S2 = { { 15,  1,   8, 14,   6, 11,   3,  4,   9,  7,   2, 13,  12,  0,   5, 10 },
            {  3, 13,   4,  7,  15,  2,   8, 14,  12,  0,   1, 10,   6,  9,  11,  5},
            { 0, 14,   7, 11,  10,  4,  13,  1,   5,  8,  12,  6,   9,  3,  2, 15},
            { 13,  8,  10,  1,   3, 15,   4,  2,  11,  6,   7, 12,   0,  5,  14,  9}
        };

        int[][] S3 = { {10,  0,   9, 14,   6,  3 , 15 , 5  , 1, 13,  12,  7,  11,  4 ,  2,  8},
            {13,  7,   0,  9,   3,  4,   6, 10,   2,  8,   5, 14,  12, 11,  15,  1},
            {13,  6,   4,  9,   8, 15,   3,  0,  11,  1,   2, 12,   5, 10,  14,  7},
            {1, 10,  13,  0,   6,  9,   8,  7,   4, 15,  14 , 3 , 11,  5 ,  2, 12}
        };


        int[][] S4 = { {7, 13,  14,  3,   0 , 6,   9, 10,   1 , 2,   8, 5,  11, 12,   4 ,15},
            {13,  8,  11,  5 ,  6 ,15 ,  0 , 3 ,  4,  7,   2, 12 ,  1 ,10,  14 , 9},
            {10,  6,   9,  0,  12, 11,   7, 13,  15,  1,   3, 14,   5,  2,   8,  4},
            {3, 15 ,  0,  6,  10,  1,  13,  8 ,  9 , 4 ,  5, 11,  12 , 7 ,  2 ,14} };

        int[][] S5 = { {2, 12,   4 , 1,   7, 10 , 11,  6,   8 , 5,   3, 15,  13,  0,  14,  9},
            {14, 11,   2, 12,   4,  7,  13,  1 ,  5 , 0,  15, 10,   3,  9 ,  8 , 6},
            { 4,  2,   1, 11,  10, 13,   7,  8 , 15,  9,  12,  5,   6,  3,   0, 14},
            {11,  8,  12,  7,   1, 14,   2, 13,   6, 15,   0 , 9,  10 , 4  , 5 , 3} };

        int[][] S6 = { {12,  1,  10, 15,   9 , 2,   6,  8,   0, 13,   3,  4,  14,  7 ,  5, 11},
            {10, 15,   4,  2 ,  7 ,12 ,  9,  5,   6,  1,  13, 14,   0, 11 ,  3  ,8},
            { 9, 14,  15,  5,   2 , 8,  12,  3 ,  7 , 0 ,  4, 10  , 1, 13 , 11 , 6},
            { 4,  3,   2, 12,   9,  5,  15, 10,  11, 14 ,  1,  7,   6,  0,   8, 13}
        };

        int[][] S7 = { {4, 11 ,  2 ,14 , 15 , 0 ,  8 ,13 ,  3, 12 ,  9,  7 ,  5 ,10,   6,  1},
            {13,  0,  11 , 7  , 4 , 9 ,  1, 10 , 14 , 3 ,  5 ,12 ,  2 ,15 ,  8,  6},
            { 1,  4  ,11, 13,  12,  3,   7 ,14,  10 ,15,   6,  8  , 0,  5,   9,  2},
            { 6 ,11 , 13 , 8  , 1 , 4,  10 , 7,   9,  5 ,  0 ,15 , 14  ,2 ,  3, 12}
        };

        int[][] S8 = { { 13,  2,   8 , 4 ,  6 ,15 , 11 , 1  ,10 , 9 ,  3 ,14,   5 , 0 , 12 , 7},
            { 1 ,15,  13 , 8 , 10,  3 ,  7 , 4,  12,  5  , 6 ,11 ,  0 ,14 ,  9,  2},
            { 7, 11 ,  4,  1 ,  9, 12 , 14 , 2,   0,  6 , 10 ,13 , 15 , 3 ,  5 , 8},
            { 2,  1,  14,  7 ,  4, 10 ,  8 ,13,  15, 12 ,  9  ,0 ,  3 , 5 ,  6, 11}
        };

        List<int[][]> sBox = new ArrayList<>();
        sBox.add(S1);
        sBox.add(S2);
        sBox.add(S3);
        sBox.add(S4);
        sBox.add(S5);
        sBox.add(S6);
        sBox.add(S7);
        sBox.add(S8);


        int[][] E = { {    32,     1,    2,     3,     4,    5 },
            {   4,     5,    6,     7,     8,    9},
            {   8,    9,   10,   11,    12,   13},
            {  12,    13,   14,    15,    16,   17},
            {  16,    17,   18,    19,    20,   21},
            {  20,    21,   22,    23,    24,   25},
            {  24,    25,   26,    27,    28,   29},
            {  28,    29,   30,    31,    32,    1}
        };


        String expansion = "";
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 6; k++) {
                expansion += a.charAt(E[i][k]-1);
            }
        }

        String xor = "";
        for (int i = 0; i < 48; i++) {
            xor += xor(expansion.charAt(i), j.charAt(i));
        }

        //rezultatul final al functiei
        String finalResult ="";
        //byteString: every 6-bit String
        String byteString = "";
        String aux ="";
        int line, column;
        String result = "";
        for (int i = 0; i < 8; i++) {
            byteString = xor.substring(i*6, (i+1)*6);
            //line
            aux = "" + byteString.charAt(0)+byteString.charAt(byteString.length()-1);
            line = Integer.parseInt(aux,2);
            //column
            aux = "" + byteString.substring(1,byteString.length()-1);
            column = Integer.parseInt(aux, 2);

            result = Integer.toBinaryString(sBox.get(i)[line][column]);

            while(result.length()<4) {
                result = "0" + result;
            }

            finalResult += result;
        }

        int[][] P = { {    16,   7,  20,  21 },
            {   29,  12,  28,  17,},
            {  1,  15,  23,  26},
            {  5,  18,  31,  10},
            {  2,   8,  24,  14},
            {  32,  27,   3,   9},
            {  19,  13,  30,   6},
            {  22,  11,   4,  25}
        };

        String newFinalResult = "";
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 4; k++) {
                newFinalResult += finalResult.charAt(P[i][k]-1);
            }
        }

        return newFinalResult;
    }

    public void encryption(){

        //converting to binary
        plaintext = hexToBin(plaintext);
        key = hexToBin(key);

        /*System.out.println("plaintext before IP is:");
        System.out.println(plaintext);
        System.out.println("si lungimea plaintextului e" + plaintext.length() + "key before IP is:");
        System.out.println(key);
        System.out.println("si lungimea cheii este " + key.length());*/

        //initial permutation IP
        int[][] IP = { { 58,    50,   42,    34,    26,   18,    10,    2 },
                { 60,    52,   44,    36,    28,   20,    12,    4},
                { 62,    54,   46,    38,    30,   22,    14,    6},
                { 64,    56,   48,    40,    32,   24,    16,    8},
                { 57,    49,   41,    33,    25,   17,     9,    1},
                { 59,    51,   43,    35,    27,   19,    11,    3},
                { 61,    53,   45,    37,    29,   21,    13,    5},
                { 63,    55,   47,    39,    31,   23,    15,    7}
        };

        String newPlaintext = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newPlaintext += plaintext.charAt(IP[i][j]-1);
            }
        }
        plaintext = newPlaintext;

        /*System.out.println("plaintext after IP is:");
        System.out.println(plaintext);*/


        int[][]PC1 = { { 57,   49,    41,   33,    25,    17,    9 },
                {  1,   58,    50,   42,    34,    26,   18},
                {  10,    2,    59,   51,   43,    35,   27},
                { 19,   11,     3,   60,    52,    44,   36},
                { 63,   55,    47,   39,    31,    23,   15},
                {  7,   62,    54,   46,    38,    30,   22},
                {  14,   6,    61,   53,    45,    37,   29},
                {  21,   13,     5,   28,    20,    12,    4}
        };

        String newKey = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                newKey += key.charAt(PC1[i][j]-1);
            }
        }
        key = newKey;


        String left;
        String right;
        String cString;
        String dString;
        left = firstHalf(plaintext);
        right = secondHalf(plaintext);

        encKeys = new ArrayList<>();

        for (int i = 1; i <= 16; i++) {
            left = firstHalf(plaintext);
            right = secondHalf(plaintext);
            cString = firstHalf(key);
            dString = secondHalf(key);
            if(i==1 || i==2 || i==9 || i==16){
                cString=leftShift(cString);
                dString=leftShift(dString);
            }else{
                cString=leftShift(leftShift(cString));
                dString=leftShift(leftShift(dString));
            }
            key = cString + dString;

            plaintext = "";
            plaintext = right;
            encKeys.add(computeKey(cString, dString));
            String f = fFunction(right, computeKey(cString, dString), i);
            for (int j = 0; j < 32; j++) {
                    plaintext += xor(left.charAt(j), f.charAt(j));
            }

        }

        //luam ultimul plaintext, il punem R16L16
        left = firstHalf(plaintext);
        right = secondHalf(plaintext);
        plaintext = right + left;

        int[][] IPinvers = { { 40,   8,  48,    16,    56,   24,    64,   32 },
            { 39,     7,   47,    15,    55,   23,    63,   31},
            { 38,     6,   46,    14,    54,   22,    62,   30},
            { 37,     5,  45,    13,    53,   21,    61,   29},
            { 36,     4,   44,    12,    52,   20,    60,   28},
            { 35,     3,   43,    11,    51,   19,    59,   27},
            { 34,     2,   42,    10,    50,   18,    58,   26},
            { 33,     1,   41,     9,    49,   17,    57,   25}
        };

        cryptotext = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cryptotext += plaintext.charAt(IPinvers[i][j]-1);
            }
        }

        cryptotext = binToHex(cryptotext);
        System.out.println("Cryptotext is: " + cryptotext);

    }

    public void decryption(){

        int[][] IPinvers = { { 40,   8,  48,    16,    56,   24,    64,   32 },
                { 39,     7,   47,    15,    55,   23,    63,   31},
                { 38,     6,   46,    14,    54,   22,    62,   30},
                { 37,     5,  45,    13,    53,   21,    61,   29},
                { 36,     4,   44,    12,    52,   20,    60,   28},
                { 35,     3,   43,    11,    51,   19,    59,   27},
                { 34,     2,   42,    10,    50,   18,    58,   26},
                { 33,     1,   41,     9,    49,   17,    57,   25}
        };

        int[][] IP = { { 58,    50,   42,    34,    26,   18,    10,    2 },
                { 60,    52,   44,    36,    28,   20,    12,    4},
                { 62,    54,   46,    38,    30,   22,    14,    6},
                { 64,    56,   48,    40,    32,   24,    16,    8},
                { 57,    49,   41,    33,    25,   17,     9,    1},
                { 59,    51,   43,    35,    27,   19,    11,    3},
                { 61,    53,   45,    37,    29,   21,    13,    5},
                { 63,    55,   47,    39,    31,   23,    15,    7}
        };

        cryptotext = hexToBin(cryptotext);

        String newCryptotext = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newCryptotext += cryptotext.charAt(IP[i][j]-1);
            }
        }
        cryptotext = newCryptotext;



        String left;
        String right;
        String cString;
        String dString;

        for (int i = 1; i <= 16; i++) {
            //System.out.println(encKeys.get(i));
            left = firstHalf(cryptotext);
            right = secondHalf(cryptotext);

            cryptotext = "";
            cryptotext = right;
            String f = fFunction(right, encKeys.get(16-i), i);
            for (int j = 0; j < 32; j++) {
                cryptotext += xor(left.charAt(j), f.charAt(j));
            }

        }
        left = firstHalf(cryptotext);
        right = secondHalf(cryptotext);
        cryptotext = right + left;

        plaintext = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                plaintext += cryptotext.charAt(IPinvers[i][j]-1);
            }
        }

        plaintext = binToHex(plaintext);
        System.out.println("Plaintext is: " + plaintext);
    }
}
