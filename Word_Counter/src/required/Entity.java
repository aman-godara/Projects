package required;

class Entity<K, L> {
	L value ;
	K key ;
	
	Entity (K key , L value ) {
		this.key = key ;
		this.value = value ; 
	}
}