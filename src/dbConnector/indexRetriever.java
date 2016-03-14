//Project by Gregory Bretall (21284961), Miles Bonner (82127215), Zach Anderson (22109634), and Lauren Dimailig (73117811)
package dbConnector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class indexRetriever
{
	private Connection connection;
	
	public indexRetriever() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		// Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();
         // Connect to the test database
        connection = DriverManager.getConnection("jdbc:mysql:///wordrelations","infom141", "cs121");
	}
	
	public void close() throws SQLException {
		connection.close();
		HashMap<String,Integer> hash = new HashMap<String,Integer>(); 
	}
	
	
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();

    public void tfIdfCalculator(HashMap<Integer, ArrayList<Integer>> docIDtoTerm, HashMap<Integer, String> IDToTerm) {
    	for (Integer key : docIDtoTerm.keySet()){
        	ArrayList<Integer> termsInDocList = docIDtoTerm.get(key);
        	for (Integer item : termsInDocList){
        		String word = IDToTerm.get(item);
        		double docCounter = 0;
        		for (Integer key2 : docIDtoTerm.keySet()){
        			if (docIDtoTerm.get(key2).contains(item)){
        				docCounter += 1;
        			}
        		}
        		
        		double occurrences = Collections.frequency(termsInDocList, item);
        		double tf = occurrences;
        		
        		//System.out.println(item+" tf: " + (tf));
        		double wtf = (1+Math.log10(tf));
        		double idf = Math.log10(Math.abs(docIDtoTerm.size())/docCounter);
        		double tfidf = wtf * idf;
        		System.out.println("DOCUMENT " + key + ": ITEM id ~ " + item + ", TFIDF ~ " + tfidf);
        	}
        	int total = docIDtoTerm.get(key).size();
        }
    }  
    
    public double milesTFIDFCalculator(Integer termF, Integer docFreq, Integer corpusSize){
    	double tfidf = (1+Math.log10(termF)) * Math.log10(Math.abs(corpusSize/docFreq));
    	return tfidf;
    }
	
	public HashMap<String,Integer> termToID() throws SQLException {
		HashMap<String, Integer> returnMap = new HashMap<String, Integer>();
		String query = "SELECT * FROM words;";
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery(query);
		while (result.next()){
			returnMap.put(result.getString(2), result.getInt(1));
		}
		return returnMap;
		
	}
	
	public HashMap<Integer, String> IDToTerm() throws SQLException {
		HashMap<Integer, String> returnMap = new HashMap<Integer, String>();
		String query = "SELECT * FROM words;";
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery(query);
		while (result.next()){
			returnMap.put(result.getInt(1),result.getString(2));
		}		
		return returnMap;
		
	}
	
	public HashMap<Integer, ArrayList<Integer>> docIDToTermList() throws SQLException {
		HashMap<Integer, ArrayList<Integer>> returnMap = new HashMap<Integer, ArrayList<Integer>>();
		String query = "select u.id as url_id, GROUP_CONCAT(w.id) as words " +
		"from words w, urls u, words_in_url wiu " +
		"where w.id = wiu.word_id && u.id = wiu.url_id " +
		"group by u.id;";
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery(query);
		while (result.next()){
			String tempStr = result.getString(2);
			String[] tempList;
			tempList = tempStr.split(",");
			ArrayList<Integer> intList = new ArrayList<Integer>();
			for (int i=0; i<tempList.length;i++){
				intList.add(Integer.parseInt(tempList[i]));
			}
			
			returnMap.put(Integer.parseInt(result.getString(1)), intList);
		}

		
		return returnMap;
	}
	
}
