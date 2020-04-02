package required;


import java.io.PrintStream;

public class DoublyLinkedList<V, K> {
		Node<V,K> head ; 
		int length ;  
		Node<V,K> tail ; 
	
	public DoublyLinkedList () {
		this.head = null ;
		this.length = 0 ;
		this.tail = null ;
	}
	
	public void add (V data, K key) {
		Node<V,K> cur = this.head ;		
		if (cur == null) {
			this.head = new Node<V,K> (null, data, key, null) ;  
			this.length = 1 ;   
			this.tail = this.head ; 
		}
		
		else {
			while (true) {
				if ( cur.key.equals(key) ) {
					cur.data = data; 
					break ; 
				}
				else if (cur.next == null) {
					cur.next = new Node<V,K>( cur, data , key , null) ;
					this.tail = cur.next ; 
					this.length = this.length + 1 ; 
					break ; 
				}			
				cur = cur.next ; 
			}
		}		
	}
	
	public V delete(K key) throws EmptyLinkedListException, KeyNotFoundException {
		Node<V,K> cur = this.head ; 
		if (cur == null) {
			throw new EmptyLinkedListException (null) ; 
		}
		else if ( ((cur.key).equals(key)) ) {
			this.head = cur.next ;
			if (this.head == null) {
				this.tail = null ;				
			}
			else {
				this.head.previous = null ;
			}
			this.length = this.length - 1 ; 
			return cur.data ; 
		}
		else {
			cur = cur.next ; 
			while (cur != null) {
				if (cur.key.equals(key)) {
					if ( cur.next != null ) {
						cur.previous.next = cur.next ;
						cur.next.previous = cur.previous ; 
						this.length = this.length - 1 ;
						return cur.data ; 
					}
					else {
						this.tail = cur.previous ; 
						cur.previous.next = null ;
						this.length = this.length - 1 ;
						return cur.data ;
					}
				}
				cur = cur.next ; 
			}		
			throw new KeyNotFoundException(null) ; 
		}
	}
	
	public V getData(K key) throws KeyNotFoundException, EmptyLinkedListException {
		Node<V,K> cur = this.head ; 
		if (cur == null) {
			throw new EmptyLinkedListException(null) ; 
		}
		else {
			while (cur != null) {
				if ( ((cur.key).equals(key)) ) {
					return cur.data ; 
				}
				cur = cur.next ; 
			}
		}
		throw new KeyNotFoundException(null) ;  
	}
	
	public boolean hasKey(K key) throws EmptyLinkedListException {
		Node<V,K> cur = this.head ; 
		if (cur == null) {
			throw new EmptyLinkedListException(null) ;   
		}
		else {
			while (cur != null) {
				if ( ((cur.key).equals(key)) ) {
					return true ; 
				}
				cur = cur.next ; 
			}
		}
		return false ;   
	}
	
	int length() {
		return this.length ; 
	}
	
	Node<V,K> giveHead() {
		return this.head ; 
	}
	
	Node<V, K> giveTail() {
		return this.tail ; 
	}
	
	void printall() {
		Node<V,K> cur = this.head ; 
		while ( cur != null ) {
			System.out.println(cur.data + " " + cur.key) ; 
			cur = cur.next ; 
		}
	}
	
	void printkey() {
		Node<V,K> cur = this.head ; 
		while ( cur != null ) {
			System.out.println(cur.key) ; 
			cur = cur.next ; 
		}
	}
	
	void printdata(PrintStream output) {
		Node<V,K> cur = this.head ;
			while ( cur != null ) {
				output.print(cur.data);
				output.print("\n");
				cur = cur.next ; 
			}		
	}
}
