package main;

public class Heap<T extends Comparable, E extends Comparable> {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		/*HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());*/
	}
	/*
	 * end code
	 */
	
	// write your code here	
	E[] heap_values ;
	T[] heap_keys ;
	int length ;
	int next_index = 0 ; 
	
	public Heap () {
		this.heap_values = (E[]) new Comparable[15] ; 
		this.heap_keys = (T[]) new Comparable[15] ; 
		this.length = 15 ; 
		this.next_index = 0 ;  
	}
	public void insert(T key, E value) {
		//write your code here
		if ( this.next_index > this.length - 1 ) {
			E[] arr1 = (E[]) new Comparable[2*(this.length) + 1] ; 
			T[] arr2 = (T[]) new Comparable[2*(this.length) + 1] ; 
			
			for (int i = 0 ; i < this.length; i++) {
				arr1[i] = this.heap_values[i] ;
				arr2[i] = this.heap_keys[i] ;
			}
			this.heap_values = arr1 ; 
			this.heap_keys = arr2 ; 
			this.length = 2*(this.length) + 1 ; 
		}
		
		this.heap_values[this.next_index] = value;
		this.heap_keys[this.next_index] = key; 
		int j = this.next_index; 
		while ( j > 0 ) {
			if (j % 2 == 0) {
				if ( (this.heap_values[((j-2)/2)]).compareTo(this.heap_values[j]) < 0 ) {
					E var1_value = this.heap_values[ ((j-2)/2) ] ; 
					T var1_key = this.heap_keys[ ((j-2)/2) ] ; 
					this.heap_values[ ((j-2)/2) ] = this.heap_values[j] ; 
					this.heap_keys[ ((j-2)/2) ] = this.heap_keys[j] ; 
					this.heap_values[j] = var1_value ; 
					this.heap_keys[j] = var1_key ; 
					j = ((j-2)/2) ; 
				}
				else {
					break; 
				}
			}
			else if (j % 2 != 0) {
				if ( (this.heap_values[((j-1)/2)]).compareTo(this.heap_values[j]) < 0 ) {
					E var1_value = this.heap_values[ ((j-1)/2) ] ; 
					T var1_key = this.heap_keys[ ((j-1)/2) ] ; 
					this.heap_values[ ((j-1)/2) ] = this.heap_values[j] ; 
					this.heap_keys[ ((j-1)/2) ] = this.heap_keys[j] ; 
					this.heap_values[j] = var1_value ; 
					this.heap_keys[j] = var1_key ; 
					j = ((j-1)/2) ; 
				}
				else {
					break; 
				}
			}
		}
		this.next_index = this.next_index  + 1 ;   
	}

	public E extractMax() {
		//write your code here
		if (this.next_index > 0) {
			E var1 = this.heap_values[0];  
			this.delete( this.heap_keys[0] ) ; 
			return var1 ; 
		}
		else {
			return null;
		}
	}

	public void delete(T key) {
		//write your code here
		for (int i = 0 ; i < this.next_index ; i++) {
			if (this.heap_keys[i].compareTo(key) == 0 ) {
				this.heap_keys[i] = this.heap_keys[this.next_index - 1] ; 
				this.heap_values[i] = this.heap_values[this.next_index - 1];
				this.next_index = this.next_index - 1 ;  
				this.percolateDown(i) ;
				break ; 
			}
		}		
	}

	public void increaseKey(T key, E value) {
		//write your code here
		for ( int i = 0 ; i < this.next_index ; i++ ) {
			if (this.heap_keys[i].compareTo(key) == 0 ) {
				this.heap_values[i] = value ; 
				int j = i; 
				while ( j > 0 ) {
					if (j % 2 == 0) {
						if ( (this.heap_values[((j-2)/2)]).compareTo(this.heap_values[j]) < 0 ) {
							E var1_value = this.heap_values[ ((j-2)/2) ] ; 
							T var1_key = this.heap_keys[ ((j-2)/2) ] ; 
							this.heap_values[ ((j-2)/2) ] = this.heap_values[j] ; 
							this.heap_keys[ ((j-2)/2) ] = this.heap_keys[j] ; 
							this.heap_values[j] = var1_value ; 
							this.heap_keys[j] = var1_key ; 
							j = ((j-2)/2) ; 
						}
						else {
							break; 
						}
					}
					else if (j % 2 != 0) {
						if ( (this.heap_values[((j-1)/2)]).compareTo(this.heap_values[j]) < 0 ) {
							E var1_value = this.heap_values[ ((j-1)/2) ] ; 
							T var1_key = this.heap_keys[ ((j-1)/2) ] ; 
							this.heap_values[ ((j-1)/2) ] = this.heap_values[j] ; 
							this.heap_keys[ ((j-1)/2) ] = this.heap_keys[j] ; 
							this.heap_values[j] = var1_value ; 
							this.heap_keys[j] = var1_key ; 
							j = ((j-1)/2) ; 
						}
						else {
							break; 
						}
					}
				}
				break ; 
			}
		}
	}

	public void printHeap() {
		//write your code here
		for (int i = 0 ; i < this.next_index; i++) {
			System.out.println( this.heap_keys[i] + ", " + this.heap_values[i] ) ; 
		}
	}
	
	/*private void delete1(int j) {
		int i = j ; 
		while ( true ) {
			if ( 2*i + 2 <= this.next_index - 1 ) {
				if ( this.heap_values[(2*i + 1)].compareTo(this.heap_values[2*i + 2]) < 0 ) {
					this.heap_values[i] = this.heap_values[2*i + 2] ; 
					i = 2*i + 2 ; 
				}
				else {
					this.heap_values[i] = this.heap_values[2*i + 1] ; 
					i = 2*i + 1 ; 
				}
			}
			else if ( 2*i + 1 <= this.next_index - 1 ) {
				
			}
			
		}
	}*/
	
	private void percolateDown(int j) {
		int i = j ; 
		while ( true ) {
			if ( 2*i + 2 <= this.next_index - 1 ) {
				if ( this.heap_values[i].compareTo(this.heap_values[2*i + 1]) < 0 || this.heap_values[i].compareTo(this.heap_values[2*i + 2]) < 0 ) {
					if ( this.heap_values[(2*i + 1)].compareTo(this.heap_values[2*i + 2]) < 0 ) {
						E var1_value = this.heap_values[i] ;
						T var1_key = this.heap_keys[i] ;
						this.heap_values[i] = this.heap_values[2*i + 2] ; 
						this.heap_keys[i] = this.heap_keys[2*i + 2] ; 
						this.heap_values[2*i + 2] = var1_value;
						this.heap_keys[2*i + 2] = var1_key;
						i = 2*i + 2 ; 
					}
					else {
						E var1_value = this.heap_values[i] ;
						T var1_key = this.heap_keys[i] ;
						this.heap_values[i] = this.heap_values[2*i + 1] ; 
						this.heap_keys[i] = this.heap_keys[2*i + 1] ; 
						this.heap_values[2*i + 1] = var1_value;
						this.heap_keys[2*i + 1] = var1_key;
						i = 2*i + 1 ; 
					}
				}
				else {
					break ; 
				}
			}
			else if ( 2*i + 1 <= this.next_index - 1 ) {
				if ( this.heap_values[i].compareTo(this.heap_values[2*i + 1]) < 0 ) {
					E var1_value = this.heap_values[i] ;
					T var1_key = this.heap_keys[i] ;
					this.heap_values[i] = this.heap_values[2*i + 1] ; 
					this.heap_keys[i] = this.heap_keys[2*i + 1] ; 
					this.heap_values[2*i + 1] = var1_value;
					this.heap_keys[2*i + 1] = var1_key;
					i = 2*i + 1 ;
				}
				else {
					break; 	
				}
			}
			else {
				break ; 
			}
		}
	}
	
}
