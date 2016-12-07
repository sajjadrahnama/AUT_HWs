package SyntaxError;

public class SelectException extends SyntaxError {

	private static final long serialVersionUID = 1L;

	public SelectException() {
		massage+=": Query you entered is not valid";
		
		}
	
}
