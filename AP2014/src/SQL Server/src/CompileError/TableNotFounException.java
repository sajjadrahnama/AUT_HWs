package CompileError;

public class TableNotFounException extends CompileError {

	public TableNotFounException() {
		super();
		massage += " : Table not found";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
