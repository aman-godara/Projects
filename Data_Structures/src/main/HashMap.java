package main;
import java.util.Vector;
import required.Entity ; 

public class HashMap<V> {	
	private Entity<String, V>[] map ;
	private int length ; 
	
	/*public static void main (String[] args ) { 
		HashMap<Integer> obj1 = new HashMap<Integer>(8) ;
		System.out.println( obj1.put("a", 87) ) ;
		System.out.println( obj1.put("b", 1) ) ;
		System.out.println( obj1.put("e", 764) ) ;
		System.out.println( obj1.put("j", 83) ) ;
		System.out.println( obj1.put("i", 12) ) ;
		System.out.println( obj1.put("z", 12) ) ;
		System.out.println( obj1.put("m", 12) ) ;
		System.out.println( obj1.put("r", 12) ) ;
		//obj1.printer(); 
		System.out.println( obj1.remove("i") ) ;
		obj1.printer(); 
	}*/	
	

	@SuppressWarnings("unchecked")
	public HashMap(int size) {
		this.map = new Entity[size] ; 
		this.length = size ; 
	}
	
	public V put(String key, V value){
		int org_place = this.hashFunc(41, key) ; 
		for ( int i = 0 ; i < this.length ; i++ ) {
			int cur_place = (org_place + i) % this.length ; 
			if ( this.map[ cur_place ] == null) {
				this.map[cur_place] = new Entity<String, V>(key, value) ; 
				return null ;	
			}
			else if ( key.equals((this.map[cur_place]).key)) { 
				V temp = (this.map[cur_place]).value ;
				(this.map[cur_place]).value = value ; 
				return temp ; 
			}
		}
		return null ;
	}

	public V get(String key){
		int org_place = this.hashFunc(41, key) ;  
		for ( int i = 0 ; i < this.length ; i++ ) {
			int cur_place = (org_place + i) % this.length ; 
			if ( this.map[ cur_place ] == null) { 
				return null ;	
			}
			else if ( key.equals((this.map[cur_place]).key)) { 
				return (this.map[cur_place]).value ; 	 
			}
		}
		return null;
	}

	public boolean remove(String key) {
		int org_place = this.hashFunc(41 ,key) ; 
		for ( int i = 0 ; i < this.length ; i++ ) {
			int cur_place = ( org_place + i) % this.length ; 
			if ( this.map[cur_place] == null ) {
				return false ; 
			}
			else {
				if ( key.equals((this.map[cur_place]).key) ) { 
					this.delete(cur_place) ; 
					return true ;
				}				
			}
		}
		return false;
	}
	
	private void delete ( int inpt ) {
		this.map[inpt] = null ;
		int last = inpt ;  
		
		for ( int i = 1 ; i < this.length ; i++ ) {
			int cur_place = (inpt + i) % this.length;   
			if ( this.map[ cur_place ] == null ) {
				break; 
			}
			else if ( displaced( this.hashFunc(41, this.map[ cur_place ].key ) , cur_place , last ) ) {
					this.map[last] = this.map[ cur_place ] ; 
					this.map[ cur_place ] = null ;
					last = cur_place ; 
			}
		}
	}

	public boolean contains(String key){
		int org_place = this.hashFunc(41, key) ;  
		for ( int i = 0 ; i < this.length ; i++ ) {
			int cur_place = (org_place + i) % this.length ; 
			if ( this.map[ cur_place ] == null) { 
				return false;	
			}
			else if ( key.equals((this.map[cur_place]).key)) { 
				return true ; 	 
			}
		}
		return false;
	}

	public Vector<String> getKeysInOrder(){
		Vector<String> vec1 = new Vector<String>() ;
		for (int i = 0 ; i < this.length; i++) {
			if ( this.map[i] == null) {
				continue; 
			}
			else {
				vec1.add( (this.map[i]).key ) ; 
			}
		}
		return vec1;
	}
	
	/*public void printer () { 
		for (int i = 0 ; i < this.length ; i++) {
			if (this.map[i] == null) {
				System.out.print( "##"  + "   " + "##" + "\n");
			}
			else {
				System.out.print( this.map[i].key + "   " + this.map[i].value + "\n");
			}
		}
	}*/
	
	private int hashFunc (int a , String str) {
		int ans = 0 ; 
		for (int i = str.length() - 1; i >= 0; i--) {
			ans = ( (int)str.charAt(i) ) + ( ( a * ans ) % this.length ) ;  
		}
		return ( ans % this.length ) ;		
	}
	
	private static boolean displaced (int org_place , int cur_place , int del_place ) {
		if (org_place == cur_place ) {
			return false ;
		}
		else if (org_place == del_place ) {
			return true ;
		}
		else if ( cur_place < org_place && org_place < del_place) { 
			return true ; 
		}
		else if (org_place < del_place && del_place < cur_place) {
			return true ; 
		}
		else if ( del_place < cur_place && cur_place < org_place ) {
			return true ;
		}
		else {
			return false ; 
		}
		
	}
	
}
