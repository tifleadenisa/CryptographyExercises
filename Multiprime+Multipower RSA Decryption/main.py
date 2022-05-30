import math
import time

import Crypto.Util.number


def generate_numbers_multiprime():
    p = Crypto.Util.number.getPrime(1024)
    q = Crypto.Util.number.getPrime(1024)
    r = Crypto.Util.number.getPrime(1024)
    while p == q or p == r or q == r:
        p = Crypto.Util.number.getPrime(1024)
        q = Crypto.Util.number.getPrime(1024)
        r = Crypto.Util.number.getPrime(1024)
    n = p * q * r
    phi_n = (p - 1) * (q - 1) * (r - 1)
    e = Crypto.Util.number.getRandomNBitInteger(16)
    while math.gcd(e, phi_n) != 1 or e >= phi_n:
        e = Crypto.Util.number.getRandomNBitInteger(16)
    d = pow(e, -1, phi_n)
    return p, q, r, n, phi_n, e, d


def generate_numbers_multipower():
    p = Crypto.Util.number.getPrime(1024)
    q = Crypto.Util.number.getPrime(1024)
    while p == q:
        p = Crypto.Util.number.getPrime(1024)
        q = Crypto.Util.number.getPrime(1024)
    n = p * p * q
    phi_n = (p * p - p) * (q - 1)
    e = Crypto.Util.number.getRandomNBitInteger(16)
    while math.gcd(e, phi_n) != 1 or e >= phi_n:
        e = Crypto.Util.number.getRandomNBitInteger(16)
    d = pow(e, -1, phi_n)
    return p, q, n, phi_n, e, d


def decryption_multiprime(y, d, p, q, r):
    start = time.time()

    xp = pow(y % p, d % (p-1), p)
    xq = pow(y % q, d % (q - 1), q)
    xr = pow(y % r, d % (r - 1), r)

    '''
    urmeaza sa aplicam TCR Garner pe urmatorul sistem:
        x = xp mod p
        x = xq mod q
        x = xr mod r
    '''

    x1 = xp

    '''
        x2 = x1 + alpha * p;
        x2 = xq mod q 

        x1 + alpha * p = xq mod q 
            <=> 
        alpha * p = (xq - x1) mod q
            <=>
        alpha = (xq - x1) * p^(-1) mod q
    '''

    alpha1 = (((xq - x1) % q) * pow(p, -1, q)) % q
    x2 = x1 + alpha1 * p

    '''
            x3 = x2 + alpha * p * q;
            x3 = xr mod r 

            x2 + alpha * p * q = xr mod r 
                <=> 
            alpha * p * q = (xr - x2) mod r
            alpha = (xr - x2) * p^(-1) * q(-1) mod r
        '''
    alpha2 = (((xr - x2) % r) * pow(p, -1, r) * pow(q, -1, r)) % r
    x3 = x2 + alpha2 * p * q

    end = time.time()
    return x3, end-start


def multiprime_rsa():
    p, q, r, n, phi_n, e, d = generate_numbers_multiprime()
    plaintext = Crypto.Util.number.getPrime(1024) % n
    ciphertext = pow(plaintext, e, n)
    decrypted_text, t1 = decryption_multiprime(ciphertext, d, p, q, r)

    start = time.time()
    decrypted_text_library = pow(ciphertext, d, n)
    t2 = time.time() - start

    print("plaintext", plaintext)
    print("ciphertext", ciphertext)
    print("decrypted text", decrypted_text)
    print(t1)
    print(t2)


def decryption_multipower(y, d, e, p, q):
    start = time.time()

    xq = pow(y % q, d % (q-1), q)
    x0 = pow(y % p, d % (p-1), p)

    # in urma aplicarii lemmei lui Hensen obtinem urmatoarele valori
    alpha = (y - pow(x0, e, p * p)) // p
    x1 = (alpha * pow(((e * pow(x0, e-1, p*p)) % p), -1, p)) % p
    xp2 = x1 * p + x0

    '''
        urmeaza sa aplicam TCR Garner pe urmatorul sistem:
            x = xp2 mod p^2
            x = xq mod q
    '''
    x = xp2

    '''
            x2 = x + alpha * p^2;
            x2 = xq mod q 

            x + alpha * p^2 = xq mod q 
                <=> 
            alpha * p^2 = (xq - x) mod q
                <=>
            alpha = (xq - x) * (p^2)^(-1) mod q
        '''

    alpha = (((xq - x) % q) * pow(p, -2, q)) % q
    x2 = x + (alpha * p * p)

    end = time.time()

    return x2, end - start


def multipower_rsa():
    p, q, n, phi_n, e, d = generate_numbers_multipower()
    plaintext = Crypto.Util.number.getPrime(1024) % n
    ciphertext = pow(plaintext, e, n)
    decrypted_text, t1 = decryption_multipower(ciphertext, d, e, p, q)

    start = time.time()
    decrypted_text_library = pow(ciphertext, d, n)
    t2 = time.time() - start

    print("plaintext", plaintext)
    print("ciphertext", ciphertext)
    print("decrypted text", decrypted_text)
    print(t1)
    print(t2)


if __name__ == '__main__':
    multiprime_rsa()
    print("-------------")
    multipower_rsa()
