package required;

public class StringRefiner {
	
	
	public static String refine(String line) {
		String refined_line = ""; 
		for (int i = 0 ; i < line.length() ; i++) {
			int p = (int)line.charAt(i) ; 
			if ( (p <= 90 && p >= 65) ) {
				refined_line = refined_line + (char)(p + 32);
			}
			else if ( (p <= 122 && p >= 97) ) {
				refined_line = refined_line + (char)(p) ; 
			}
		}
		return refined_line ; 
	}
	
	
}
