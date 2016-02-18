import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IcsIndexer 
{
	public static void createIndex() throws FileNotFoundException, ParseException
	{
		JSONParser parser = new JSONParser();
		
			JSONObject arr;
			try {
				arr = (JSONObject)parser.parse(new InputStreamReader(new FileInputStream("bbidyuk_html_files/html_files.json")));
				System.out.println(arr.get("0"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Scanner htmlScanner = new Scanner(new File("bbidyuk_html_files/Html/access.ics.uci.educontact.html"));
		String textFile = "";
		while(htmlScanner.hasNextLine())
		{
			textFile += htmlScanner.nextLine();
		}
		htmlScanner.close();
		System.out.println(textFile);
		textFile = htmlToText(textFile);
		System.out.println(textFile);
	}
	
	public static String htmlToText(String html)
	{
		return Jsoup.parse(html).text();
	}
	
	public static void main(String[] args) throws ParseException
	{
		try {
			createIndex();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

