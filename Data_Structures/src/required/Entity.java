package required;

public class Entity<K, L> {
	public L value ;
	public K key ;
	
	public Entity (K key , L value ) {
		this.key = key ;
		this.value = value ; 
	}
}