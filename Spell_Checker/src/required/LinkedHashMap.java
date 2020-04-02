package required;

import java.io.PrintStream;

//import main.SpellChecker;

public class LinkedHashMap<V,K> {
	private DoublyLinkedList<V,K>[] map ;
	@SuppressWarnings("unused")
	private int length ; 
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap (int size) {
		this.map = new DoublyLinkedList[size];
		this.length = size ;
	}
	
	
	public void add(V data, K key, int org_place){
		if ( this.map[org_place] == null ) {
			this.map[org_place] = new DoublyLinkedList<V,K>() ; 
			(this.map[org_place]).add(data, key) ;
		}
		else {
			(this.map[org_place]).add(data, key) ;
		}
	} 
	
	public V getData(K key, int org_place) throws KeyNotFoundException, EmptyLinkedListException {
		V temp ; 
		try {
			temp =  (this.map[org_place]).getData(key) ;
		} catch ( NullPointerException e ) {
			throw new EmptyLinkedListException(null) ; 
		}
		return ( temp ) ;
	}
	
	public V delete(K key, int org_place) throws KeyNotFoundException, EmptyLinkedListException {
		V temp ;
		try {
			temp = (this.map[org_place]).delete(key) ; 
		} catch ( NullPointerException e ) {
			throw new EmptyLinkedListException(null) ; 
		}
		return temp ;
	}
	
	public boolean hasKey(K key, int org_place) {
		boolean temp ;
		try {
			temp = this.map[org_place].hasKey(key) ; 
		} catch (Exception e) {
			return false ;  
		}
		return temp ; 
	}
	
	public void printall(int org_place) {
		if ( this.map[org_place] != null) {
			this.map[org_place].printall();
		} 
	}
	
	public void printkey(int org_place) {
		if ( this.map[org_place] != null) {
			this.map[org_place].printkey();
		} 
	}
	
	public void printdata(PrintStream output, int org_place) {
		if ( this.map[org_place] != null) {
			this.map[org_place].printdata(output);
		} 
	}
	
