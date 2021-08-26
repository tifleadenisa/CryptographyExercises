#include "decryption.h"
#include <iostream>
#include <fstream>


int decryption::symbols(int m, int j)
{
	int tLength = 0;
	for (int i = 0; i < 26; i++) {
		frequency[i] = 0.0;
	}
	for (int i = j; i < cryptotext.length() - m; i += m) {
		tLength++;
		frequency[int(cryptotext[i]) - 65]++;
	}
	return tLength;
}

bool decryption::ic(int m)
{
	double sum = 0.0;
	double element = 0.0;
	int length;
	bool OK = 1;
	for (int k = 0; k < m; k++) {
		length = symbols(m, k);
		for (int i = 0; i < 26; i++) {
			element = ((frequency[i] / length) * ((frequency[i] - 1.0) / (length - 1)));
			sum += element;
		}
		cout << "Indicele pentru cheia de lungime " << m << " incepand cu pozitia " << k << " este:" << sum << "\n";
		if (sum < 0.06 || sum > 0.07) {
			OK = false;
		}
			
	}
	return OK;
}

int decryption::keyLength()
{
	int m = 1;
	do {
		m++;
	} while (ic(m)==false && m < 26);
	if (m == 26)
		return -1;
	else
		return m;
}

decryption::decryption(string fileName)
{
	this->fileName = fileName;
}

void decryption::setFileName(string fileName)
{
	this->fileName = fileName;
}

string decryption::getFileName()
{
	return fileName;
}

void decryption::calculateCryptotext()
{
	char ch;
	ifstream fin (fileName);
	while (fin >> ch) {
		if (ch >= 'A' && ch <= 'Z') {
			cryptotext += ch;
		}
		else {
			if (ch >= 'a' && ch <= 'z') {
				cryptotext += toupper(ch);
			}
		}
	}
	fin.close();
}
