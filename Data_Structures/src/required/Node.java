package required;

public class Node<A,B> {
	public Node<A,B> next ;
	public A data ; 
	public B key ; 
	public Node<A,B> previous ;
	
	public Node(Node<A,B> n , A data, B key, Node<A,B> m) {
		this.data = data ;
		this.key = key ; 
		this.previous = n ;			
		this.next = m ;			
	}
	
}