package main;



public class Smart_Array<V> {
	
	private V[] data  ; 
	private int curindex ; 
	
	@SuppressWarnings("unchecked")
	public Smart_Array () {
	this.data = (V[]) new Object[1] ; 
	this.curindex = 0 ;
	
	}

		
	public static void main(String[] args ) throws EmptySmartArray, WrongInput {
		Smart_Array<Integer> obj1  = new Smart_Array<Integer>(); 
		obj1.printSoArray();
		obj1.insertEnd(12);
		obj1.printSoArray();
		obj1.deleteEnd() ; 
		obj1.printSoArray();
		obj1.insertEnd(10);
		obj1.insertEnd(6);
		obj1.insertEnd(5);
		obj1.insertEnd(7);
		obj1.insertEnd(12);
		obj1.printSoArray();
		obj1.deleteEnd() ; 
		obj1.printSoArray();
		System.out.println(obj1.lastElement()) ; 
		obj1.insertBetween(999, 2);
		obj1.updateIndex(90, 2) ; 
		obj1.printSoArray();
		System.out.println(obj1.lastElement()) ; 
		/*obj1.print_soarray () ;
		System.out.println(obj1.data.length) ;
		System.out.println("---") ;
		
		obj1.delete_end();
		obj1.delete_end();
		obj1.delete_end();
		obj1.delete_end();
		obj1.delete_end();
		obj1.print_soarray () ;
		System.out.println(obj1.data.length) ; 
		System.out.println("---") ;
		
		obj1.insert_end(22);
		obj1.print_soarray();	
		System.out.println("---") ;
		obj1.delete_end();
		obj1.delete_end();*/
	}


	public void insertEnd (V a) { 
		while ( this.curindex > (this.data.length - 1) ) {
			this.doubleSize () ;
		}		
		this.data[this.curindex] = a ;  
		this.curindex = (this.curindex) + 1 ; 
	}

	
	public V updateIndex (V a, int b) throws WrongInput, EmptySmartArray {
		if ( b >= this.curindex || b < 0 ) {
			if ( this.curindex > 0 ) {
				throw new WrongInput("") ; 			
			}
			else {
				throw new EmptySmartArray("") ;
			}  
		}
		
		V temp = this.data[b] ; 
		this.data[b] = a ; 
		return temp ; 
	}	
	
	private void reArrange (int b) {
		for (int i = b; i < this.curindex - 1; i++) {
			this.data[b] = this.data[b+1] ;  
		}
		this.curindex = this.curindex - 1 ;
	}
	
	
	@SuppressWarnings("unchecked")
	private void doubleSize () {
		V[] arr = (V[]) new Object[ 2 * this.data.length ] ; 	
		for (int i = 0 ; i < this.data.length; i++){
			arr[i] = this.data[i] ; 	
		}
		this.data = arr; 
	}

	
	@SuppressWarnings("unchecked")
	public V deleteEnd() throws EmptySmartArray {
		if (this.curindex > 0) { 
			V temp = this.data[curindex - 1] ;  
			this.curindex = this.curindex - 1 ; 
			if ( curindex + 1 <= ( this.data.length / 2 ) ) {
				V[] arr = (V[]) new Object[this.data.length / 2] ; 
				for (int i = 0 ; i < curindex; i++) {
					arr[i] = this.data[i] ; 
				}
				this.data = arr ; 		
			}
			return temp ; 
		}
		else {
			throw new EmptySmartArray("") ; 
		}	
	}
	
	
	@SuppressWarnings("unchecked")
	public V deleteIndex(int b) throws EmptySmartArray, WrongInput {
		if ( b >= this.curindex || b < 0 ) {
			if (this.curindex > 0) {
				throw new WrongInput("") ;						
			}
			else {
				throw new EmptySmartArray("") ; 
			}
		}
		else { 
			V temp = this.data[b] ;
			this.reArrange(b); 
			if ( curindex + 1 <= ( this.data.length / 2 ) ) {
				V[] arr = (V[]) new Object[curindex + 1] ; 
				for (int i = 0 ; i < curindex; i++) {
					arr[i] = this.data[i] ; 
				}
				this.data = arr ; 		
			}
			return temp ; 
		}	
	}
	
	
	public void insertBetween (V a, int b) throws WrongInput, EmptySmartArray {
		if ( b > this.curindex || b < 0 ) {
			if (this.curindex > 0) {
				throw new WrongInput("") ;						
			}
			else {
				throw new EmptySmartArray("") ; 
			}
		}
		else {
			if (this.curindex == 0 ) {
				this.insertEnd(a);
			}
			else {
				this.insertEnd(this.data[this.curindex - 1]);
				for (int i = this.curindex - 2 ; i > b ; i--) {
					this.data[i] = this.data[i - 1] ; 
				}
				this.data[b] = a ; 
			}
		}
	}

	
	public void printSoArray () {
	String str = "" ;
	for (int i = 0 ; i < this.data.length ; i++) {
		str = str + ( this.data[i] + " " ) ;
	}
		if (str != ""){
			System.out.println("str is: " + str) ;
		}
		else {
			System.out.println("Empty Smart_Array") ;
		}
	System.out.println("length of the Array is: " + this.data.length) ;
	System.out.println("next insertion will be at index: " + this.curindex) ;
	System.out.println("------------") ; 
	}
	
	public V lastElement () throws EmptySmartArray {
		if (this.curindex > 0) {
			return this.data[this.curindex - 1] ; 
		}
		else {
			throw new EmptySmartArray("") ; 
		}
	}


}

@SuppressWarnings("serial")
class EmptySmartArray extends Exception {
	
	EmptySmartArray (String msg) {
		super(msg) ; 
	}
}

@SuppressWarnings("serial")
class WrongInput extends Exception {
	
	WrongInput (String msg) {
		super(msg) ; 
	}
}