package answers;

public class Lab {

	public static void main(String[] args) {
		
		String t = "Foobar Candy|1|2";
		int li = t.lastIndexOf("|");
		String exportedPhrase = t.substring(0, t.indexOf("|"));
		int rest = exportedPhrase.length();
		String rr = t.substring(t.lastIndexOf("|")+1, t.length());
		
		
		
	}
	
	
}
