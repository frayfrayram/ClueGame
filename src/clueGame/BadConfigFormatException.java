package clueGame;

public class BadConfigFormatException extends RuntimeException {
	
	public BadConfigFormatException() {	
		super("Bad configuration file");
	}

	public BadConfigFormatException(String str) {
		super(str);
	}
}
