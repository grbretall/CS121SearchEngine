// package ir.assignments.two.a;
// 
// /**
//  * Basic class for pairing a word/2-gram/palindrome with its frequency.
//  * 
//  * DO NOT MODIFY THIS CLASS
//  */
public final class FrequencyFloat {
	private final String word;
	private long frequency;
	
	public FrequencyFloat(String word) {
		this.word = word;
		this.frequency = 0;
	}
	
	public FrequencyFloat(String word, int frequency) {
		this.word = word;
		this.frequency = frequency;
	}
	
	public String getText() {
		return word;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public void incrementFrequency() {
		frequency++;
	}
	
	@Override
	public String toString() {
		return word + ":" + frequency;
	}
}
