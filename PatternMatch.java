/*
 * Pattern matching:
 *  
 * Algorithm options:  
 * 1.LCSS
 * 2.Naive string search
 * 3.KMP algo
 * 4.Boore algo
 * 
 * How to use:
 * Please place test file and existing file in same folder with java file.
 * Then run using  
 * 
 * javac PatternMatch.java
 * 
 * java PatternMatch algo_option test_filename exist_Filename1 exist_Filename2 exist_Filename3
 * 
 * e.g.
 * Sentence: 'john doe is here' found in file: 'exist1.txt'
 * Sentence: 'john doe is here' found in file: 'exist2.txt'
 * Elapsed nanoTime: 115

 * Result: 1 line plagiarised out of 1 line tested = 100% plagiarism
 * Significant plagiarism detected( >= 51%)
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PatternMatch {	
	
	public static ArrayList<String> readPara(String fileName){
		File file=new File(fileName);
		ArrayList<String> paras = new ArrayList<String>();
		Scanner scnr = null;
		try {
			scnr = new Scanner(file);			
			scnr.useDelimiter("(?!^)(?m)(?=^)");
		} catch (FileNotFoundException e) {
			System.err.println("File Not found:"+fileName);
	        System.exit(1);
		}
		
		do{
			String testPara = scnr.next().trim();		
			if(testPara.length() > 0)
				paras.add(testPara);
		}while( scnr.hasNext() );		
		return paras;
	}
	
	static int[] paraList;
	
	public static void main(String[] args) {
		//This defines the number of existing files to test against
		int maxNumberOfExistingFiles = 3; 
		
		//parse command arguments
		int algoSelected=0;
		String testFileName = null;
		String[] existingFileList = null ;
		
		//parse command line arguments
		if (args.length > 0 && args.length < (maxNumberOfExistingFiles + 2) ) {
		    try {
		    	algoSelected = Integer.parseInt(args[0]);
		    	if (algoSelected < 1 || algoSelected > 4){
		    		throw new Exception();
		    	}
		    }catch (Exception e) {
		        System.err.println("Wrong algo selected:" + args[0] + " must be an integer between 1 and 4.");
		        System.exit(1);
		    }
		    
		    testFileName=args[1];
		    existingFileList = new String[args.length-2];
		    for (int i=0 ; i < (existingFileList.length) ; i++ ){
		    	existingFileList[i]=args[i+2];	
		    }
		}else{
			System.err.println("Wrong number of command line arguments:"+args.length);
	        System.exit(1);
		}
				
		//call file reading functions
		boolean answer=false;
		char[] testFile;

		paraList = new int[readPara(testFileName).size()+1];
		
		float totalLinesTested=0;
		float numOfLinesPlagiarised =0;
		float plagiarismPercent=0;
		long lStartTime = 0;
		long lEndTime=0;
		long difference=0;

		if(algoSelected == 1){
			for(int i =0; i<existingFileList.length; i++ ){
				lStartTime = System.nanoTime();
				answer = findPlagiarismUsingLcss(testFileName,existingFileList[i]);
				lEndTime=System.nanoTime();
				difference = difference+(lEndTime - lStartTime);	 
			}
			
			float sum=0;
			for (int j : paraList){ sum += j; }	
			float totalPara =readPara(testFileName).size();
			plagiarismPercent = (sum/totalPara)*100;
			System.out.println("\nLCSS result: "+sum +" paragraphs out of "+ totalPara+" paragraphs matched = "+plagiarismPercent+"% plagiarism");
		
		}else{
			
			lStartTime = System.nanoTime();
			
			File testFileInstance=new File(testFileName);
			Scanner scnr = null;
			try {
				scnr = new Scanner(testFileInstance);
				scnr.useDelimiter("\\.");
			} catch (FileNotFoundException e) {
				System.err.println("File Not found:"+testFileName);
		        System.exit(1);
			}
			
			//reading each line of test file 
			while( scnr.hasNext() ){
				String testLine = scnr.next();				
				if(testLine.length()  == 0){
					continue;
				}
								
				testFile=testLine.toCharArray();
				
				totalLinesTested=totalLinesTested+1;
				boolean alreadyPlagiarised=false;
				
				//reading one existing file at a time
				for(int i =0; i<existingFileList.length; i++ ){
					char[] anExistingFile=readFileInCharArray(existingFileList[i]);
					//call respective algorithms function
					if(algoSelected == 2){
						lStartTime = System.nanoTime();
						answer = naiveStringSearch(testFile,anExistingFile);
						lEndTime=System.nanoTime();
						difference = difference+(lEndTime - lStartTime);
						
						if (answer == true){
							
							System.out.println("Sentence: '"+testLine+"' found in file: '"+existingFileList[i]+"' ");
							if(alreadyPlagiarised == false){
								numOfLinesPlagiarised=numOfLinesPlagiarised+1;
								alreadyPlagiarised=true;
								}
							
							}
						}
					if(algoSelected == 3){ 
						lStartTime = System.nanoTime();
						answer = kmpAlgo(testFile,anExistingFile);
						lEndTime=System.nanoTime();
						difference = difference+(lEndTime - lStartTime);
						if (answer == true){
							System.out.println("Sentence: '"+testLine+"' found in file: '"+existingFileList[i]+"' ");
							if(alreadyPlagiarised == false){
								numOfLinesPlagiarised=numOfLinesPlagiarised+1;
								alreadyPlagiarised=true;
								}
							
							}
						
						}
					if(algoSelected == 4){
						lStartTime = System.nanoTime();
						answer = boyer_moore(testFile,anExistingFile);
						lEndTime=System.nanoTime();
						difference = difference+(lEndTime - lStartTime);
						
						if (answer == true){
							System.out.println("Sentence: '"+testLine+"' found in file: '"+existingFileList[i]+"' ");
							if(alreadyPlagiarised == false){
								numOfLinesPlagiarised=numOfLinesPlagiarised+1;
								alreadyPlagiarised=true;
							}
						}	
					
					}
				}
			}
		
			plagiarismPercent = (numOfLinesPlagiarised/totalLinesTested) * 100;
			System.out.println("\nResult: "+numOfLinesPlagiarised +" line plagiarised out of "+ totalLinesTested+" line matched = "+plagiarismPercent+"% plagiarism");
		}
		
		
		System.out.println("\nElapsed miliseconds: " + (difference/1000000) );
		if(plagiarismPercent >= 51){
			System.out.println("Significant plagiarism detected( >= 51%) ");			
		}
		
	}
	

	public static char[] readFileInCharArray(String testFileName){
		File f=new File(testFileName);
		FileReader fr;
		char[] c = null;
		try {
			fr = new FileReader(f);
			c=new char[(int)f.length()];
			fr.read(c);
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static boolean naiveStringSearch(char[] pattern,char[] text){
		int pat = pattern.length;
	    int txt = text.length;
	    boolean answer=false;

	    for (int i = 0; i <= txt - pat; i++)
	    {
	        int j;
	  
	        /* at i, check for patterns */
	        for (j = 0; j < pat; j++)
	            if (text[i+j] != pattern[j]){
	                break;
	                }	
	        if (j == pat){
	        	// if pat[0...pat-1] = txt[i, i+1, ...i+pat-1]. pattern at index i
	            answer=true;
	        }
	    }
	    return answer;
	}
	
	public static boolean boyer_moore(char[] pattern, char[] text) {

		int m= text.length;
		int n= pattern.length;
		int preProcCharTable[]= new int[256];
		int preProcOffsetTable[]= new int[n];
		int lastPrefix=n;
		//Initializing Char table
		for(int c=0;c<256;c++)
			{	
				preProcCharTable[c]=n;
			}
		int sp;
		for(int k=0; k<n-1; k++)	
			{
				sp=pattern[k];
				if(sp>256)
				{
					continue;
				}
				else
				{
					preProcCharTable[pattern[k]]=n-1-k;
				}
			}

		//Initializing Offset Table
		for(int i=n-1;i>=0;i--)
		{
			if(prefix(pattern,i+1))
				lastPrefix=i+1;
			preProcOffsetTable[n-1-i]=lastPrefix-i+(n-1);
		}
		
		for(int l=0;l<n-1;l++)
		{
			int suffixLength=sufLen(pattern,l);
			preProcOffsetTable[suffixLength]=(n-1)-l+suffixLength;

		}
			
		int skip=0,i,j;
		int test2;
		for(i=n-1; i<m;)
		{
			skip=0;
			for(j=n-1;pattern[j]==text[i]&&j>=0;j--,i--)
			{
				if(j==0)
					return true;
			}
			
			test2=text[i];
			if(test2>256)
			{
				skip=preProcOffsetTable[(n-1) - j];
			}
			else{
				skip=Math.max(preProcOffsetTable[(n-1) - j], preProcCharTable[text[i]]);
			}
			i+=skip;
		}
		
		return false;
	}
	
	public static boolean prefix(char[] pat, int pos)
	{
		for(int m=pos,n=0;m<pat.length;m++,n++)
		{	if(pat[m]!=pat[n])
				return false;
		}
		return true;
	}
	
	public static int sufLen(char[] pat, int pos)
	{
		int length=0;
		for(int m=pos,n=pat.length-1;m>=0 && pat[m]==pat[n];m--,n--)
			length=length+1;
		
		return length;
	}	
	
	public static boolean findPlagiarismUsingLcss(String testFile, String existFile){
		ArrayList<String> test = readPara(testFile);
		ArrayList<String> existing = readPara(existFile);
		int plagiarized = 0;
		int paraNumber=0;
		
		for(String para : test){
			paraNumber++;
			for(String checkingPara : existing){
				String matchingString = lcss(para,checkingPara);
				int lenOfMatch = matchingString.length();
				int lenOfPara = checkingPara.length();
				if(lenOfMatch >= (float)(lenOfPara*0.51)){
					System.out.println("Longest Common Subsequence: '"+matchingString+"'\nMatched from string: '"+checkingPara+"' \nFound in file: '"+existFile+"' ");					
					plagiarized++;
					paraList[paraNumber]=1;
				}
			}
		}
		
		int totalParas = existing.size();
		if(plagiarized >= (float)(totalParas*0.5)){			
			return true;
		}		
		return false;
	}
	
	public static String lcss(String pattern, String checkString) {
	    int[][] lengths = new int[pattern.length()+1][checkString.length()+1]; 
	    StringBuffer buffer = new StringBuffer();
	    
	    for (int i=0; i<pattern.length(); i++)
	        for (int j=0; j<checkString.length(); j++)
	            if (pattern.charAt(i) == checkString.charAt(j))
	                lengths[i+1][j+1] = lengths[i][j] + 1;
	            else
	                lengths[i+1][j+1] = Math.max(lengths[i+1][j], lengths[i][j+1]);
	 	    	    
	    // Get the common subsequence
	    for (int x=pattern.length(), y=checkString.length(); ((x!=0) && (y!=0));) {
	        if (lengths[x][y] == lengths[x-1][y])
	            x--;
	        else if (lengths[x][y] == lengths[x][y-1])
	            y--;
	        else {
	            if(pattern.charAt(x-1) == checkString.charAt(y-1)){
	            	buffer.append(pattern.charAt(x-1));
	            	x-=1;
	            	y-=1;		            		          
	            }
	        }
	    } 
	    return buffer.reverse().toString();
	}

	public static boolean kmpAlgo(char[] pattern, char[] text) {
		
		
        int i = 0, j = 0;
        int patternLen = pattern.length; //pattern length
        int textLen = text.length; //text length
 
        int b[] = prefixSet(pattern); //call prefixSet to calculate Prefix set 
 
        while (i < textLen) {
            while (j >= 0 && text[i] != pattern[j]) 
                j = b[j];
            i++;
            j++;
 
            // a match is found
            if (j == patternLen) {
                j = b[j];
            	return true;
            }
        }
		return false;
}
	
	public static int[] prefixSet(char[] pattern) {
		int i = 0, j = -1;
		int patternLen = pattern.length; //pattern length
		int[] b = new int[patternLen + 1];
		b[i] = j;
		while (i < patternLen) {
			while (j >= 0 && pattern[i] != pattern[j])
		         j = b[j];
		            
		    i++;
		    j++;
		    b[i] = j;
		}
		return b;
	}
	
}

