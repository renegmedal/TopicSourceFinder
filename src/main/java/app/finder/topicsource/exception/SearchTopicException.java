package app.finder.topicsource.exception;

public class SearchTopicException extends RuntimeException { 
	/**
	 * 
	 */
	private static final long serialVersionUID = -807287619526132358L;

	public SearchTopicException() {	}

	public SearchTopicException(String message) {
		super(message);
		 
	}
	public SearchTopicException(Throwable cause) {
		super(cause);
		 
	}

	 

}
