#include <iostream>
#include "fileManipulation.h"
#include "decryption.h"
using namespace std;

int main() {
	fileManipulation crypto("dracula.txt", "CRy PTO");
	crypto.transformKey();
	crypto.calculatePlaintext();
	crypto.transformIntoCryptotext();
	crypto.writeIntoFile();

	decryption decr("cryptotext.txt");
	decr.calculateCryptotext();
	cout << decr.ic(6);
	//cout << "Lungimea cheii este: " << decr.keyLength();
	return 0;
}