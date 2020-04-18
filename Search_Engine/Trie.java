package main;


import required.EmptySmartArray;
import required.Node ;
import required.WrongInput;
import required.Output;
import required.PageInfo;
import required.StringRefiner;
import required.WordInfo; 

public class Trie {
	public Node<String> top ;  
	
	public static void main(String[] args) throws Exception {
		Trie obj1 = new Trie() ; 
		//obj1.fillRefData("src/main/", 1, ".txt") ;
		System.out.println(obj1.getValue("Re eSe")) ; 
	}
	
	public Trie () {
		this.top = new Node<String>( "$", null ) ;  
	}
	
	
	
	
	public void insert (String input, PageInfo link) throws WrongInput, EmptySmartArray {
		String temp = input ; 
		Node<String> current = this.top ;
		
		while (temp != "") {
			Output hm = Trie.halfMatch(current.contains, temp) ;
			if ( hm.insertnew ) {
				System.out.println( temp + " " + hm.location ); 
				Node<String> newobj = new Node<String> (temp , null) ; 
				if (current.contains  == null) {
					current.contains = new SmartArray<Node<String>> () ;
				}
				current.contains.insertBetween(newobj, hm.location);
				current = newobj ; 
				temp = "" ; 
			}
			else { 
				if (hm.fullmatch) {
					System.out.println(hm.match + " " + hm.location) ;
					current = current.contains.ithElement(hm.location) ; 
					temp = cutter(temp, hm.match) ; 
				}
				else {
					System.out.println(hm.match + " " + hm.location) ;
					Node<String> now = current.contains.ithElement(hm.location); 
					now.value = cutter(now.value, hm.match) ; 
					
					Node<String> newobj = new Node<String> (hm.match, new SmartArray<Node<String>> ()) ;
					newobj.contains.insertEnd(now) ;
					current.contains.updateIndex(newobj, hm.location) ; 
					temp = cutter(temp, hm.match) ;
					current = newobj ; 
				}
			}
		}		
		if (current.end == null) {
			current.end = new SmartArray<WordInfo>() ;
			WordInfo obj1 = new WordInfo (link) ; 
			obj1.wordcount = 1 ; 
			current.end.insertEnd(obj1) ;
		}
		else {
			if ( current.end.lastElement().page == link ) {
				current.end.lastElement().wordcount += 1 ;
			}
			else {
				WordInfo obj1 = new WordInfo (link) ; 
				obj1.wordcount = 1 ; 
				current.end.insertEnd(obj1) ;
			}
		}
	}
	
	private static Output halfMatch(SmartArray<Node<String>> strarr, String str) throws WrongInput, EmptySmartArray {
		Output out ; 
		if (strarr == null) {
			out = new Output(true, 0, null, false) ;
			return out ; 
		}
		else {
			out = new Output(true, strarr.curindex, null, false) ;
			
			loop1:
			for (int i = 0 ; i < strarr.curindex ; i++) {
				String base = (strarr.ithElement(i)).value ; 
						
				if (str.charAt(0) > base.charAt(0)) {
					continue ; 
				}
				else if (str.charAt(0) == base.charAt(0)) {
					out.insertnew = false ;
					out.location = i ;
					
					out.match = "" ;
					loop2:
					for ( int j = 0 ; j < Math.min(base.length(), str.length()); j++ ) {
						if ( base.charAt(j) == str.charAt(j) ) {
							out.match = out.match + Character.toString(base.charAt(j)) ;   
						}
						else {
							break loop2 ;  
						}
					}
					if (out.match.equals(base)) {
						out.fullmatch = true ; 
					}
					break loop1 ; 
				}
				else {
					out.location = i ; 
					break loop1 ; 
				}
			}
		}
		return out ; 
	}
	
	
	private static String cutter (String str, String cut) {
		String temp = "" ;
		for (int i = cut.length() ; i < str.length(); i++) {
			temp = temp + Character.toString(str.charAt(i)) ;
		}
		return temp ; 
	}
	
	public SmartArray<WordInfo> getValue (String input) throws WrongInput, EmptySmartArray {
		Node<String> current = this.top ;
		String temp = StringRefiner.refine(input) ;
		
		while (temp != "") {
			Output hm = Trie.halfMatch(current.contains, temp) ;  
			if (hm.fullmatch) {
				current = current.contains.ithElement(hm.location);
				temp = cutter(temp, hm.match) ; 
			}
			else {
				return null ; 
			}
		}
		return current.end ; 
	}
	
	
	/*public boolean hasKey (String input, String link) throws EmptySmartArray, WrongInput {
		Node<String> current = this.top ;
		String temp = StringRefiner.refine(input) ;
		
		while (temp != "") {
			Output hm = Trie.halfMatch(current.contains, temp) ;  
			if (hm.fullmatch) {
				current = current.contains.ithElement(hm.location);
				temp = cutter(temp, hm.match) ; 
			}
			else {
				return false ; 
			}
		}
		if (current.end == null) {
			return false; 
		}
		else {
			if ( current.end.lastElement().page == link ) {
				return true ; 
			}
			else {
				return false ; 
			}
		}
	}*/

}
