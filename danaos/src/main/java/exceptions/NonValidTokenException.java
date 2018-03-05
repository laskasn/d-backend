package exceptions;

public class NonValidTokenException extends Exception {

	private static final long serialVersionUID = -2834659827755141154L;
	
	public NonValidTokenException(String msg) {
		super(msg);
	}

}
