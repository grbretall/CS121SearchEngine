import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jsoup.Jsoup;


public class IcsIndexer 
{
	public static void createIndex() throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File("bbidyuk_html_files/Html/access.ics.uci.educontact.html"));
		String textFile = "";
		while(scan.hasNextLine())
		{
			textFile += scan.nextLine();
		}
		System.out.println(textFile);
		textFile = htmlToText(textFile);
		System.out.println(textFile);
	}
	
	public static String htmlToText(String html)
	{
		return Jsoup.parse(html).text();
	}
	
	public static void main(String[] args)
	{
		try {
			createIndex();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

