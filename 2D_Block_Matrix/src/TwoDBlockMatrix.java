package col106assignment_1;
import java.util.* ; 
import java.text.DecimalFormat ; 

public class TwoDBlockMatrix {
	float[][] data;
	
	
		
	/*public static void main(String[] args) {
	TwoDBlockMatrix var2;
	try {
		var2 = buildTwoDBlockMatrix(args);
		var2.toString1() ;
		var2.toString() ;
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} 
	
	}*/
	private static String rounder ( float x ) {
		DecimalFormat format2dec = new DecimalFormat("0.00");
		return format2dec.format(x) ; 
	}
	
	public static TwoDBlockMatrix buildTwoDBlockMatrix (java.io.InputStream in) {
		SubBlockMatrix matrix = new SubBlockMatrix ();
		//try File file1 = new File("D:\\Files for input\\assignment_1.txt") ; 
		Scanner scan1 = new Scanner (in);
		while (scan1.hasNextLine()) {
			matrix.creator (scan1.nextLine() + "T") ;
		}
		 
		/*try { File file_obj = new File (loc);
		InputStream in = new FileInputStream (file_obj);
		
		
		} catch (Exception e) {
		e.printStackTrace(); 
		} */
		
		scan1.close();
		float[][] var1 = matrix.to_array () ; 
		return (new TwoDBlockMatrix (var1)); 
		} 
	
		public TwoDBlockMatrix (float[][] array) {
		this.data = array ;		
		}

		
	public TwoDBlockMatrix transpose (){
		int length_i = (this.data).length ;
		int length_j = ((this.data)[0]).length ; 
		TwoDBlockMatrix out_matrix = new TwoDBlockMatrix (new float[length_j][length_i]);
		for (int a = 0; a < length_i; a++) {
			for (int b = 0 ; b < length_j; b++) {
				out_matrix.data[b][a] = (this.data)[a][b]; 
			}
		}
		return out_matrix ; 
		}
	
	public TwoDBlockMatrix multiply (TwoDBlockMatrix other) throws IncompatibleDimensionException {
		int length_i_this = (this.data).length ;
		int length_j_this = ((this.data)[0]).length ; // try and exception for Wrong Input ???
		int length_i_other = (other.data).length ;
		int length_j_other = ((other.data)[0]).length ;	
		
		if (length_j_this != length_i_other) {
			throw new IncompatibleDimensionException("Matrices Cannot Be Multiplied") ; }
		
		TwoDBlockMatrix out_matrix = new TwoDBlockMatrix (new float[length_i_this][length_j_other]); 
		for (int a = 0 ; a < length_i_this; a++) {
			for (int b = 0; b < length_j_other ; b++) {
				float var1 = 0.0f ; 
				for (int c = 0 ; c < length_i_other; c++) {
					var1 = var1 + (this.data[a][c] * other.data[c][b]) ;   					
				}
				out_matrix.data[a][b] = var1 ;
				var1 = 0.0f; 				
			}			
		}
		return out_matrix ; }
	
	public TwoDBlockMatrix getSubBlock(int row_start, int col_start, int row_end, int col_end) throws SubBlockNotFoundException {
		int length_i = row_end - row_start ; 
		int length_j = col_end - col_start ; // try and exception for Wrong Input??
		
		try {
		TwoDBlockMatrix out_matrix = new TwoDBlockMatrix (new float[length_i][length_j]);
		int fr = 0;  
		for (int a = row_start - 1; a < row_end - 1 ; a++) {
			int dr = 0;
			for (int b = col_start - 1  ; b < col_end -1 ; b++) {
				out_matrix.data[fr][dr] = this.data[a][b]; 
				dr = dr + 1; }
			fr = fr + 1;			
		}
		return out_matrix ; }
		catch (Exception excp ) {
			throw new SubBlockNotFoundException("Sub Block Not Found") ; 
		}
	}
	
	public TwoDBlockMatrix inverse() throws InverseDoesNotExistException {
		int length_i = (this.data).length ;
		int length_j = (this.data[0]).length ; // try and exception ?? for Wrong Input
		
		TwoDBlockMatrix out_matrix = new TwoDBlockMatrix (new float[length_i][length_j]);
		out_matrix.identity_c() ;
		
		if (length_i != length_j) {
			throw new InverseDoesNotExistException ("Sorry, It Is Not A Square Matrix") ; 
		}
		
		this.real_inverse(out_matrix); 
		

		this.identity_checker() ; 
		return out_matrix ; 
	}
	
