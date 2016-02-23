import ir.assignments.three.frequency.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import dbConnector.DbConnector;

public class IcsIndexer {
	
	public final int corpusSize = 44546;
	
	
	public static HashSet<String> excludedWords = new HashSet<String>();
	
	public static void populateExcludedWords()
	{
		excludedWords.clear();
		excludedWords.addAll(Utilities.tokenizeFile(new File("resources.txt")));
	}

	
	public static void createIndex() throws IOException, ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		JSONParser parser = new JSONParser();
		
		JSONObject arr;
		String fileName;
		String textFile;
		String url;
		DbConnector db = new DbConnector();
		
		
		arr = (JSONObject)parser.parse(new InputStreamReader(new FileInputStream("bbidyuk_html_files/html_files.json")));
		for(int i = 0; i < 44546; i++)
		{
			//System.out.println(arr.get("0"));
			//This section loops through all the different html files
			//in the folder where our corpus is located
			populateExcludedWords();
			/*String myDirectoryPath = "bbidyuk_html_files/html/";
			File dir = new File(myDirectoryPath);
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {*/
					//Here we take the document and put it into a string to be parsed
					fileName = arr.get(new Integer(i).toString()).toString().split("\"")[3];
					url = arr.get(new Integer(i).toString()).toString().split("\"")[7].replace("\\", "");
					Scanner htmlScanner = new Scanner(new File("bbidyuk_html_files/Html/"+fileName));
					textFile = "";
					while (htmlScanner.hasNextLine()) {
						textFile += htmlScanner.nextLine();
					}
					htmlScanner.close();
					//System.out.println(textFile);
					//This will parse the html file into just text, removing all of the tags
					textFile = htmlToText(textFile);
					ArrayList<String> wordsInFile = Utilities.tokenizeFile(textFile);
					if(i%50 == 0){
						System.out.println("File Text= "+textFile);
						System.out.println("url= "+url);
					}
					
					db.insertTokens(wordsInFile, url);
					
					
					/*for(String s: wordsInFile)
					{
						if(excludedWords.contains(s))
						{
							wordsInFile.remove(s);
						}
					}*/
					
					// Remove this, this is only for testing purposes
					
					//return;
		}
		db.dbConnectorCloser();
			/*}
		} else {
			// Handle the case where dir is not really a directory.
			// Checking dir.isDirectory() above would not be sufficient
			// to avoid race conditions with another process that deletes
			// directories.
		}*/
	}

	public static String htmlToText(String html) 
	{
		return Jsoup.parse(html).text();
	}

	public static void main(String[] args) {
		try {
			long startTime = System.currentTimeMillis();
			createIndex();
			long endTime = System.currentTimeMillis();
			System.out.println("That took " + (endTime - startTime) + " milseconds");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
