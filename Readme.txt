Spell Checker
This program is a Java implementation of a spell checker using external data, specifically the Jazzy English language dictionary.

Implementation
The spell checker is implemented using two data structures: a search tree and a trie. The program can be configured to use either structure through a configuration file named a1properties.txt. If the configuration file is missing, the program defaults to using a trie.

The program reads the Jazzy English language dictionary from a file named english.0, which must be located in the same directory as the program's main class file.

Given a word, the program determines if it is spelled correctly. If it is not, the program suggests up to three alternative spellings from the dictionary.

The program processes input from a file named on the command line, with one word per line. The output is written to a file named output.txt in the same format as the input, with suggested alternative spellings for misspelled words.

Analysis
Based on our testing, the trie implementation is faster than the search tree implementation for this particular use case. The reason for this is that the trie has a faster lookup time than the search tree. In big-Θ notation, the time complexity of the trie implementation is O(k), where k is the length of the word being looked up, while the time complexity of the search tree implementation is O(log n), where n is the number of words in the dictionary. Since k is typically much smaller than n, the trie implementation is faster.
