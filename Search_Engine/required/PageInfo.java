package required;

public class PageInfo {
	public int words ; 
	//public int uniquewords ; 
	public String name ;
	public int marks ; 
	
	public PageInfo (String name) {
		this.words = 0 ;  
		this.name = name ; 
		this.marks = 0 ; 
	}
	
	public void restart () {
		this.marks = 0 ; 
	}
	
	public void increaseMarks(int input) {
		this.marks = this.marks + input ; 
	}
	
}

