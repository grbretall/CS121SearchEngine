package dbConnector;

import java.sql.*;
import java.util.List;

public class DbConnector {
	
	private Connection connection;
	
	public DbConnector() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		// Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();
         // Connect to the test database
        connection = DriverManager.getConnection("jdbc:mysql:///wordrelations","infom141", "cs121");
	}
	
	public void dbConnectorCloser() throws SQLException{
		connection.close();
	}
	
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
	
	
	
	@SuppressWarnings("finally")
	public int insertWord(String word) throws SQLException{
		String insertWordString = "INSERT INTO words(word) VALUES(?);";		
		try {
			PreparedStatement insertWord = connection.prepareStatement(insertWordString);
			insertWord.setString(1, word);
			insertWord.executeUpdate();
			System.out.println("inserted");
		} catch (SQLException e) {
			System.out.println(word);
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
		String insertWordInUrlString = "Insert INTO words_in_url(word_id, url_id) values(?, ?);";
		try {
			PreparedStatement insertWordInUrl = connection.prepareStatement(insertWordInUrlString);
			insertWordInUrl.setInt(1, wordId);
			insertWordInUrl.setInt(2, urlId);
			insertWordInUrl.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to add pairs with wordID: "+ wordId+ " and urlId: "+ urlId);
		}
		
	}
		
	public void insertTokens(List<String> tokenArray, String origin) throws SQLException{	
		int wordId;
		int urlId = insertUrl(origin);
		for(String token: tokenArray){
			System.out.println(token);
			wordId = insertWord(token);
			insertWInU(wordId, urlId);
		}
		
	}

}
