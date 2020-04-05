package dataStructures;


public class LinkedHashMap<V,K> {
	DoublyLinkedList<V,K>[] map ;
	int length ; 
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap (int size) {
		this.map = new DoublyLinkedList[size];
		this.length = size ;
	}
	
	public static void main(String[] args ) {
		LinkedHashMap<Integer,String> obj1 = new LinkedHashMap<Integer,String>(128) ;
		System.out.println(obj1.convert("ahmad")) ;
		System.out.println(obj1.hashFunc("ahmad")) ; 
	}
	
	public void add(V data, K key){
		int org_place = this.hashFunc(key) ;
		if ( this.map[org_place] == null ) {
			this.map[org_place] = new DoublyLinkedList<V,K>() ; 
			(this.map[org_place]).add(data, key) ;
		}
		else {
			(this.map[org_place]).add(data, key) ;
		}
	} 
	
	public V getData(K key) throws KeyNotFoundException, EmptyLinkedListException {
		int org_place = this.hashFunc(key) ;
		V temp ; 
		try {
			temp =  (this.map[org_place]).getData(key) ;
		} catch ( NullPointerException e ) {
			throw new EmptyLinkedListException(null) ; 
		}
		return ( temp ) ;
	}
	
	public V delete(K key) throws KeyNotFoundException, EmptyLinkedListException {
		int org_place = this.hashFunc(key) ; 
		V temp ;
		try {
			temp = (this.map[org_place]).delete(key) ; 
		} catch ( NullPointerException e ) {
			throw new EmptyLinkedListException(null) ; 
		}
		return temp ;
	}
	
	public boolean hasKey(K key) {
		int org_place = this.hashFunc(key) ;
		boolean temp ;
		try {
			temp = this.map[org_place].hasKey(key) ; 
		} catch (Exception e) {
			return false ;  
		}
		return temp ; 
	}
	
	public void printall(K key) {
		int org_place = this.hashFunc(key) ;
		if ( this.map[org_place] != null) {
			this.map[org_place].printall();
		} 
	}
	
	public void printkey(K key) {
		int org_place = this.hashFunc(key) ;
		if ( this.map[org_place] != null) {
			this.map[org_place].printkey();
		} 
	}
	
	public void printdata(K key) {
		int org_place = this.hashFunc(key) ;
		if ( this.map[org_place] != null) {
			this.map[org_place].printdata();
		} 
	}
	
	int hashFunc (K key) {
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
	}
	
}
