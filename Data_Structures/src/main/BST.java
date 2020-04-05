package main;


@SuppressWarnings("rawtypes")
public class BST<T extends Comparable, E extends Comparable> {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main(String[] args) {
		/*BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());*/
		
		/*BST obj1 = new BST() ; 
		obj1.insert(1 , 15);
		obj1.insert(2 , 5);
		obj1.insert(3 , 1);
		obj1.insert(4 , 6);
		obj1.insert(5 , 20);
		obj1.insert(6 , 16);
		obj1.insert(7 , 25);
		obj1.insert(8 , 21);
		obj1.insert(9 , 26);
		obj1.insert(10 , 23);
		obj1.insert(11, 2);
		//obj1.insert(12 , 16);
		//obj1.insert(13 , 19);
		//obj1.insert(14 , 21);
		//obj1.insert(15 , 26);
		//obj1.insert(16 , 24);
		//obj1.delete(1); 
		obj1.update(10, 50);
		obj1.update(5, 100);
		obj1.delete(8);
		obj1.delete(1); 
		//obj1.update(13 , 22);
		obj1.printBST () ;*/
		}
	/*
	 * end code
	 * start writing your code from here
	 */
	
	//write your code here 
		
	BST left_t  ;
	BST  right_t ; 
	E value ;
	T key ; 
	
	public BST () {
		left_t = null ;
		right_t  = null ; 
		E value = null ;
		T key = null ;  
	}
	
    @SuppressWarnings({ "unchecked" })
	public void insert(T key, E value) {
		//write your code here
    	if ( this.value != null ) {
    		if (value.compareTo(this.value) < 0 ) { 
	    		if (this.left_t == null ) {
	    			BST var1 = new BST() ;
	    			var1.value = value ;
	    			var1.key = key ; 
	    			this.left_t = var1 ; 
	    		}
	    		else {
	    			this.left_t.insert( key , value) ; 
	    		}  		
	    	}
	    		else if ( value.compareTo(this.value) > 0 ) {
		    		if (this.right_t == null ) {
		    			BST var1 = new BST() ;
		    			var1.value = value ;
		    			var1.key = key ;
		    			this.right_t = var1 ; 
		    		}
		    		else {
		    			this.right_t.insert( key , value) ; 
		    		}
	    		}
    		}
    	else {
    		this.value = value ;
    		this.key = key  ;
    	}    	
    }

