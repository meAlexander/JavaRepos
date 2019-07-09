package exceptions;

@SuppressWarnings("serial")
public class AddUserExceptions extends Exception {
	
	@Override
	public String getMessage() {
		
		return "Failed to add a new user to the system! :(";
	}
}