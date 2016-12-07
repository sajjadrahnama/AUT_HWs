package SyntaxError;

public class UpdateException extends SyntaxError {

	private static final long serialVersionUID = 1L;

	public UpdateException() {
		massage += ": Query you entered is not valid";
	}
	
}
