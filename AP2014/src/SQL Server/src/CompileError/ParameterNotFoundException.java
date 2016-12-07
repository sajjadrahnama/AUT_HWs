package CompileError;

public class ParameterNotFoundException extends CompileError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterNotFoundException() {
		massage += " : Parameter not found";
	}
	
}
