import random
from copy import deepcopy
import time
import numpy as np
import sympy
from sympy import poly
from sympy.abc import x


def generate_p():
    return sympy.randprime(2 ** 162, 2 ** 165)


def to_binary(a):
    m = ""
    for i in a:
        m += ((bin(ord(i))[2:]).zfill(8))
    return m


def compute_m(message, p):
    chunks = [message[i:i + 20] for i in range(0, len(message), 20)]
    numbers = []
    poly = []
    for chunk in chunks:
        numbers.append(to_binary(chunk))
    for number in numbers:
        poly.append(int(number, 2) % p)
    return poly


def compute_poly_mod_p(poly, x, p):
    result = 0
    for elem in poly:
        result += elem
        result %= p
        result *= x
        result %= p
    return result


def encode_message(message, p):
    m = compute_m(message, p)
    print("Message converted in integers: ", m)
    k = len(m) + 1
    n = k + 2
    y = []
    for i in range(1, n + 1):
        y.append(compute_poly_mod_p(m, i, p))
    return k, n, y


def alter_message(message, p):
    i = random.randint(0, len(message) - 1)
    elem = random.randint(0, p - 1)
    message[i] = elem
    return message


def mod_neg(a, p):
    return p - a


def mod_exp(a, b, p):
    return pow(a, b, p)


def mod_inv(a, p):
    return mod_exp(a, p - 2, p)


def compute_fc_slow(a, z, p):
    start = time.time()
    fc = 0
    for i in a:
        b = deepcopy(a)
        b.remove(i)
        mul = 1
        for j in b:
            mul *= j * (mod_inv(mod_neg(j - i, p), p) % p)
            mul %= p
        mul *= z[i - 1]
        mul %= p
        fc += mul
        fc %= p
    return fc, time.time() - start


def compute_fc_medium(a, z, p):
    start = time.time()
    fc = 0
    for i in a:
        b = deepcopy(a)
        b.remove(i)
        mul = 1
        numerator = 1
        denominator = 1
        for j in b:
            numerator *= j % p
            numerator %= p
            denominator *= (mod_neg((j - i), p) % p)
            denominator %= p
        mul *= (numerator * mod_inv(denominator, p) % p)
        mul *= z[i - 1]
        mul %= p
        fc += mul
        fc %= p
    return fc, time.time() - start


def compute_fc_fast(a, z, p):
    # 1/2 + 5/7 + 1/3 //42
    start = time.time()
    fc = 0
    for i in a:
        b = deepcopy(a)
        b.remove(i)
        mul = 1
        numerator = 1
        denominator = 1
        for j in b:
            numerator *= j % p
            numerator %= p
            denominator *= (mod_neg((j - i), p) % p)
            denominator %= p
        mul *= (numerator * mod_inv(denominator, p) % p)
        mul *= z[i - 1]
        mul %= p
        fc += mul
        fc %= p
    return fc, time.time() - start


def generate_a(z, n, k, p):
    a = random.sample(range(1, n + 1), k)
    t_slow = 0.0
    t_medium = 0.0
    t_fast = 0.0
    fc_slow, aux = compute_fc_slow(a, z, p)
    t_slow += aux
    fc_medium, aux = compute_fc_medium(a, z, p)
    t_medium += aux
    fc_fast, aux = compute_fc_fast(a, z, p)
    t_fast += aux
    # print(fc_slow, fc_medium, fc_fast)
    print()
    print("Free coefficients:")
    print(fc_slow)
    while fc_slow != 0 and fc_medium != 0 and fc_fast != 0:
        a = random.sample(range(1, n + 1), k)
        fc_slow, aux = compute_fc_slow(a, z, p)
        t_slow += aux
        fc_medium, aux = compute_fc_medium(a, z, p)
        t_medium += aux
        fc_fast, aux = compute_fc_fast(a, z, p)
        t_fast += aux
        print(fc_slow)
    print()
    print("Time for computing the free coefficient with  k(k-1) inverses: ", t_slow)
    print("Time for computing the free coefficient with  k inverses: ", t_medium)
    print("Time for computing the free coefficient with  1 inverse: ", t_fast)
    print()
    return a


def decode_message(a, z, p):
    polynomial = 0
    for i in a:
        b = deepcopy(a)
        b.remove(i)
        elem = z[i - 1]
        for j in b:
            elem *= (x - j)
            elem *= mod_inv(mod_neg(i - j, p), p)
        polynomial += elem
    polynomial = poly(polynomial)
    coefs = polynomial.coeffs()
    m = []
    for i in coefs:
        m.append(mod_neg(i, p) % p)
    m.pop(len(m) - 1)
    return m


def text_from_bits(bits, encoding='utf-8', errors='surrogatepass'):
    n = int(bits, 2)
    return n.to_bytes((n.bit_length() + 7) // 8, 'big').decode(encoding, errors) or '\0'


def numbers_to_txt(m):
    chunks = []
    message = ""
    for number in m:
        chunks.append(bin(number))
    for chunk in chunks:
        message += text_from_bits(chunk)
    return message


if __name__ == '__main__':
    p = generate_p()

    # message = "This is phrase from a satellite. Watch out for errors!"
    message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc suscipit arcu quis magna vehicula " \
              "eleifend. Aliquam id nulla vitae purus viverra ornare ac vitae libero. Etiam vitae magna molestie, " \
              "iaculis odio sit amet, consequat dolor. Nullam nec finibus tortor, eget posuere dui. Cras pretium vel " \
              "quam vitae suscipit. Suspendisse faucibus rhoncus eros, et aliquam quam tincidunt non. Cras nec " \
              "viverra ex, eu rhoncus justo. Suspendisse vestibulum sapien non vestibulum vehicula. Orci varius " \
              "natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec ut nisl nisi. Nunc " \
              "ultricies sollicitudin odio, ut suscipit ante egestas volutpat. Nam tincidunt vitae ipsum sit amet " \
              "bibendum. Nunc imperdiet rhoncus ornare. Maecenas ultricies ligula ex, eget tristique magna " \
              "condimentum in. Etiam iaculis lacus et justo laoreet, ac aliquam libero auctor. Cras vehicula aliquam " \
              "molestie. "

    k, n, y = encode_message(message, p)

    # p = 11
    # k = 3
    # n = 5
    # y = [9, 0, 6, 5, 8]

    print("p =", p)
    print("k =", k)
    print("n =", n)
    print("y =", y)
    print()
    z = alter_message(deepcopy(y), p)
    print("altered y =", z)
    a = generate_a(z, n, k, p)
    print("Computed a: ", a)
    print()
    m = decode_message(a, z, p)
    print(numbers_to_txt(m))
