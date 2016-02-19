package dbConnector;

import helperPackage.Utilities;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public abstract class TestingJDBC {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		DbConnector db = new DbConnector();
		db.insertTokens(Utilities.tokenizeFile(new File("testFile/file1.txt")), "file1.txt");
		db.insertTokens(Utilities.tokenizeFile(new File("testFile/file2.txt")), "file2.txt");
	}

}
