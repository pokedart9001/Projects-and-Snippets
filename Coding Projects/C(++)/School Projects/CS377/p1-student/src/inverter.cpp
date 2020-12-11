#include <map>
#include <queue>
#include <set>
#include <string>

#include "inverter.h"

using namespace std;

bool nextWord(ifstream *fs, string *currentWord) {
	string word = "";
	char currentChar;

	while (fs->get(currentChar)) {
		if (isalpha(currentChar)) {
			word += currentChar;
		} else {
			*currentWord = word;
			return true;
		}
	}

	return false;
}

string build_inverted_index(string inputs) {
	map<string, set<int>> invertedIndex;
	ifstream fileList(inputs, ifstream::out);

	string currentFile;
	for (int index = 0; getline(fileList, currentFile); index++) {
		ifstream textFile(currentFile, ifstream::out);

		string currentWord;
		while (nextWord(&textFile, &currentWord)) {
			if (!currentWord.empty()) {
				invertedIndex[currentWord].insert(index);
			}
		}

		textFile.close();
	}

	fileList.close();

	ostringstream output;
	for (auto mapElem : invertedIndex) {
		output << mapElem.first << ":";
		for (int i : mapElem.second) {
			output << " " << i;
		}
		output << endl;
	}

	return output.str();
}