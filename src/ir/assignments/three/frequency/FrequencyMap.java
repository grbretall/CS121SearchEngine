//Project by Gregory Bretall (21284961), Miles Bonner (82127215), Zach Anderson (22109634), and Lauren Dimailig (73117811)
package ir.assignments.three.frequency;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class FrequencyMap 
{

	private HashMap<String, Integer> wordFrequencyMap;
	private HashSet<String> excludedWords = new HashSet<String>();
	
	public FrequencyMap()
	{
		wordFrequencyMap  = new HashMap<String, Integer>();
		excludedWords = new HashSet<String>();
	}
	
	public void addToMap(String token)
	{
		if(excludedWords.contains(token) || token.length() < 2)
		{
			return;
		}
		else if(wordFrequencyMap.containsKey(token))
		{
			wordFrequencyMap.put(token, wordFrequencyMap.get(token)+1);
		}
		else
		{
			wordFrequencyMap.put(token, 1);
		}
	}
	
	public void addToMap(String token, int value){
		//System.out.println("token:"+ token+ " value:"+ value);
		wordFrequencyMap.put(token, value);
	}
	
	public HashMap<String, Integer> getMap()
	{
		return wordFrequencyMap;
	}
	
	public int getFrequency(String key)
	{
		return wordFrequencyMap.get(key);
	}
	
	public void setExcludedWords(HashSet<String> toSet){
		excludedWords = toSet;
	}
	
	public void clear()
	{
		wordFrequencyMap.clear();
	}
	
	public ArrayList<Frequency> sort()
	{
		ArrayList<Frequency> returnList = new ArrayList<Frequency>();
		//Comparator new FrequencyComparator();
		wordFrequencyMap.forEach((key, value) -> 
				returnList.add(new Frequency(key, value)));
		Collections.sort(returnList, new FrequencyComparator());
		return returnList;
	}
}
