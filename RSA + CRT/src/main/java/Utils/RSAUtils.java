package Utils;

import java.math.BigInteger;

public class RSAUtils {

    //modular inverse using extended Euclid algorithm
    public BigInteger modInverse(BigInteger a, BigInteger m)
    {
        BigInteger m0 = m;
        BigInteger y = BigInteger.ZERO;
        BigInteger x = BigInteger.ONE;
        if (m.equals(BigInteger.ONE))
            return BigInteger.ZERO;
        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger q = a.divide(m);
            BigInteger t = m;
            m = a.mod(m);
            a = t;
            t = y;
            y = x.subtract(q.multiply(y));
            x = t;
        }
        if (x.compareTo(BigInteger.ZERO) < 0){
            x = x.add(m0);
        }

        return x;
    }

    public BigInteger power(BigInteger x, BigInteger y, BigInteger p)
    {
        BigInteger res = BigInteger.ONE;

        x = x.mod(p);

        if (x.equals(BigInteger.ZERO))
            return BigInteger.ZERO;

        while (y.compareTo(BigInteger.ZERO) > 0)
        {
            if (y.mod(BigInteger.TWO).equals(BigInteger.ONE)){
                res = res.multiply(x).mod(p);
            }
            y = y.divide(BigInteger.TWO);
            x = x.multiply(x).mod(p);
        }
        return res;
    }

    public BigInteger mod2PrimeExp(BigInteger a, BigInteger n,BigInteger m1, BigInteger m2){

            BigInteger n1 = n.mod(m1.subtract(BigInteger.ONE));
            BigInteger n2 = n.mod(m2.subtract(BigInteger.ONE));
            BigInteger inverse = modInverse(m1, m2);

            BigInteger x1 = power(a.mod(m1), n1, m1);
            BigInteger x2 = power(a.mod(m2), n2, m2);
            BigInteger x = x1.add(m1.multiply((x2.subtract(x1)).multiply(inverse).mod(m2)));
            return x;

    }
}
