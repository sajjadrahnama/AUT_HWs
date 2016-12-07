package SyntaxError;

public class DropException extends SyntaxError {

	private static final long serialVersionUID = 1L;

	public DropException() {
		massage += ": Query you entered is not valid";
	}
	
}
