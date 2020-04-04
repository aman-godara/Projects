package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import required.LinkedHashMap;
import required.StringRefiner;   

public class SpellChecker {
	private LinkedHashMap<String, String> ldmap; 
	private int[][] markers ; 
	public static char[] char_arr1 = new char[]{'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'} ;
	public static char[] char_arr2 = new char[]{'l','k','j','h','g','f','d','s','a','m','n','b','v','c','x','z','p','o','i','u','y','t','r','e','w','q'} ;
	
	public SpellChecker (int size, String inputFile, String outputFile) throws Exception {
		int[][] temp = SpellChecker.pointers(size) ; 
		this.markers = temp ; 
		this.ldmap = new LinkedHashMap<String, String>( (temp[temp.length-1][1]) + 1) ;
		System.out.println("Length of LinkedHashMap: " + ( (temp[temp.length-1][1]) + 1 ) ) ; 
		this.fillRefDict(inputFile, outputFile) ;		
	}
	
	public static void main(String[] args) throws Exception { 
		SpellChecker obj1 = new SpellChecker(14, "src/main/Reference Dictionary.txt", "src/main/Stored Dictionary.txt") ; 
		//obj1.printlength() ;
		obj1.check( "src/main/","laotop", ".txt" ) ;
		//Please enter the word you are looking for in the ### section.  A new file will be created 
		//at the location "src/main/" with extension ".txt" and name as inputed by you in the 
		///section ###. 
		 
		
	}
	
	public void fillRefDict (String inputFile,String outputFile) throws Exception {		
		try {
			FileInputStream f1 = new FileInputStream(inputFile) ;
			Scanner input = new Scanner(f1) ;
			
			FileOutputStream f2 = new FileOutputStream (outputFile, false) ;
			PrintStream output = new PrintStream (f2) ;
			
			while ( input.hasNextLine() ) {
				String line = input.nextLine() ; 
				String refined_line = StringRefiner.refine(line) ; 
				int hash_value = this.hashFunc(refined_line) ; 
				this.ldmap.add(line, refined_line, hash_value);
				output.print(line + " # " + refined_line + " # " + hash_value+"\n");
			}
			input.close(); 	
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input_File_Address_(" + inputFile +")_Not_Found"); 
		}
	}
	
	
	private int weight1(char chr) throws Exception {
		for (int i = 0 ; i < SpellChecker.char_arr1.length ; i++) {
			if ( chr == SpellChecker.char_arr1[i] ) {
				return i ; 
			} 
		}
		throw new Exception();
	}
	
	private int weight2(char chr) throws Exception {
		for (int i = 0 ; i < SpellChecker.char_arr2.length ; i++) {
			if ( chr == SpellChecker.char_arr2[i] ) {
				return i ; 
			} 
		}
		throw new Exception();
	}
	
	
	private static int lengthCalc(int input) {
		int length = (int) ( ( Math.round((double)(25 * input)/4) ) + 1 ) ; 
		//System.out.println(length ) ;
		if ( isOddEven(length) == -1) {
			int a = (length + 1)/ 2 ;
			//System.out.println( ( (4*(a-1)*(a-1)) ) + 2*( 2 * a - 1 ) );
			return ( (4*(a-1)*(a-1)) ) + 2*( 2 * a - 1 ) ; 
		}
		else {
			int a = length/ 2 ;
			//System.out.println( 4*a*a ) ;
			return ( 4*a*a ) ; 
		}
		//return ( (length - 1)*(length - 1) + 1 ); 
	}
	
	private static int[][] pointers (int input) throws Exception {
		if (input < 1) {
			throw new Exception() ; 
		}
		int[][] out = new int[input + 1][3] ;
		int initiate = -1 ; 
		for (int i = 0 ; i < input+1; i++) {
			int length_of_i = lengthCalc(i) ; 
			out[i][0] = initiate + 1 ;
			out[i][1] = initiate + length_of_i ;
			out[i][2] = length_of_i ; 
			initiate = out[i][1] ; 
			//System.out.println(out[i][0] + " " + out[i][1] + " " + out[i][2] ) ;
		}
		return out ; 
	}
	
	private static int isOddEven(int input) {
		if (input == 0) {
			return 1 ; 
		}
		else {
			if (input%2 == 0) {
				return 1 ;				
			}
			else {
				return -1 ; 
			}
		}
	}
	
