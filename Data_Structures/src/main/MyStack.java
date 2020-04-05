package main;

//import java.lang.reflect.Array;
//import java.util.EmptyStackException;
import required.EmptyStackException ;


public class MyStack<T> {
	T[] arr ;
	int arr_length ; 
	int cur_index ; 

	/*public static void main(String[] args) {
		MyStack<Integer> obj1  = new MyStack<Integer> () ;
		//System.out.println(obj1.cur_index) ; 
		obj1.push(23) ; s
		//obj1.push(21) ;
		System.out.println(obj1.cur_index) ; 
		System.out.println(obj1.arr_length) ; 
		
		System.out.println(obj1.pop()) ;
		System.out.println(obj1.cur_index) ; 
		//System.out.println(obj1.pop(23)) ; 
		//System.out.println(obj1.cur_index) ; 
		//System.out.println(obj1.cur_index) ; 

	}*/
	
	@SuppressWarnings("unchecked")
	public MyStack() {
		this.arr = (T[]) new Object[0] ;  // (T[])Array.newInstance(type , 0) ; 
		this.arr_length = 0 ; 
		this.cur_index = 0 ;
	}
	
	
	public void push(T value) {
		if (cur_index <= arr_length - 1){
			this.arr[cur_index] = value ;
			this.cur_index = this.cur_index + 1 ;  
			}
		else {
			this.double_stack() ; 
			this.arr[cur_index] = value ;
			this.cur_index = this.cur_index + 1 ;
			}
		}
	
	@SuppressWarnings("unchecked")
	private void double_stack () {
		T[] newarr = (T[]) new Object[( 2 *(this.arr_length) ) + 1] ; //(T[])Array.newInstance(type , ( 2 *(this.arr_length) ) + 1 ) ;
		for (int i = 0 ; i < this.arr_length; i++ ) {
			newarr[i] = this.arr[i] ; 
			}
		this.arr = newarr ; 
		this.arr_length = ( 2 *(this.arr_length) ) + 1 ;  
	}
	
	public T pop() throws EmptyStackException {
		if ( this.cur_index == 0 ) {
			throw new EmptyStackException () ; 
		}
		else {
			this.cur_index = this.cur_index - 1 ; 
			return this.arr[cur_index] ; 
		}
	}
	
	public T top() throws EmptyStackException {
		if ( this.cur_index == 0 ) {
			throw new EmptyStackException () ; 
		}
		else { 
			return this.arr[cur_index - 1] ; 
		}
	}
	
	public boolean isEmpty() throws EmptyStackException {
		return ( this.cur_index == 0 ) ;
	}
	
	/*public void print_stk () throws EmptyStackException {
		String var1 = "" ;
		while (this.isEmpty() == false) {
			var1 = var1 + " " + this.pop() ; 
		}
		System.out.println(var1) ;	
	}*/
		
		
}

