package main ;

import required.HashMap ; 

public class WordCounter {
	private HashMap<Integer> count_map ; 

	public WordCounter(){
		this.count_map = null ; 
	}
	
	/*public static void main (String[] args ){
		WordCounter obj1 = new WordCounter () ; 
		int var1 = obj1.count("e", "e") ;
		System.out.println(var1) ; 
	}*/

	public int count(String str, String word){
		int length_str = str.length() ;
		int length_word = word.length();
		
		if ( (length_str - length_word ) < 0 ) {
			return 0 ;
		}
		this.count_map = new HashMap<Integer>( length_str - length_word + 1 ) ; 
		for (int i = 0 ; i < length_str - length_word + 1 ; i++) {
			String temp = "" ;
			for (int j = i ; j < i + length_word; j++) {
				temp = temp + str.charAt(j) ; 
			}
			if ( this.count_map.contains(temp) ) {
				int val = this.count_map.get(temp) ;
				this.count_map.put(temp, val + 1) ; 
			}
			else {
				this.count_map.put(temp, 1) ;
			}
		}
		if (this.count_map.contains(word)) {
			return (this.count_map.get(word) ) ; 
		}
		else {
			return 0; 
		}
	}
}