	private void real_inverse (TwoDBlockMatrix out_matrix) throws InverseDoesNotExistException {
		int length_i = (this.data).length ;
		int length_j = (this.data[0]).length ; // try and exception ?? for Wrong Input
		
		for (int i = 0 ; i < length_i ; i++ ) {
			if (this.data[i][i] == 0) {
				for (int j = i ; j < length_i ; j++) {
					if (this.data[j][i] != 0) {
						this.swap_row(i, j) ;
						out_matrix.swap_row(i, j);
						break; 
					}
				}
			}
			
			try {
				float var1 = this.data[i][i];  
				float var2 = out_matrix.data[i][i] ; 
				for (int j = 0 ; j < length_j ; j++) {
					this.data[i][j] = (this.data[i][j] / var1 );  
					out_matrix.data[i][j] = (out_matrix.data[i][j] / var2 );  
				}
			} catch (Exception e) {
				throw new InverseDoesNotExistException ("Sorry, No Division by Zero Possible") ;
			}
			
			if (i < (length_i) ) {
				float[] var1 = this.data[i] ; 
				float[]  var2 = out_matrix.data[i] ;  
				for (int a = 0 ; a < length_i ; a++ ) {
					if (a != i) { 
					float var3 = this.data[a][i]; 
					float var4 = out_matrix.data[a][i]; 
					for (int j = 0 ; j < length_j ; j++) {
						this.data[a][j] = this.data[a][j] - ( var3 * (var1[j]) );
						out_matrix.data[a][j] = out_matrix.data[a][j] - ( var4 * (var2[j]) );
					}
					
					
					
			}
			}
				out_matrix.toString1();
			}
		}
		//out_matrix.toString1(); 		
	}
	
	private void swap_row (int a , int b) {
		float[] var1 = this.data[a] ; 
		float[] var2 = this.data[b] ;
		this.data[a] = var2 ;
		this.data[b] = var1 ;
	}
	
	private void identity_c () {
		for ( int i = 0 ; i < this.data.length ; i++ ) {
			this.data[i][i] = 1.0f ; 
		}
	}

	public void toString1 () {
		for (int i = 0 ; i < this.data.length ; i++) {
			for (int j = 0 ; j < this.data[0].length ; j++) {
				System.out.print(this.data[i][j] + " "); 
			}
		System.out.println() ; 
		}
	}
	
