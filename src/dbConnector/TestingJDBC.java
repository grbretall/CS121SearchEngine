package dbConnector;

import ir.assignments.three.frequency.Utilities;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class TestingJDBC {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		//DbConnector db = new DbConnector();
		//db.insertTokens(Utilities.tokenizeFile(new File("file1.txt")), "file1.txt");
		//db.insertTokens(Utilities.tokenizeFile(new File("file2.txt")), "file2.txt");
		Scanner wordIndexScanner = new Scanner(new File("Outputs/WordIndex.txt"));
        //ArrayList<String> stringList = new ArrayList<String>();
        HashMap<Integer,String> IDTermList = new HashMap<Integer,String>();
        HashMap<String,Integer> TermIDList = new HashMap<String,Integer>();
        //as long as the file has another line, keep reading in
        while (wordIndexScanner.hasNextLine()) {
           String line = wordIndexScanner.nextLine();
           String[] stringList = line.split(",");
           IDTermList.put(Integer.parseInt(stringList[0]), stringList[1]);
           TermIDList.put(stringList[1],Integer.parseInt(stringList[0]));
        }
        
		Scanner docIDTermsScanner = new Scanner(new File("Outputs/docid2termlist.txt"));
        HashMap<Integer,ArrayList<Integer>> docIDTermList = new HashMap<Integer,ArrayList<Integer>>();
        //as long as the file has another line, keep reading in
        
        while (docIDTermsScanner.hasNextLine()) {
           String line = docIDTermsScanner.nextLine();
           String[] stringList = line.split("[^\\w']+");
           ArrayList<Integer> arrList = new ArrayList<Integer>();
           for (int i=1;i<stringList.length;i++){
        	   String tempString = stringList[i];
        	   arrList.add(Integer.parseInt(stringList[i]));
           }
           
           docIDTermList.put(Integer.parseInt(stringList[0]), arrList);
        }
        
        System.out.println("TermIDList - created in this: " + TermIDList);
        System.out.println("IDTermList - created in this: " + IDTermList);
        System.out.println("docIDTermList - created in this: " + docIDTermList);
		indexRetriever iR = new indexRetriever();
		iR.termToID();
		HashMap<Integer, String> IDToTerm = iR.IDToTerm();
		HashMap<Integer, ArrayList<Integer>> docIDToTerms = iR.docIDToTermList();
		System.out.println("IDToTerm - iR created: " + IDToTerm);
		System.out.println("docIDToTerms - iR created: " + docIDToTerms);
		iR.tfIdfCalculator(docIDTermList, IDTermList);
		iR.close();
	}

}
