package exceptions;
@SuppressWarnings("serial")
public class FinalRegistrationExceptions extends Exception {
	
	@Override
	public String getMessage() {
		return "Registration failed!";
	}
}