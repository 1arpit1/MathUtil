package math.util.exceptions;

public class UnperformableActionException extends RuntimeException {

	private static final long serialVersionUID = 3763176540131208275L;

	public UnperformableActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnperformableActionException(String message) {
		super(message);
	}

	public UnperformableActionException(Throwable cause) {
		super(cause);
	}

}
