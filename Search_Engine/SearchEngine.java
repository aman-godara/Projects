package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import required.EmptySmartArray;
import required.PageInfo;
import required.StringRefiner;
import required.WordInfo;
import required.WrongInput;

public class SearchEngine {
	private Trie wordtree ; 
	private SmartArray<PageInfo> pagesdata ; 
	private int pagescount ; 
	private SmartArray<PageInfo> rank ; 
	
	
	public static void main(String[] args) throws Exception {
		SearchEngine obj1 = new SearchEngine() ; 
		obj1.fillRefData("src/main/", ".txt") ;
		//System.out.println(obj1.getValue("Re eSe")) ; 
		obj1.search("dylan  blue") ; 
	}
	
	
	public SearchEngine() {
		this.wordtree = new Trie() ;
		this.pagesdata = new SmartArray<PageInfo> () ; 
		this.pagescount = 0 ;		
	}
	
	
	public void fillRefData (String location, String extension) throws Exception {
		Scanner input ;
		String inputFile = null ;
		
		int i = 1 ;
		try {
			while (true) {				
				inputFile = location + i + extension ; 
				input = new Scanner( new FileInputStream(inputFile) ) ;
				
				this.pusher(input, i, extension) ; 
				
				input.close() ; 
				i = i + 1 ;
			}
		} catch (FileNotFoundException e) {
			System.out.println("--> All Files Till " + (i - 1) + extension + " Are Scanned"); 
		}
	}
	
	
	private void pusher (Scanner input, int link, String extension) throws WrongInput, EmptySmartArray {
		PageInfo obj1 = new PageInfo (Integer.toString(link) + extension) ; 
		
		while ( input.hasNextLine() ) {
			
			String inputLine = input.nextLine() ; 			
			String temp = "" ;	
			
			for (int j = 0; j < inputLine.length(); j++) {
				char char_at_j = inputLine.charAt(j) ;  
				if ( char_at_j == ' ' || char_at_j == ',' || char_at_j == '.' || char_at_j == ';' ) {
					if (temp != "") {
						this.wordtree.insert( StringRefiner.refine(temp), obj1 ) ;
						System.out.println( "---------------" ) ;
						obj1.words = obj1.words + 1 ;  
						temp = "" ; 
					}				 
				}
				else {
					if ( j == inputLine.length() - 1) {
						temp = temp + Character.toString( char_at_j ) ;
						this.wordtree.insert( StringRefiner.refine(temp), obj1 ) ;
						System.out.println( "---------------" ) ;
						obj1.words = obj1.words + 1 ;  
					}
					else {
						temp = temp + Character.toString( char_at_j ) ;
					}
				}			
			}
			
			this.pagesdata.insertEnd(obj1) ; 
			this.pagescount = this.pagescount + 1 ;	
		}				
	}
	
	public void search (String query) throws WrongInput, EmptySmartArray {
		this.rank = new SmartArray<PageInfo> () ; 
		String temp = "" ;	
		
		for (int j = 0; j < query.length(); j++) {
			char char_at_j = query.charAt(j) ;  
			if ( char_at_j == ' ' || char_at_j == ',' || char_at_j == '.' || char_at_j == ';' ) {
				if (temp != "") {
					this.pageRank(this.wordtree.getValue( temp ) ) ;
					temp = "" ; 
				}				 
			}
			else {
				if ( j == query.length() - 1) {
					temp = temp + Character.toString( char_at_j ) ;
					this.pageRank(this.wordtree.getValue( temp ) ) ;
				}
				else {
					temp = temp + Character.toString( char_at_j ) ;
				}
			}			
		}
		System.out.println("------ Outputs Of Your Searched Query Are ------"); 
		for (int i = 0 ; i < this.rank.getLength(); i++) {
			PageInfo page = this.rank.ithElement(i) ; 
			System.out.println( page.name + " " + page.marks ); 
		}
		System.out.println("------ That's All Of It ------"); 
	}
	
	
	public void pageRank (SmartArray<WordInfo> input) throws WrongInput, EmptySmartArray {
		if (input == null) {
			
		}
		else {
			for (int i = 0 ; i < input.getLength() ; i++) {
				WordInfo wid = input.ithElement(i) ; 
				int marks =  pageRankAlgo(wid);			
			
				if ( this.ifExists(wid.page) ) {
					wid.page.increaseMarks(marks) ;
				}
				else {
					wid.page.restart() ; 
					this.rank.insertEnd(wid.page) ;
					wid.page.increaseMarks(marks) ;  
				}
			}
		}
	}
	
	private boolean ifExists( PageInfo input ) throws WrongInput, EmptySmartArray {
		for (int i = 0 ; i < this.rank.getLength(); i++) {
			if (this.rank.ithElement(i) == input) {
				return true ; 
			}
		}
		return false ;
	}
	
	private static int pageRankAlgo(WordInfo input) {
		return input.wordcount ; 
	}
	
	
}
