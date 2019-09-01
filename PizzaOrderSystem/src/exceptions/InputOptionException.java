package exceptions;
@SuppressWarnings("serial")
public class InputOptionException extends Exception {
	
	@Override
	public String toString() {
		return "Invalid option!";
	}
}