#pragma once
#include <string>
using namespace std;

class decryption
{
private:
	string key;
	string fileName;
	string plaintext;
	string cryptotext;
	double frequency[26] = { 0.0 };

	//calculeaza textul obtinut din y extragand simboluri din m in m pozitii incepand cu pozitia j
	//de asemenea, actualizeaza vectorul frequency cu frecventa literelor din textul respectiv
	//si returneaza lungimea textului
	int symbols(int m, int j);

	
	

public:
	
	decryption(string fileName);
	void setFileName(string fileName);
	string getFileName();
	void calculateCryptotext();

	//calculeaza IC pentru o cheie de lungime m si returneaza daca toate IC sunt aprox 0.065
	bool ic(int m);

	int keyLength();
};

