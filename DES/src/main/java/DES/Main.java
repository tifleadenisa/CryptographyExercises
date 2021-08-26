package DES;

public class Main {

    public static void main(String[] args){

        DataEncryptionStandard des = new DataEncryptionStandard("0123456789ABCDEF", "133457799BBCDFF1");

        des.encryption();
        des.decryption();

    }
}
