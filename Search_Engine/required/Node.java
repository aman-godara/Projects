package required;

import main.SmartArray; ; 

public class Node<V> {
	
	public V value ; 
	public SmartArray<Node<V>> contains ;
	public SmartArray<WordInfo> end ; 
	
	public Node(V value, SmartArray<Node<V>> contains) {
		this.value = value ; 
		this.contains = contains ; 
		this.end = null ; 
	}
	
}