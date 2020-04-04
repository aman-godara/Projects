package col106assignment_2;
import java.util.Arrays ; 

public class StackSort {
	int[] target ;  
	
	
	public StackSort() {
		 
	}

	/*public static void main(String[] args) {	 
		StackSort obj1 = new StackSort () ;  
		int[] arr = new int[] {4,1,2,1,2,1,4,3,4} ;  
		obj1.sort(arr) ; 
	}*/

	
	public String[] sort(int[] nums) throws EmptyStackException {
		this.sortclone(nums);		
		MyStack<Integer> store = new MyStack<Integer>(); 
		
		String[] output = new String[(nums.length) * 2];
		int index_out = 0 ;
		
		int start_nums = 0 ; 
		
		outer_loop:
		for (int i = 0 ; i < this.target.length; i++) {
			int var1 = this.target[i] ;
			if ( store.isEmpty() == false ) {
				if (  store.top() == var1) {
				store.pop() ; 
				output[index_out] = "POP" ;
				index_out = index_out + 1 ; 
				continue outer_loop; 
				}
			}
			if ( start_nums >= nums.length) {
				String[] arr1 = new String[]{"NOTPOSSIBLE"} ; 
				return (arr1); 
				//System.out.println("NOTPOSSIBLE"); 
			}
			else {
				loop1:
				for (int j = start_nums ; j < nums.length; j++) {
					if ( nums[j] == var1) {
						output[index_out] = "PUSH";
						output[index_out + 1] = "POP";  
						index_out = index_out + 2 ; 
						start_nums = j + 1;
						break loop1 ; 
					}
					else if (j == nums.length - 1) {
						String[] arr1 = new String[]{"NOTPOSSIBLE"} ; 
						return (arr1); 
						//System.out.println("NOTPOSSIBLE"); 
					}
					else {
						store.push(nums[j]); 
						output[index_out] = "PUSH"; 
						index_out = index_out + 1 ;
					}
				}
			}
			
		}
		//print_arr(output) ; 
		return output ; 		 
	}
	
	
	private void sortclone(int[] nums) {
		this.target = nums.clone(); 
		Arrays.sort(this.target);
	}
	
	/*private static void print_arr (String[] arr ) {
		String var1 = "" ; 
		for (int i = 0 ; i < arr.length ; i++) {
			var1 = var1 + arr[i] + " " ;  
		}
		System.out.println(var1) ; 
	}*/
	
}