	public void printlength (PrintStream output) {
		for (int i = 0 ; i < this.length ; i++) {
			if (this.map[i] == null ) {
				output.println(0) ;
			}
			else {
				output.println(this.map[i].length) ;
			}
		}
	}
	
}
	

	
	/*int hashFunc (K key) {
		int out = 0 ;
		String inpt = this.convert(key) ; 
		for (int i = 0 ; i < inpt.length(); i++) {
			out = out + (int)Math.pow( 2, 96 + this.log2() - (int)(inpt.charAt(i)) ) ;
		}
		return out ; 
	}
	
	String convert(K key) { 
		String inpt = (String) key ;
		String out = "" ; 
		loop1:
		for (int j = 1 ; j <= this.log2() ; j++) {
			char a = (char)(96 + j) ; 
			for (int i = 0 ; i < inpt.length() ; i++) {
				if (inpt.charAt(i) == a) {
					out = out + a ;
					continue loop1 ; 
				}
			}
		}
		return out ;
	}
	
	int log2() {
		int ans = 0 ; 
		int a = this.length ; 
		if (a <= 0 ) {
			return -1 ; 
		}
		else {
			while (a > 1) {
				a = a/2 ;
				ans = ans + 1 ; 
			}
			if (ans < 27) {
				return ans ;
			}
			return -1 ; 
		}
	}*/
	
	/*private void halo (int a) {
		int[] arr = new int[a] ;
		int start = 0 ;
		int end = 25 ; 
		int c = 0 ;
		int b = 0 ;   
		for (int i = 0 ; i < a ; i++) {
			c = c + (start + i) ;
			b = b + (end - i) ; 
			int length = b - c + 1 ; 
			arr[i] = length ; 
			//System.out.println(arr[i]) ;
		}
		int tu = -1 ;
		for (int i = 0 ; i < arr.length ; i++) {
			
			if (i == 0 ) {
				arr[i] = arr[i] + tu ;
			}
			else if (i == 1) {
				arr[i] = arr[i]*2 + tu ;
			}
			else {
				arr[i] = arr[i]*3 + tu ;
			}
			tu = arr[i] ; 
			System.out.println(tu) ; 
		}
		
	}*/
	
	/*private int halo3(int inpt) {
		int[] start = new int[]{0} ; 
		int[] end = new int[]{0} ; 
		int org = (inpt - (inpt%3))/3 ; 
		for (int i = 0 ; i < org; i++) {
			start[0] = start[0] + 3*(0 + i) ; 
			end[0] = end[0] + 3*(25 - i) ; 
		}
		start[0] = start[0] + (inpt%3)*(0 + org ) ; 
		end[0] = end[0] + (inpt%3)*(25 - org ) ;
		System.out.println(end[0] - start[0] + 1) ;
		return (end[0] - start[0] + 1) ; 
	}*/
	
	/*private int[] endPoints (int input) {
		int[] out = new int[input + 1] ;
		int initiate = -1 ; 
		for (int i = 0 ; i <= input; i++) {
			int length = this.lengthCalc(i) ; 
			out[i] = initiate + length ; 
			initiate = out[i] ; 
			System.out.println(out[i]) ;
		}
		return out ;
	}*/
	
	
	/*private int stl(String str) throws Exception {
		int std = (pointers(15))[str.length()][0] ; 
		int length = (int) ( ( Math.round( (double)(25 * str.length())/4) ) + 1 ) ;
		System.out.println(std) ; 
		System.out.println(length) ; 
		int a = 0 ; 
		for (int i = 0 ; i < str.length(); i++) {
			a = a + weight1(str.charAt(i)) ; 
		}
		System.out.println(a) ; 
		a = (int)( Math.round((double)a/4) ) + 1; 
		System.out.println(a) ; 
		if (isOddEven(length) == -1) {
			int median = (length + 1)/2 ; 
			if (a <= median + 1) {
				std = std + 2*(a-1)*(a-1) ; 
			}
			else {
				std = std + 2*median*median + 2*(median - 1)*(median - 1) - 2*(2*median - a)*(2*median - a); 
			}
		}
		else {
			double median = (double)(length + 1)/2 ;
			if ( a <= Math.round(median) ) {
				std = std + 2*(a-1)*(a-1) ; 
			}
			else {
				std = (int) (std + 2*(Math.round(median - 1))*(Math.round(median - 1)) + 2*(Math.round(median - 1))*(Math.round(median - 1)) - 2*(2*median - a)*(2*median - a)) ;
			}
			
		}
		System.out.println(std) ; 
		//std = std + ((a-1)*(a-1)) + 1 ; 
		//int l_length = (2*a) - 1 ;
		int l_length ;
		if (isOddEven(length) == -1) {
			int median = (length + 1)/2 ; 
			if (a <= median) {
				l_length = 2*(2*a - 1) ;
			}
			else {
				l_length =  (2*( 2*(2 * median - a) - 1 )) ;
			}
		}
		else {
			double median = (double)(length + 1)/2 ;
			if (a < median) {
				l_length = 2*(2*a - 1) ; 
			}
			else {
				l_length = (int) ( 2*( 2*(2 * median - a) - 1) ); 
			}
		}
		a = 0 ; 
		for (int i = 0 ; i < str.length() ; i++) {
			a = a + weight2( str.charAt(i) ) ; 
		}
		System.out.println(std) ; 
		System.out.println(a) ; 
		System.out.println(l_length) ; 
		System.out.println( std + (a % l_length) ) ; 
		return (std + (a % l_length)) ; 
	}*/
	
	
	/*private static int isOddEven(int input) {
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
	}*/
		
	//public static void main(String[] args ) throws Exception {
		//LinkedHashMap<Integer,String> obj1 = new LinkedHashMap<Integer,String>(128) ;
		/*System.out.println(obj1.convert("ahmad")) ;
		System.out.println(obj1.hashFunc("ahmad")) ; */
		//obj1.halo(10) ;  
		//obj1.markers(12) ; 
		//obj1.endPoints(12) ;
		//obj1.stl("zoodendrium") ;  
		//obj1.hashFunc("zzz") ;  
	//}