	private void identity_checker() throws InverseDoesNotExistException {
		for (int i = 0 ; i < this.data.length ; i++) {
			for (int j = 0 ; j < this.data[0].length ; j++) {
				if (i == j && this.data[i][j] != 1.0f) {
					throw new InverseDoesNotExistException ("Sorry, Inverse Does Not Exist"); 
				}
				else if (i != j && this.data[i][j] != 0.0f  ) {
					throw new InverseDoesNotExistException ("Sorry, Inverse Does Not Exist");
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public String toString () {
		String  final_str = "" ;  
		
		loop1:
		for (int i = 0 ; i < this.data.length ; i++) {
			int var2 = -1 ;
			//boolean vac_var2 = true ;
			//boolean vac_var3 = true ; 
			int var3 = -1 ; 
			loop2:
				for (int j = 0 ; j < this.data[0].length ; j++) {
				if ( this.data[i][j] != 0 && var2 == -1) {
					var2 = j ; }
					//vac_var2 = false ;}
				else if ( (this.data[i][j] == 0 && var3 == -1 && var2 != -1) ) {
					var3 = j ; }
				if ( this.data[i][j] != 0 && j == this.data[0].length - 1 && var2 != -1){
					var3 = j + 1 ; 
				}
					//vac_var2 = false ;}
				
				if (var3 != -1) {
					final_str = final_str  + (String.valueOf(i + 1) + " " + String.valueOf(var2 + 1) + " \n" );
					loop4:
						for (int a = i ; a < this.data.length; a++) {
						String var1 = "" ;
						loop5:							
							for (int b = var2 ; b < var3 ; b++) {								 
								float var4 = this.data[a][b] ;  
								if ( var4 != 0.0f ) {
									if (var1 == "" ) {
									var1 = rounder (var4) ; }
									else {
										var1 = var1 + " " + rounder(var4) ; 
									}
								}
								else {
									break loop4;  
								}
								}
						for (int b = var2 ; b < var3 ; b++) {
							this.data[a][b] = 0.0f ; 
						}
				final_str = final_str  + (var1 + ";") + "\n";
				}
				final_str = final_str + ("#\n");
				var2 = -1 ;
				var3 = -1 ; 
				}
			}
		}
		return final_str ; 
	}
	}

class SubBlockMatrix{
	List<List<Float>> data ; 
	int[] location; 
	int size_i ; 
	int size_j; 
		
	
	SubBlockMatrix (){
		this.data = new ArrayList<List<Float>>();
		this.data.add( new ArrayList<Float>() ) ; 
		this.location = new int[] {1 ,1} ;
		this.size_i = this.data.size(); 
		this.size_j = (this.data.get(0)).size();
		 
	}
	
	void creator (String str) {
		//List<List<Float>> out_alist = new ArrayList<List<Float>> (); 
		List<Float> temp_alist = new ArrayList<Float>();
		String temp_str = "" ;  
		loop1:
		for (int a = 0 ; a < str.length(); a++) {
			if (str.charAt(a) == ';') {
				if (temp_str != "") {
				temp_alist.add(Float.parseFloat(temp_str)) ;
				}
				
				this.addie(temp_alist);
				this.location[0] = this.location[0] + 1 ; 
				break loop1; 				
			}
			else if (str.charAt(a) == ' ' ) {
				if (temp_str != "") {
					temp_alist.add(Float.parseFloat(temp_str)) ;
					temp_str = "" ; 
				}
			}
			else if (str.charAt(a) == 'T') {
				if (temp_str != "") {
					temp_alist.add(Float.parseFloat(temp_str)) ;
				}				
			this.location[0] = Math.round((temp_alist.get(0))); 
			this.location[1] = Math.round((temp_alist.get(1)));
			}
			else if (str.charAt(a) == '#') {
				break loop1; 
			}
			else {
				temp_str = temp_str + str.charAt(a);
			}
		}
	}

	void addie (List<Float> add_it ) {
		
		//float matrix_obj_i = (float)this.data.size();  
	    //float matrix_obj_j = (float)(this.data.get(0)).size();
		int i = 0 ; 
		int var1= this.location[1] ; 
		while (i < add_it.size()) {
			if ( this.location[0] <=  this.size_i && this.location[1] <= this.size_j) {
			(this.data.get( (this.location[0]) - 1 )).set( ((this.location[1]) - 1) , add_it.get(i)); 
			this.location[1] = this.location[1] + 1 ; 
			i = i + 1 ; 
			}
			else  {
				this.expand( checker (this.location[0] ,this.size_i ), checker (this.location[1] , this.size_j) ) ;
				}
			 
		}
		this.location[1] = var1 ;
	}
	static int checker (int a , int b ) {
		if (a - b > 0) {
			return a - b; 
		}
		else {
			return 0 ;
		}
	}
	
	
	void expand (int exp_i , int exp_j ) {
		for (int a = 0; a < exp_j ; a++ ) {
			for (int b = 0 ; b < this.size_i; b++) {
			(this.data.get(b)).add(0.0f);
			}
		}
		
		this.size_j = this.size_j + exp_j ; 
		
		for (int c = 0 ; c < exp_i ; c++) {
			List<Float> var1 = new ArrayList<Float>() ; 
			for ( int d = 0 ; d < this.size_j; d++) {
				var1.add(0.0f) ; 
			}
			this.data.add(var1) ; 	
			}
		
		this.size_i = this.size_i + exp_i ;  
		}	
	
	
	float[][] to_array () {
		float[][] var1 = new float[this.size_i][this.size_j];  
		for (int i = 0 ; i < this.size_i; i++) {
			for (int j = 0 ; j < this.size_j ; j++) {
				var1[i][j] = ((this.data).get(i)).get(j)  ;
			}	
		}
		return (var1); 
	}
		
	}

	@SuppressWarnings("serial")
	class IncompatibleDimensionException extends Exception {
		public IncompatibleDimensionException (String msg) {
			super(msg) ; 
		}
	}

	@SuppressWarnings("serial")
	class SubBlockNotFoundException extends Exception {
		public SubBlockNotFoundException (String msg) {
			super(msg) ; 
		}
	}
	@SuppressWarnings("serial")
	class InverseDoesNotExistException extends Exception {
		public InverseDoesNotExistException (String msg) {
			super(msg) ; 
		}
	}
	
