package exceptions;

@SuppressWarnings("serial")
public class UserExistsException extends Exception{
	
	@Override
	public String getMessage() {
		
		return "User exists!";
	}
	
}