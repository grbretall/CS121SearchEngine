package ir.assignments.three.frequency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */
    
   //This function takes in a file and turns it into tokens - it treats punctuation
   //as one word - e.g. first removing apostraphes then tokenizing (can't --> cant)
	public static ArrayList<String> tokenizeFile(File input) {
      //try statement to catch if file doesn't exist
      try {
         //initialize scanner that reads in file
         Scanner tokenizer = new Scanner(input);
         ArrayList<String> stringList = new ArrayList<String>();
         //as long as the file has another line, keep reading in
         while (tokenizer.hasNextLine()) {
            String line = tokenizer.nextLine();
            stringList.add(line);
         }
         //initializing the list to be returned
         ArrayList<String> returnList = new ArrayList<String>();
         
         //for loop to parse the data and add it to the returnList
         for (int i=0; i < stringList.size(); i++){
            String temp = stringList.get(i);
            temp = temp.toLowerCase();
            //does the following --> "can't the dog come in" to ["cant", "the", "dog", "come", "in"]
            for (String splitStr: temp.split("[^\\w']+")){
               //splitStr = splitStr.replaceAll("'","");
               if (!splitStr.equals(""))
                  returnList.add(splitStr);
            }
         }
         return returnList;
      } catch (Exception e) {
         System.out.println("That file doesn't seem to exist!");
         return null;
      }
	}

	// This function takes in a file and turns it into tokens - it treats
	// punctuation
	// as one word - e.g. first removing apostraphes then tokenizing (can't -->
	// cant)
	public static ArrayList<String> tokenizeFile(String s) {
		// try statement to catch if file doesn't exist
			// initializing the list to be returned
			ArrayList<String> returnList = new ArrayList<String>();
			

				for (String splitStr : s.toLowerCase().split("[^\\w']+")) {
					splitStr = splitStr.replaceAll("[^a-zA-Z0-9]", "");
					if (!splitStr.equals(""))
						returnList.add(splitStr);
				}
			return returnList;
	}
	
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		int total = 0;
      // for loop to caluculate total number
      if (frequencies.size() != 0){
         for (int i=0; i<frequencies.size(); i++){
            total += frequencies.get(i).getFrequency();
         }
      }
      int unique = frequencies.size();
      
      // print statements for total and unique
      System.out.println("Total item count: " + total);
      System.out.println("Unique item count: " + unique);
      
      // for loop to handle printing of individual frequencies
      System.out.println();
      for (int i=0; i<frequencies.size(); i++){
         System.out.printf("%-30s %30s %n", frequencies.get(i).getText(), frequencies.get(i).getFrequency());
      }
	}
}
