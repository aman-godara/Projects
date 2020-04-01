package required;

class Node<A,B> {
	Node<A,B> next ;
	A data ; 
	B key ; 
	Node<A,B> previous ;
	
	Node(Node<A,B> n , A data, B key, Node<A,B> m) {
		this.data = data ;
		this.key = key ; 
		this.previous = n ;			
		this.next = m ;			
	}
	
}