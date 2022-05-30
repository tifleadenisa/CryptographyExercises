import random
from math import sqrt
import Crypto.Util.number
from sympy import isprime, jacobi_symbol
from sympy.ntheory import factorint


def generate_alpha(p):
    alpha = random.randint(2, p - 2)
    js = jacobi_symbol(alpha, p)
    while js != -1:
        alpha = random.randint(2, p - 2)
        js = jacobi_symbol(alpha, p)
    return alpha


def shanks():
    p = Crypto.Util.number.getPrime(31)
    p *= 2
    p += 1
    while not isprime(p):
        p = Crypto.Util.number.getPrime(31)
        p *= 2
        p += 1
    alpha = generate_alpha(p)
    beta = random.randint(1, p - 1)
    print("p = ", p)
    print("alpha = ", alpha)
    print("beta = ", beta)
    m = int(sqrt(p-1)) + 1

    # first step, computing for j
    j_comp = []
    for j in range(m):
        j_comp.append((j, pow(alpha, j, p)))
    j_comp.sort(key=lambda a: a[1])

    # second step, computing for i
    i = 0
    while i < m:
        elem = (beta * pow(alpha, -m * i, p)) % p
        j = next((x for x, y in j_comp if y == elem), None)
        if j is not None:
            print("i and j:")
            print(i, j)
            print("epsilon is: ", i * m + j)
            i = m
        i += 1


# def generate_p_sph():
#     factors = []
#     p_1 = 1
#     for i in range(7):
#         no_of_bits = random.randint(2, 6)
#         prime = Crypto.Util.number.getPrime(no_of_bits)
#         if next((x for x, y in factors if x == prime), None) is None:
#             factors.append((prime, 1))
#     while len(bin(p_1)[2:]) < 1024:
#         p_1 = 1
#         for elem in range(len(factors)):
#             p_1 *= pow(factors[elem][0], factors[elem][1])
#             factors[elem] = (factors[elem][0], factors[elem][1]+1)
#     print(len(bin(p_1)[2:]))
#     print(p_1)
#     print(isprime(p_1 + 1))

# def generate_p_sph():
#     p = Crypto.Util.number.getPrime(1024)
#     print(p)
#     print(factorint(p-1))


if __name__ == '__main__':
    shanks()
    # generate_p_sph()
