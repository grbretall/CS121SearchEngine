package searchEngineMain;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import TfIdf.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Mubin Shrestha
 */
public class TfIdfMain {
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();
    /**
     * Main method
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term frequency inverse document frequency        
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
//                 tf = new TfIdf().tfCalculator(docTermsArray, terms);
//                 idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tf = tfCalculator(docTermsArray, terms);
                idf = idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
    }  
    public double idfCalculator(List<String[]> allTerms, String termToCheck) {
        double count = 0;
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        return Math.log(allTerms.size() / count);
    }
    public double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / totalterms.length;
    }
    
//     public static void main(String args[]) throws FileNotFoundException, IOException
//     {
//          
//         TfIdfMain.tfIdfCalculator();
//             
//     }
}