	private int hashFunc(String key) throws Exception {
		int out ; 
		if (key.length() < this.markers.length ) {
			out = this.markers[key.length()][0] ; 	
		}
		else {
			out = this.markers[this.markers.length - 1][0] ;
			key = key.substring(0, this.markers.length - 2) ; 
		}
		
		int top_length = (int) ( ( Math.round( (double)( (double)(25 * key.length()) / 4 ) ) ) + 1 ) ;
		//System.out.println(out) ; 
		//System.out.println(top_length) ; 
		
		int weight1 = 0 ; 
		for (int i = 0 ; i < key.length(); i++) {
			weight1 = weight1 + weight1(key.charAt(i)) ; 
		}
		
		//System.out.println(weight1) ; 
		int weight1_box = (int)( Math.round( (double) weight1 / 4 ) ) + 1; 
		//System.out.println(weight1_box) ; 
		
		if (isOddEven(top_length) == -1) {
			int median = (top_length + 1)/2 ; 
			if (weight1_box <= median + 1) {
				out = out + 2*(weight1_box-1)*(weight1_box-1) ; 
			}
			else {
				out = out + 2*median*median + 2*(median - 1)*(median - 1) - 2*(2*median - weight1_box)*(2*median - weight1_box); 
			}
		}
		else {
			double median = (double)(top_length + 1)/2 ;
			if ( weight1_box <= Math.round(median) ) {
				out = out + 2*(weight1_box-1)*(weight1_box-1) ; 
			}
			else {
				out = (int) (out + 2*(Math.round(median - 1))*(Math.round(median - 1)) + 2*(Math.round(median - 1))*(Math.round(median - 1)) - 2*(2*median - weight1_box)*(2*median - weight1_box)) ;
			}
			
		}
		//System.out.println(out) ; 
		
		int bottom_length ;
		double median = (double)(top_length + 1)/2 ; 
		if (weight1_box <= median) {
			bottom_length = 2 * (2 * weight1_box - 1) ;
		}
		else {
			bottom_length =  (int) (2 * (2 * (2 * median - weight1_box) - 1 ) ) ;
		}
			
		int weight2 = 0 ; 
		for (int i = 0 ; i < key.length() ; i++) {
			weight2 = weight2 + weight2( key.charAt(i) ) ; 
		}
		
		//System.out.println(weight2) ; 
		//System.out.println(bottom_length) ; 
		//System.out.println( out + (weight2 % bottom_length) ) ; 		
		return (out + (weight2 % bottom_length)) ; 
	}
	
	private void check(String outputFileLocation, String input, String extension) throws Exception {
		long start = System.currentTimeMillis() ;
		
		String input_key = StringRefiner.refine(input) ; 
		int[] out = new int[500] ;
		for (int i = 0 ; i < out.length ; i++) {
			out[i] = -1 ; 
		}
		
		String modified_key = SpellChecker.convert(input_key) ;
		this.TypingMistakehashFunc(out, modified_key) ; 
		//SpellChecker.addtoarr(out, this.hashFunc(modified_key));
		
		for (int i = 97 ; i < 123; i++) {
			String temp = SpellChecker.insert(modified_key, (char)i);
			SpellChecker.addtoarr(out , this.hashFunc(temp)) ; 
		}
		
		for (int i = 0 ; i < modified_key.length() ;i++) {
			String rem_modified_key = SpellChecker.kick(modified_key, i) ;
			SpellChecker.addtoarr(out , this.hashFunc(rem_modified_key)) ; 
			
			for (int j = 97 ; j < 123 ; j++) {
				String temp = SpellChecker.insert(rem_modified_key, (char)j) ; 
				addtoarr(out , this.hashFunc(temp)) ; 
			}
		}
		
		int combinations = 0 ;
		try {			
			FileOutputStream f2 = new FileOutputStream (outputFileLocation + input + extension, false) ;
			PrintStream output = new PrintStream (f2) ;			
			for (int i = 0 ; i < out.length ; i++) {
				if ( out[i] == -1) {
					break ; 
				}				
				else {
					combinations = combinations + 1 ; 
					this.ldmap.printdata(output, out[i]) ;	
					//System.out.println(hash_value); 
				}
			}
			output.close(); 
		} catch (FileNotFoundException e) {
			System.out.println("Output_File_Address_(" + outputFileLocation + input + extension +")_Not_Found"); 
		}
		
		
		long end = System.currentTimeMillis() ;
		System.out.println("Number of combinations tested: " + combinations); 
		System.out.println("Time Taken: " + (end - start) ) ; 
	}
	
	/*private static void addtoarr (String[] arr, String str) {
		//String a = this.map.convert(str) ; 
		for (int i = 0 ; i < arr.length ; i++) {
			if (arr[i] == null) {
				arr[i] = str ; 
				break ; 
			}
			else if ( arr[i].compareTo(str) == 0 ) {
				break ; 
			}		
		}
	}*/
	
