package col106assignment_2;

public class MyCalculator {
	MyStack<String> perm ;  
	MyStack<String> temp ; 
	int check ; 
	
	public MyCalculator() {
		this.perm = new MyStack<String> () ; 
		this.temp = new MyStack<String> () ; 
		this.check = 0 ; 
	}
	
	
	/*public static void main(String[] args) {
		MyCalculator obj1 = new MyCalculator () ; 
		System.out.println( obj1.calculate("2 +(5 -7 )") );
	}*/
	


	private void mult_string (String str) throws EmptyStackException {
		//MyStack<String> cal_stack = new MyStack<String>();  
		//String var1 = "" ;  
		if ( this.check == 1 ) {
				//this.temp.pop() ;
				String var2 = this.temp.pop() ;
				this.temp.push( String.valueOf( (Integer.parseInt(var2)) * (Integer.parseInt(str)) ) ) ;
				this.check = 0 ; 
			}		
		else {
			if (str.equals("*")) {
				this.check = 1 ;			 
			}
			else {
			this.temp.push(str) ;
			}
		}
	}
	
	
	private void addsubs_string (String str) throws EmptyStackException{
		//MyStack<String> cal_stack = new MyStack<String>();  
		//String var1 = "" ; 
		//if ( stk.isEmpty() == false ) {
		if ( this.check == 1 ) {
			//this.perm.pop() ;
			String var2 = this.perm.pop() ;
			this.perm.push( String.valueOf( (Integer.parseInt(var2)) + (Integer.parseInt(str)) ) ) ;
			this.check = 0 ;
		}
		else if (this.check == -1) {
			//this.perm.pop() ;
			String var2 = this.perm.pop() ;
			this.perm.push( String.valueOf( (Integer.parseInt(var2)) - (Integer.parseInt(str)) ) ) ;
			this.check = 0 ; 
		}
		else {
			if (str.equals("+")){
				this.check = 1; 
			}
			else if (str.equals("-")) {
				this.check = -1 ; 
			}
			else {
			this.perm.push(str) ; 
			}
		}
	}
	
	public int calculate(String str) throws EmptyStackException {
		this.check = 0 ; 
		String mod_str = "(" + str + ")" ; 
		String store1 = "" ; 
		for (int i = 0 ; i < mod_str.length() ; i++) {
			if ( mod_str.charAt(i) == '(' ) {
				this.perm.push("(");
			}
			else if ( mod_str.charAt(i) == ')' ) {
				if (store1 != "") {
					this.perm.push(store1) ;
					store1 = "" ;
				}				 
				String var1 = this.perm.pop() ; 
				while ( !(var1.equals("(")) ) {
					this.mult_string(var1) ; 
					var1 = this.perm.pop() ; 
				}
								
				while (this.temp.isEmpty() == false) {
					this.addsubs_string(this.temp.pop()) ; 
				}
			}
			else if (mod_str.charAt(i) == ' ') {
				if (store1 != "") {
					this.perm.push(store1) ;
					store1 = "" ;
				}				 
			}
			else if (mod_str.charAt(i) == '+' || mod_str.charAt(i) == '-' ||  mod_str.charAt(i) == '*'){
				if (store1 != "") {
					this.perm.push(store1) ;
					store1 = "" ;
				}
				this.perm.push(Character.toString(mod_str.charAt(i)));
			}
			else {
				store1 = store1 + Character.toString(mod_str.charAt(i)) ; 
			}			
		}
		//	System.out.println(this.check) ; 
		return (Integer.parseInt(this.perm.pop())) ;
		
		//this.perm.print_stk (); 
	}
	
	
	
}
