package CompileError;

public class ParameterNumberException extends CompileError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterNumberException() {
		massage += " : Number of Parameter you enterd is not valid";
	}
	
}
