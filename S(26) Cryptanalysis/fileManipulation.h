#pragma once
#include <string>
using namespace std;

class fileManipulation
{
private:
	string key;
	string fileName;
	string plaintext;
	string cryptotext;
	string alphabet = { "ABCDEFGHIJKLMNOPQRSTUVWXYZ" };

	char plusLetter(char a, char b);

public:

	fileManipulation(string fileName, string key);
	void setFileName(string fileName);
	string getFileName();
	string getKey();
	void setKey(string key);
	void setPlaintext(string plaintext);
	void transformKey();
	void calculatePlaintext();
	void transformIntoCryptotext();
	void writeIntoFile();
};

