#include "fileManipulation.h"
#include <iostream>
#include <fstream>

char fileManipulation::plusLetter(char a, char b)
{
	char ch = (int(a) + int(b)) - 130;
	if (int(ch) <= 25) {
		return ch + 65;
	}
	else {
		return ch - 26 + 65;
	}
}

fileManipulation::fileManipulation(string fileName, string key)
{
	this->fileName = fileName;
	this->key = key;
}

void fileManipulation::setFileName(string fileName)
{
	this->fileName = fileName;
}

string fileManipulation::getFileName()
{
	return fileName;
}


string fileManipulation::getKey()
{
	return key;
}

void fileManipulation::setKey(string key)
{
	this->key = key;
}

void fileManipulation::setPlaintext(string plaintext)
{
	this->plaintext = plaintext;
}

void fileManipulation::calculatePlaintext()
{
	char ch;
	ifstream fin(fileName);
	while (fin >> ch) {
		if (ch >= 'A' && ch <= 'Z') {
			plaintext += ch;
		}
		else {
			if (ch >= 'a' && ch <= 'z') {
				plaintext += toupper(ch);
			}
		}
	}
	fin.close();
}

void fileManipulation::transformIntoCryptotext()
{

	for (int i = 0; i < plaintext.length(); i++) {
		cryptotext += plusLetter(plaintext[i], key[i % key.length()]);
	}
	//cout << "the key is:" << key << "\n\n";
	//cout << "the key length is:" << key.length() - 1 << "\n\n";
	//cout << "the plaintext is:" << plaintext << "\n\n";
	//cout << "the cryptotext is:" << cryptotext;
}

void fileManipulation::writeIntoFile()
{
	ofstream fout("cryptotext.txt");
	fout << cryptotext;
	fout.close();
}

void fileManipulation::transformKey()
{
	string newKey;
	for (string::size_type i = 0; i < key.size(); i++) {
		if (key[i] >= 'A' && key[i] <= 'Z') {
			newKey += key[i];
		}
		else {
			if (key[i] >= 'a' && key[i] <= 'z') {
				newKey += toupper(key[i]);
			}
		}
	}
	key = newKey;
}

