package SyntaxError;

public class FirstWordException extends SyntaxError {
	
	public FirstWordException() {
		massage += ": First word you entered is not valid";
	}

	private static final long serialVersionUID = 1L;
}
