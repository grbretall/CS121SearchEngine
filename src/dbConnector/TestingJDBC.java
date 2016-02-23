package dbConnector;

import ir.assignments.three.frequency.Utilities;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class TestingJDBC {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		//DbConnector db = new DbConnector();
		//db.insertTokens(Utilities.tokenizeFile(new File("file1.txt")), "file1.txt");
		//db.insertTokens(Utilities.tokenizeFile(new File("file2.txt")), "file2.txt");
		indexRetriever iR = new indexRetriever();
		iR.termToID();
		HashMap<Integer, String> IDToTerm = iR.IDToTerm();
		HashMap<Integer, ArrayList<Integer>> docIDToTerms = iR.docIDToTermList();
		iR.tfIdfCalculator(docIDToTerms, IDToTerm);
		iR.close();
	}

}
