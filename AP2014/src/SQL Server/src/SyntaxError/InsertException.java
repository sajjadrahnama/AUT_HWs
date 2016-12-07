package SyntaxError;

public class InsertException extends SyntaxError {

	public InsertException() {
		massage +=": Query you entered is not valid";
	}

	private static final long serialVersionUID = 1L;

}
