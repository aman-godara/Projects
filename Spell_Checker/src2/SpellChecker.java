package dataStructures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class SpellChecker {
	private LinkedHashMap<Integer, String> map ; 
	private int length ; 
	
	public SpellChecker (int size) {
		this.map = new LinkedHashMap<Integer, String> (size) ; 
		this.length = size ; 
	}
	
	public static void main(String[] args) throws EmptyLinkedListException, KeyNotFoundException, FileNotFoundException {
		SpellChecker obj1 = new SpellChecker(1024) ; 
		obj1.updatedict ("src/dataStructures/in.txt") ; 
		//printer(obj1.check("ahmad")) ;
		//transform("src/dataStructures/inputDict.txt" , "src/dataStructures/in.txt") ;
		obj1.check("nonadjuster"); 
	}
	
	public void add(String inpt) {
		this.map.add(0, inpt);
	}
	
	public int delete(String inpt) throws KeyNotFoundException, EmptyLinkedListException {
		return this.map.delete(inpt) ;
	}
	
	public boolean hasKey(String inpt) {
		return this.map.hasKey(inpt) ;
	}
	
	public void updatedict (String inputFile) {		
		try {
			FileInputStream f1 = new FileInputStream(inputFile) ;
			Scanner input = new Scanner(f1) ;
			while ( input.hasNextLine() ) {
				String a = input.nextLine() ; 
				this.add(a);  
			}
			input.close(); 	
		} catch (FileNotFoundException e) {
			System.out.println("Input_File_Not_Found"); 
		}
	}
	
	public void check(String inpt) throws FileNotFoundException {
		long start = System.currentTimeMillis() ;
		String a = this.map.convert(inpt) ;
		String[] out = new String[50] ;
		this.addtoarr(out, a);
		
		for (int i = 0 ; i < this.map.log2(); i++) {
			String temp = a + (char)(97 + i) ;
			this.addtoarr(out , temp) ; 
		}
		
		for (int i = 0 ; i < a.length() ;i++) {
			String b = this.remov(a, i) ; 
			for (int j = 0 ; j < this.map.log2() + 1 ; j++) {
				String temp = b + (char)(96 + j) ;
				this.addtoarr(out , temp) ; 
			}
		}
		
		for (int i = 0 ; i < out.length ; i++) {
			if ( out[i] == null) {
				break ; 
			}
			else {
				this.map.printkey(out[i]) ;				
			}
		}
		long end = System.currentTimeMillis() ;
		System.out.println( end - start ) ; 
	}
	
	
	
	private void addtoarr (String[] arr, String str) {
		String a = this.map.convert(str) ; 
		for (int i = 0 ; i < arr.length ; i++) {
			if (arr[i] == null) {
				arr[i] = a ; 
				break ; 
			}
			else if ( arr[i].compareTo(a) == 0 ) {
				break ; 
			}		
		}
	}
	
	private String remov (String str, int i) {
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
	
	private static void printer(String[] str) {
		for (int i = 0 ; i < str.length ; i++) {
			System.out.println( str[i] ) ; 
		}
	}
	
	private static void transform (String inputFile, String outputFile) {
		
		try {
			FileOutputStream f2 = new FileOutputStream (outputFile, true) ;
			PrintStream output = new PrintStream (f2) ;  
			//output.print(str);
			FileInputStream f1 = new FileInputStream(inputFile) ;
			Scanner input = new Scanner(f1) ;
			while ( input.hasNextLine() ) {
				output.print(trans(input.nextLine()));
			}
			input.close(); 
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("Either Output OR Input File_Not_Found");
		}
		
	}
	
	private static String trans(String str) {
		String a = ""; 
		for (int i = 0 ; i < str.length() ; i++) {
			int p = (int)str.charAt(i) ; 
			if ( (p <= 90 && p >= 65) ) {
				a = a + (char)(p + 32);
			}
			else if ( (p <= 122 && p >= 97) ) {
				a = a + (char)(p) ; 
			}
			else if (p == 32) {
				continue ; 
			}
			else {
				a = "" ; 
				break ; 
			}
		}
		if ( a.compareTo("") != 0 ) {
			a = a + "\n" ; 
			return a ; 
		}
		return "" ; 
	}
	
	
	
}
