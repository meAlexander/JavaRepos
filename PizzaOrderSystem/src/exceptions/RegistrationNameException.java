package exceptions;
@SuppressWarnings("serial")
public class RegistrationNameException extends Exception{
	
	@Override
	public String getMessage() {
		return "Username is already used!";
	}
}