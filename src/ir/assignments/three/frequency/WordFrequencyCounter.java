//Project by Gregory Bretall (21284961), Miles Bonner (82127215), Zach Anderson (22109634), and Lauren Dimailig (73117811)
package ir.assignments.three.frequency;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 * 
	 * The output list of frequencies should be 
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {
      List<Frequency> returnList = new ArrayList<Frequency>();      
      HashMap<String,Integer> freqMap = new HashMap<String,Integer>();
      
      //if the list isn't empty do this
      if (words.size() > 0){
         for(int i=0; i < words.size(); i++){
            String tempWord = words.get(i);
            if (!freqMap.containsKey(tempWord)){
               freqMap.put(tempWord, 1);
            } else
               freqMap.put(tempWord, (freqMap.get(tempWord)+1));
         }
         //create frequencies from map values
         for (String word: freqMap.keySet()){
            returnList.add(new Frequency(word, freqMap.get(word)));
         }
         //sort returnlist using FrequencyComparator
         Collections.sort(returnList, new FrequencyComparator());
         return returnList;
      } else{
         //returns an empty list if the initial list was empty
         return returnList;
      }
	}
	
	/**
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
