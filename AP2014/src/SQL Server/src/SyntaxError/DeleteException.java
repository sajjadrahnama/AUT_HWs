package SyntaxError;

public class DeleteException extends SyntaxError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -366094964558148991L;

	//private static final long serialVersionUID = 1L ;
	public DeleteException(){
		massage+=": Query you entered is not valid";
	}
	
}
