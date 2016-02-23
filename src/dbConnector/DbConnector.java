package dbConnector;

import ir.assignments.three.frequency.Utilities;

import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.List;

public class DbConnector {
	
	private Connection connection;
	public static HashSet<String> excludedWords = new HashSet<String>();
	
	
	public DbConnector() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		// Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();
         // Connect to the test database
        connection = DriverManager.getConnection("jdbc:mysql:///wordrelations","infom141", "cs121");
        excludedWords.clear();
		excludedWords.addAll(Utilities.tokenizeFile(new File("resources.txt")));
	}
	

	/**
	 * Attempts to close the data base connection.
	 * */
	
	public void dbConnectorCloser() throws SQLException{
		connection.close();
	}
	

	/**
	 * 
	 * @param
	 * @return
	 * */
	public int findWordIdByWord(String word) throws SQLException{
		String queryString = "SELECT * FROM words WHERE words.word = '"+word+"';";
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery(queryString);
		while (result.next()){
			//System.out.println(result.getInt(1)+" "+ result.getString(2));
			return result.getInt(1);
		}
		System.out.println("nothing here");
		return 0;
	}
	

	/**
	 * 
	 * @param
	 * @return
	 * */
	private int findUrlIdByUrl(String url) throws SQLException{
		String queryString = "Select * from urls where url = ?";
		PreparedStatement query = connection.prepareStatement(queryString); 
		query.setString(1, url);
		ResultSet result = query.executeQuery();
		while(result.next()){
			return result.getInt(1);
		}
		return 0;
	}

	/**
	 * 
	 * @param
	 * @return
	 * */
	@SuppressWarnings("finally")
	public int insertWord(String word) throws SQLException{
		String insertWordString = "INSERT INTO words(word) VALUES(?);";		
		try {
			PreparedStatement insertWord = connection.prepareStatement(insertWordString);
			insertWord.setString(1, word);
			insertWord.executeUpdate();
			//System.out.println("inserted");
		} catch (SQLException e) {
			//System.out.println(word);
			//e.printStackTrace();
		} finally{
			return findWordIdByWord(word);
		}	
	}
	
	@SuppressWarnings("finally")
	private int insertUrl(String word) throws SQLException{
		String insertWordString = "INSERT INTO urls(url) VALUES(?);";		
		try {
			PreparedStatement insertWord = connection.prepareStatement(insertWordString);
			insertWord.setString(1, word);
			insertWord.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			return findUrlIdByUrl(word);
		}	
	}
	
	private void insertWInU(int wordId, int urlId){
		String insertWordInUrlString = "Insert INTO words_in_url(word_id, url_id, frequency) values("
				+ ""+wordId+","
				+ ""+urlId+" ,"
				+ " 1)"
				+ " ON DUPLICATE KEY UPDATE frequency = frequency + 1;";
		try {
			//PreparedStatement insertWordInUrl = connection.prepareStatement(insertWordInUrlString);
			//ResultSet result = select.executeQuery(queryString);
			Statement insertWordInUrl = connection.createStatement();
			insertWordInUrl.executeUpdate(insertWordInUrlString);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to add pairs with wordID: "+ wordId+ " and urlId: "+ urlId);
		}
		
	}
		
	public void insertTokens(List<String> tokenArray, String origin) throws SQLException{	
		int wordId;
		int urlId = insertUrl(origin);
		for(String token: tokenArray){
			if(!excludedWords.contains(token) && token.length() > 1){
				//System.out.println(token);
				wordId = insertWord(token);
				insertWInU(wordId, urlId);
			}
		}	
	}
}
