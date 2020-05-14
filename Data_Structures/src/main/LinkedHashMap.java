package main;

import required.EmptyLinkedListException;
import required.KeyNotFoundException;

@SuppressWarnings("unused")
public class LinkedHashMap<V,K> {
	private DoublyLinkedList<V,K>[] map ;
	private int length ; 
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap (int size) {
		this.map = new DoublyLinkedList[size];
		this.length = size ;
	}
	
	public static void main(String[] args ) {
		LinkedHashMap<Integer,String> obj1 = new LinkedHashMap<Integer,String>(128) ;
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
	
	void printall(K key) {
		int org_place = this.hashFunc(key) ;
		if ( this.map[org_place] != null) {
			this.map[org_place].printall();
		} 
	}
	
	void printkey(K key) {
		int org_place = this.hashFunc(key) ;
		if ( this.map[org_place] != null) {
			this.map[org_place].printkey();
		} 
	}
	
	void printdata(K key) {
		int org_place = this.hashFunc(key) ;
		if ( this.map[org_place] != null) {
			this.map[org_place].printdata();
		} 
	}
	
	private int hashFunc (K key) {
		String str = (String) key ; 
		int a = 41 ; 
		int ans = 0 ; 
		for (int i = str.length() - 1; i >= 0; i--) {
			ans = ( (int)str.charAt(i) ) + ( ( a * ans ) % this.length ) ;  
		}
		return ( ans % this.length ) ;		
	}
	
	
}