    public void update(T key, E value) {
		//write your code here 
    	this.delete (key) ;
    	this.insert(key, value) ;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void delete(T key) {
		//write your code here
    	BST[] arr1 = new BST[]{null ,null} ; 
    	this.find_key(key , null , arr1) ; 
    	
    	BST var2 = null; 
    	if (arr1[0].left_t == null && arr1[0].right_t == null) {
    		if ( arr1[1] == null) {
    			arr1[0].value = null ;
    			arr1[0].key = null ;
    		}
    		else if (arr1[1].right_t == arr1[0]) {
    			arr1[1].right_t = null ; 
    		}
    		else {
    			arr1[1].left_t = null ; 
    		}
    	}
    	
    	else if (arr1[0].right_t != null) {
    		var2 = arr1[0].right_t.successor(arr1[0])  ;
    		arr1[0].value = var2.value ; 
        	arr1[0].key = var2.key;
    	} 
    	
    	else if (arr1[0].left_t != null) {
    		var2 = arr1[0].left_t.predecessor(arr1[0])  ; 
    		arr1[0].value = var2.value ; 
        	arr1[0].key = var2.key;
    	}
    	
    }

    public void printBST () {
		//write your code here    	
    	//String str ;
    	if (this.value != null) {
    		//str = String.valueOf(this.key) + ", " + String.valueOf(this.value)+ "\n"  ;
    		System.out.println( this.key + ", " + this.value) ; 
    	}
    	else {
    		//str = "" ;
    		System.out.print("") ;  
    	}
    	
    	Printer obj1 = new Printer(this) ;  
    	
    	while (obj1.print_arr_len > 0) {
    		//str = str + obj1.printBST_1(str);	
    		obj1.printBST_1() ; 
    	}
    	
    	//System.out.print(str) ;     	  
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void find_key ( T key , BST inpt, BST[] result) {
    	if (this.key.compareTo(key) == 0 ) {
    		result[0] = this ;
    		result[1] = inpt ; 
    	}
    	else if (this.left_t != null && this.right_t != null ) {
    		this.left_t.find_key(key, this, result) ;
    		this.right_t.find_key(key, this, result) ; 
    		
    	}
    	else if (this.left_t != null ) {
    		this.left_t.find_key(key, this, result) ;
    		
    	}
    	else if (this.right_t != null ){
    		this.right_t.find_key(key, this, result) ;
    	} 
    	else {    		
    	}
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private BST predecessor (BST inpt) {
    	BST obj_c = this ; 
    	BST obj_p = inpt ; 
    	int a = 0 ; 
    	while( obj_c.right_t != null ) {
    		a = 1 ; 
    		obj_p = obj_c ; 
    		obj_c = obj_c.right_t ; 
    		
    	}
    	if (a == 1 ) {
    		obj_p.right_t = obj_c.left_t ; 
    	}
    	else {
    		obj_p.left_t = obj_c.left_t ; 
    	}
    	
    	obj_c.right_t = null ; 
    	obj_c.left_t = null ;
    	
    	return obj_c ; 
    	
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private BST successor (BST inpt) {
    	BST obj_c = this ;
    	BST obj_p = inpt ; 
    	int a = 0 ; 
    	while ( obj_c.left_t != null ) {
    		a = 1 ; 
    		obj_p = obj_c ; 
    		obj_c = obj_c.left_t ; 
    	}
    	if (a == 1 ) {
    		obj_p.left_t = obj_c.right_t ;  
    	}
    	else {
    		obj_p.right_t = obj_c.right_t ;
    	}
    	return obj_c ; 
    }    
    
    private class Printer {
    	BST[] print_arr ;
    	int print_arr_len ;  
    	
    	private Printer (BST inpt) {
    		this.print_arr = new BST[] {inpt} ; 
    		print_arr_len = 1 ; 
    	}
    	
    	@SuppressWarnings("rawtypes")
    	private void printBST_1 () {
        	BST[] arr1 = new BST[2 * this.print_arr_len] ;
        	int j = 0 ; 
        	int i = 0 ; 
        	while (i < this.print_arr_len) {
        		if (this.print_arr[i].left_t != null && this.print_arr[i].right_t != null) {
        			//str = str + String.valueOf(this.print_arr[i].left_t.key) + ", "+ String.valueOf(this.print_arr[i].left_t.value)+ "\n" ; 
        			//str = str + String.valueOf(this.print_arr[i].right_t.key) + ", "+ String.valueOf(this.print_arr[i].right_t.value)+ "\n" ;
        			System.out.println(this.print_arr[i].left_t.key + ", " + this.print_arr[i].left_t.value); 
        			System.out.println(this.print_arr[i].right_t.key + ", " + this.print_arr[i].right_t.value); 
        			arr1[j] = this.print_arr[i].left_t ;
        			arr1[j+1] = this.print_arr[i].right_t ;
        			j = j + 2 ;
        			i = i + 1 ; 
        		}
        		else if (this.print_arr[i].left_t != null) {
        			//str = str + String.valueOf(this.print_arr[i].left_t.key) + ", "+ String.valueOf(this.print_arr[i].left_t.value)+ "\n" ;
        			System.out.println(this.print_arr[i].left_t.key + ", " + this.print_arr[i].left_t.value);
        			arr1[j] = this.print_arr[i].left_t ;
        			j = j + 1 ;
        			i = i + 1 ; 
        		}
        		else if (this.print_arr[i].right_t != null) {
        			//str = str + String.valueOf(this.print_arr[i].right_t.key) + ", "+ String.valueOf(this.print_arr[i].right_t.value)+ "\n" ;
        			System.out.println(this.print_arr[i].right_t.key + ", " + this.print_arr[i].right_t.value);
        			arr1[j] = this.print_arr[i].right_t ;
        			j = j + 1 ;
        			i = i + 1 ; 
        		}
        		else {
        			i = i + 1 ;  
        		}        		
        	}
        	this.print_arr_len = j ; 
        	this.print_arr = arr1; 
        	//return str ; 
        	
        }        
    }
}

