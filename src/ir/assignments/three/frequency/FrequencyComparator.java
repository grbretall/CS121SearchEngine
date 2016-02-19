package ir.assignments.three.frequency;

import java.util.Comparator;

//Code base formed from on sample from https://stackoverflow.com/questions/3408976/sort-array-first-by-length-then-alphabetically-in-java
public class FrequencyComparator implements Comparator<Frequency>{
   //overriding of the compare function for Frequency
   @Override
   public int compare(Frequency f1, Frequency f2){
      //to create a list in descending order
      if (f1.getFrequency() < f2.getFrequency()){
         return 1;
      } else if (f1.getFrequency() > f2.getFrequency()){ 
         return -1;
      }
      return f1.getText().compareTo(f2.getText());
   }
}