	private static String kick (String str, int i) {
		String temp = "" ;  
		for (int j = 0 ; j < str.length(); j++) {
			if ( j == i) {
				continue ; 
			}
			else {
				temp = temp + str.charAt(j) ; 
			}
		}
		return temp ; 
	}
	
	private static String convert(String key) { 
		String temp = "" ; 
		for (int j = 97 ; j < 123 ; j++) { 
			for (int i = 0 ; i < key.length() ; i++) {
				if (key.charAt(i) == (char)j) {
					temp = temp + (char)j ;
				}
			}
		}
		return temp ;
	}
	
	private static String insert(String str, char chr) {
		String temp = "" ; 
		int a = 1 ; 
		
		for (int i = 0 ; i < str.length(); i++) {
			if ( a == 1 && str.charAt(i) >= chr ) {
				temp = temp + chr + str.charAt(i) ; 
				a = 0 ; 
			}
			else {
				temp = temp + str.charAt(i) ; 
			}
		}
		
		if ( a == 0 ) {
			 return temp ; 
		}
		else {
			return ( temp + chr ) ; 
		}		
	}
	
	@SuppressWarnings("unused")
	private void printlength() {
		try {			
			FileOutputStream f2 = new FileOutputStream ("src/main/LENGTHSTORED", false) ;
			PrintStream output = new PrintStream (f2) ;
			this.ldmap.printlength( output ); 
			output.close(); 
		} catch (FileNotFoundException e) {
			System.out.println("Output_File_Address_(" + "src/main/LENGTHSTORED.txt" + ")_Not_Found"); 
		}
	}
	
	private void TypingMistakehashFunc(int[] typing_out, String key) throws Exception {
		int out ; 
		if (key.length() < this.markers.length ) {
			out = this.markers[key.length()][0] ; 	
		}
		else {
			out = this.markers[this.markers.length - 1][0] ;
			key = key.substring(0, this.markers.length - 2) ; 
		}
		
		int top_length = (int) ( ( Math.round( (double)( (double)(25 * key.length()) / 4 ) ) ) + 1 ) ;
		//System.out.println(out) ; 
		//System.out.println(top_length) ; 
		
		int weight1 = 0 ; 
		for (int i = 0 ; i < key.length(); i++) {
			weight1 = weight1 + weight1(key.charAt(i)) ; 
		}
		
		//System.out.println(weight1) ; 
		int weight1_box = (int)( Math.round( (double) weight1 / 4 ) ) + 1; 
		//System.out.println(weight1_box) ; 
		
		if (isOddEven(top_length) == -1) {
			int median = (top_length + 1)/2 ; 
			if (weight1_box <= median + 1) {
				out = out + 2*(weight1_box-1)*(weight1_box-1) ; 
			}
			else {
				out = out + 2*median*median + 2*(median - 1)*(median - 1) - 2*(2*median - weight1_box)*(2*median - weight1_box); 
			}
		}
		else {
			double median = (double)(top_length + 1)/2 ;
			if ( weight1_box <= Math.round(median) ) {
				out = out + 2*(weight1_box-1)*(weight1_box-1) ; 
			}
			else {
				out = (int) (out + 2*(Math.round(median - 1))*(Math.round(median - 1)) + 2*(Math.round(median - 1))*(Math.round(median - 1)) - 2*(2*median - weight1_box)*(2*median - weight1_box)) ;
			}
			
		}
		//System.out.println(out) ; 
		
		int bottom_length ;
		double median = (double)(top_length + 1)/2 ; 
		if (weight1_box <= median) {
			bottom_length = 2 * (2 * weight1_box - 1) ;
		}
		else {
			bottom_length =  (int) (2 * (2 * (2 * median - weight1_box) - 1 ) ) ;
		}
			
		int weight2 = 0 ; 
		for (int i = 0 ; i < key.length() ; i++) {
			weight2 = weight2 + weight2( key.charAt(i) ) ; 
		}
		
		//System.out.println(weight2) ; 
		//System.out.println(bottom_length) ; 
		//System.out.println( out + (weight2 % bottom_length) ) ; 	
		addtoarr( typing_out, (out + (weight2 % bottom_length)) ) ;
		addtoarr( typing_out, (out + ( (weight2 + 1) % bottom_length)) ) ;
		addtoarr( typing_out, (out + ( (weight2 + bottom_length - 1) % bottom_length)) ) ; 
	}
	
	private static void addtoarr (int[] arr, int abc) {
		for (int i = 0 ; i < arr.length ; i++) {
			if ( arr[i] == -1 ) {
				arr[i] = abc ; 
				break ; 
			}
			else if ( arr[i] == abc ) {
				break ; 
			}		
		}
	}
	
}
