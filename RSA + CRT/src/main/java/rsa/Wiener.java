package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Wiener {
    private BigInteger n;
    private BigInteger e;
    private List<BigInteger> q = new ArrayList<>();
    private List<BigInteger> alfa = new ArrayList<>();
    private List<BigInteger> beta = new ArrayList<>();

    public Wiener(BigInteger n, BigInteger e){
        this.n = n;
        this.e = e;
        constructQ(e, n);
        constructAlfaAndBeta();
    }

    private void constructQ(BigInteger a, BigInteger b){
        BigInteger modulo = BigInteger.valueOf(-1);
        BigInteger divide;
        while(!modulo.equals(BigInteger.ZERO)){
            divide = a.divide(b);
            modulo = a.mod(b);
            q.add(divide);
            a = b;
            b = modulo;
        }
    }

    private void constructAlfaAndBeta(){
        alfa.add(q.get(0));
        alfa.add(q.get(0).multiply(q.get(1)).add(BigInteger.ONE));
        beta.add(BigInteger.ONE);
        beta.add(q.get(1));
        BigInteger qi;
        BigInteger alfa_i_1;
        BigInteger alfa_i_2;
        BigInteger beta_i_1;
        BigInteger beta_i_2;
        while(alfa.size()<q.size()){
            alfa_i_1 = alfa.get(alfa.size()-1);
            alfa_i_2 = alfa.get(alfa.size()-2);
            beta_i_1 = beta.get(beta.size()-1);
            beta_i_2 = beta.get(beta.size()-2);
            alfa.add(q.get(alfa.size()).multiply(alfa_i_1).add(alfa_i_2));
            beta.add(q.get(beta.size()).multiply(beta_i_1).add(beta_i_2));
        }
    }

    private boolean criteria(BigInteger l, BigInteger d){
        return false;
    }
}
