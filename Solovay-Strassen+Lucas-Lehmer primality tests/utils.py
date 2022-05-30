# from Crypto.Cipher import AES
# from Crypto.PublicKey import RSA
# from Crypto.Random import get_random_bytes
#from Crypto.Util.Padding import pad, unpad
# from hashlib import sha256
from Crypto import Util.
import Crypto


def generate_parameters():
    p = Crypto.Util.number
    q = Crypto.Util.number.getPrime(512)
    r = Crypto.Util.number.getPrime(512)


def multiprime_rsa():
    pass


if __name__ == '__main__':
    generate_parameters()
