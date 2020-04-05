package main;

import java.lang.reflect.Array; 
import java.util.EmptyStackException;

public class CircularQueue<T> {
	T[] arr;
	int arr_length; 
	int cur_index ; 
	int head_index ;

	public static void main(String[] args) {
		CircularQueue<Integer> obj1 = new CircularQueue<Integer>(0) ; 
		System.out.println( obj1.isEmpty() ); 

	}
	
	@SuppressWarnings("unchecked")
	/*public MyQueue(Class<T> type) {
		this.arr = (T[])Array.newInstance(type , 0) ;
		this.arr_length = 0 ; 
		this.cur_index = -1 ;
		this.head_index = 0 ;*/
	
	public CircularQueue (int size) {
		this.arr = (T[]) new Object[size];
		this.arr_length = 0 ; 
		this.cur_index = -1 ;
		this.head_index = 0 ;
	}
	public void push (T value) {
		if ( ( this.head_index - this.cur_index ) == 1 || ( this.cur_index - this.head_index ) == this.arr_length - 1) {
			this.double_stack(null);
			this.arr[cur_index] = value ; 
			this.cur_index = ( (this.cur_index + 1 ) % (this.arr_length) ); 
		}
		else {
			
			this.arr[cur_index] = value ; 
			this.cur_index = ( (this.cur_index + 1 ) % (this.arr_length) ) ;				
		}
	}

	@SuppressWarnings("unchecked")
	private void double_stack(Class<T> type) {
		T[] newarr = (T[])Array.newInstance(type , ( 2 *(this.arr_length)) + 1 ) ;  
		int i = 0 ; 
		int j = this.head_index ; 
		while (i < this.arr_length) {
			newarr[i] = this.arr[j] ; 
			i = i + 1 ;
			j = ( (j + 1) % (this.arr_length) ); 
		}
		
		this.cur_index = i;
		this.arr = newarr ;
		this.head_index = 0 ;
		this.arr_length = (2 *(this.arr_length)) + 1 ;		
	}
	
	public T pop() throws EmptyStackException {
		if (this.head_index != this.cur_index && this.cur_index != -1) {
			int var1 = this.head_index; 
			this.head_index = ( this.head_index + 1 ) % this.arr_length ;
			return ( this.arr[var1] );
		}
		else {
			throw new EmptyStackException () ;  
		}
		  
		
	}
	
	
	public T top() throws EmptyStackException {
		if (this.head_index != this.cur_index && this.cur_index != -1) {
			return ( this.arr[this.head_index] );
		}
		else {
			throw new EmptyStackException () ;  
		}
		  
		
	}
	
	public boolean isEmpty() {
		return (this.head_index == this.cur_index || this.cur_index == -1) ; 
	}
	
	
	

}
