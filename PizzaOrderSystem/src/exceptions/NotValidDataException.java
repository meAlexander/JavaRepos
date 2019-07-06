package exceptions;
@SuppressWarnings("serial")
public class NotValidDataException extends Exception{
	
	@Override
	public String getMessage() {
		
		return "Invalid data!";
	}
}