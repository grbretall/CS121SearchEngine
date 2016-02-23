package searchEngineMain;

import ir.assignments.three.frequency.Frequency;
import ir.assignments.three.frequency.FrequencyMap;
import ir.assignments.three.frequency.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import dbConnector.DbConnector;

public class IcsIndexer {

	public final int corpusSize = 44546;

//	public static HashMap<WordUrlIdPair, Integer> wordUrlFrequencyMap = new HashMap<WordUrlIdPair, Integer>();
	public static HashMap<Integer, ArrayList<Integer>> docid2termlist  = new HashMap<Integer, ArrayList<Integer>>();
	public static HashMap<Integer, String> wordMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> urlMap = new HashMap<Integer, String>();
	public static FrequencyMap wordFreqhencies = new FrequencyMap();

	public static HashSet<String> excludedWords = new HashSet<String>();

	public static void populateExcludedWords() {
		excludedWords.clear();
		excludedWords.addAll(Utilities.tokenizeFile(new File("resources.txt")));
		wordFreqhencies.setExcludedWords(excludedWords);
	}
	
	public void initializeData(){
		//ArrayList<String> x = Utilities.tokenizeFile(new File("resources.txt"));
		
	}

	public static void createIndex() throws IOException, ParseException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		long startTime = System.currentTimeMillis();
		long totalTime = 0;
		long averageTime= 0;
		int currentWordId = 1;
		int currentUrlId = 1;
		
		JSONParser parser = new JSONParser();

		JSONObject arr;
		String fileName;
		String textFile;
		String url;

		arr = (JSONObject) parser.parse(new InputStreamReader(
				new FileInputStream("bbidyuk_html_files/html_files.json")));
		for (int i = 0; i < 6; i++) {
			// System.out.println(arr.get("0"));
			// This section loops through all the different html files
			// in the folder where our corpus is located
			populateExcludedWords();
			// Here we take the document and put it into a string to be parsed
			fileName = arr.get(new Integer(i).toString()).toString()
					.split("\"")[3];
			url = arr.get(new Integer(i).toString()).toString().split("\"")[7]
					.replace("\\", "");
			//url = "testfile.txt";
			Scanner htmlScanner = new Scanner(new File(
					"bbidyuk_html_files/Html/" + fileName));
			// Scanner htmlScanner = new Scanner(new File("testfile.txt"));
			textFile = "";
			while (htmlScanner.hasNextLine()) {
				textFile += htmlScanner.nextLine();
			}
			htmlScanner.close();
			//System.out.println(textFile);
			// This will parse the html file into just text, removing all of the
			// tags
			textFile = htmlToText(textFile);
			//System.out.println(textFile);
			//System.out.println(url);
			ArrayList<String> wordsInFile = Utilities.tokenizeFile(textFile);
			if(!urlMap.containsValue(url))
			{
				urlMap.put(currentUrlId, url);
			}
			for (String word : wordsInFile) {
				if (!excludedWords.contains(word) || word.equals(null)) 
				{
					wordFreqhencies.addToMap(word);
					if(!wordMap.containsValue(word))
					{
						wordMap.put(currentWordId, word);
					}
					if(!docid2termlist.containsKey(currentUrlId)){
						ArrayList<Integer> tempArray= new ArrayList<Integer>();
						tempArray.add(currentWordId);
						docid2termlist.put(currentUrlId, tempArray);
					}
					else{
						docid2termlist.get(currentUrlId).add(currentWordId);
					}
					
				  //WordUrlIdPair currentWordUrlPair = new WordUrlIdPair(word, url);
//					if (!wordUrlFrequencyMap.containsKey(currentWordUrlPair)) {
//						wordUrlFrequencyMap.put(currentWordUrlPair, 1);
//					} else {
//						wordUrlFrequencyMap
//								.put(currentWordUrlPair, wordUrlFrequencyMap
//										.get(currentWordUrlPair) + 1);
//					}
				}
				currentWordId++;
			}
			currentUrlId++;
			totalTime += System.currentTimeMillis()- startTime;
			averageTime = (System.currentTimeMillis()- startTime)/currentUrlId;
			
			if(i%100 ==0){
				System.out.println("That took: "+(System.currentTimeMillis() - startTime)/1000 +""
						+ "\n   With average "+ (averageTime)
						+ "\n     i'm at "+i);				
			}

			//System.out.println(System.currentTimeMillis());

			// Remove this, this is only for testing purposes
			/*
			 * DbConnector dbc = new DbConnector();
			 * dbc.insertTokens(wordsInFile, url);
			 */
			// return;
		}
		System.out.println(System.currentTimeMillis() - startTime);
		PrintStream out = new PrintStream(new FileOutputStream(
				"Outputs/WordIndex.txt"));
		System.setOut(out);
		wordMap.forEach((key, value) -> System.out.println(key + ", " + value));
		out.close();
		out = new PrintStream(new FileOutputStream(
				"Outputs/UrlIndex.txt"));
		System.setOut(out);
		urlMap.forEach((key, value) -> System.out.println(key + ", " + value));
		out.close();
//		out = new PrintStream(new FileOutputStream(
//				"Outputs/UrlWordFrequencies.txt"));
//		System.setOut(out);
//		wordUrlFrequencyMap.forEach((key, value) -> System.out.println(key.getUrl() + ", " + 
//				key.getWord() + ", " + value));
//		out.close();
		//docid2termlist
		out = new PrintStream(new FileOutputStream(
				"Outputs/docid2termlist.txt"));
		System.setOut(out);
		docid2termlist.forEach((key, value) -> System.out.println(key + ", " + value));
		out.close();
		
		out = new PrintStream(new FileOutputStream(
				"Outputs/wordFrequencies.txt"));
		System.setOut(out);
		wordFreqhencies.getMap().forEach((key, value) -> System.out.println(key + ", " + value));
		out.close();

	}

	public static String htmlToText(String html) {
		return Jsoup.parse(html).text();
	}

	public static void main(String[] args) {
		try {
			createIndex();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
