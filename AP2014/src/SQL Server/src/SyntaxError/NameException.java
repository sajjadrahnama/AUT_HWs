package SyntaxError;

public class NameException extends SyntaxError {
	
	private static final long serialVersionUID = 1L;
	public NameException() {
		massage += ": The name you entered is not right";
	}

}
