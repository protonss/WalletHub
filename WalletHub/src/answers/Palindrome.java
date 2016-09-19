package answers;

/**
 * I created two methods for basically two reasons 
 * 1) To make my code more easily testable 
 * 2) To ensure decoupling
 * 
 * I'm throwing Exception for null string in both methods because:
 * 1) I need to validate string size in isPalindrome method
 * 2) reverse method is supposed to be used elsewhere   
 * 
 * @author Anselmo Ribeiro
 *
 */
public class Palindrome {

	public boolean isPalindrome(final String str) throws Exception {
		if(null==str)
			throw new Exception("The string cannot be null");

		if(str.length()<=1)
			return false;
		
		return str.equals(this.reverse(str));
	}

	public String reverse(final String str) throws Exception {
		if(null==str)
			throw new Exception("The string cannot be null");
		
		int len = str.length();
		char[] ca = new char[len];

		// loop over the half string since the symmetrical elements switch places
		for (int i = 0; i < (int) (len + 1) / 2; i++) {
			ca[i] = str.charAt(len - (i + 1));
			ca[len - (i + 1)] = str.charAt(i);
		}

		return new String(ca);
	}

}
