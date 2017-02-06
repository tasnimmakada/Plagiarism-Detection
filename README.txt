README.txt 

Name: Tasnim Makada, Deepak Verma, Chinthan Bhat, Anurag Uplanchiwar
Student Id: 1001288916, 1001288915, 1001267683, 1001231698

Programming Language: Java 1.6

### Code Structure ###
1. PattenMatch class
Contains all the functions related to substring and subsequence matching.
Added functions:
1.1. readPara(string): Reads a file and splits it in paragraphs for LCSS
1.2. readFileInCharArray(string): Reads a file and converts it into a character array
1.3. naiveStringSearch(char[], char[]): Takes 2 character arrays and executes the naive string matching algorithm on it.
1.4. boyer_moore(char[], char[]): Takes 2 character arrays and executes the Boyer-Moore string matching algorithm on it.
1.5. prefix(char[], int): Takes a character array pattern and integer as input to find the prefix of a pattern for Boyer-Moore algorithm.
1.6. sufLen(char[], int): Takes a character array pattern and integer as input to find the suffix of a pattern for Boyer-Moore algorithm.
1.7. lcss(string, string): Takes 2 string inputs and finds the longest common subsequence using Dynamic Programming
1.8. findPlagiarismUsingLcss(string, string): Checks if the length of longest subsequence is more than 51% of string to verify plagiarism.
1.8. kmpAlgo(char[], char[]): Takes 2 character arrays and executes the KMP string matching algorithm on it.
1.9. prefixSet(char[]): Finds the prefix function values for the pattern in KMP.

### How to run ###
1. Compile the class (the input file has to exist in the same folder)
javac PatternMatch.java

3. Excute the class (the input file has to exist in the same folder)
3.1. LCSS
java 1 pattern.txt exist1.txt exist2.txt
3.2. Naive String matching algorithm
java 2 pattern.txt exist1.txt exist2.txt
3.3. KMP algorithm
java 3 pattern.txt exist1.txt exist2.txt
3.2. Boyer-Moore algorithm
java 4 pattern.txt exist1.txt exist2.txt

### OUTPUT (Example for very simple input files) ###
1. LCSS
Longest Common Subsequence: 'AAABBCCDD'
Matched from string: 'AAABBCCDDA' 
Found in file: 'exist1.txt' 

LCSS result: 1.0 paragraphs out of 1.0 paragraphs matched = 100.0% plagiarism

Elapsed miliseconds: 1
Significant plagiarism detected( >= 51%) 

2. Naive
Sentence: 'john doe is here' found in file: 'exist1.txt' 

Result: 1.0 line plagiarised out of 1.0 line matched = 100.0% plagiarism

Elapsed miliseconds: 0
Significant plagiarism detected( >= 51%) 

3. KMP
Sentence: 'john doe is here' found in file: 'exist1.txt' 

Result: 1.0 line plagiarised out of 1.0 line matched = 100.0% plagiarism

Elapsed miliseconds: 0
Significant plagiarism detected( >= 51%) 

4. Boyer-Moore
Sentence: 'john doe is here' found in file: 'exist1.txt' 

Result: 1.0 line plagiarised out of 1.0 line matched = 100.0% plagiarism

Elapsed miliseconds: 0
Significant plagiarism detected( >= 51%) 
