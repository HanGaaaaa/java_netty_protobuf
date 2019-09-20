package kinghua.protodemo;

public class GameException extends RuntimeException {
	private static final long serialVersionUID = -8429532974568597326L;
	public GameException() {
		super("");
	}
	public GameException(String msg) {
		super(msg);
	}
	
	
	
}
