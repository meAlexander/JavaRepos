package exceptions;

@SuppressWarnings("serial")
public class AddNewUserExceptions extends Exception {
	
	@Override
	public String getMessage() {
		
		return "Failed to add a new user to the system! :(";
	}
}