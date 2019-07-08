package exceptions;
@SuppressWarnings("serial")
public class RegistrationException extends Exception{
	
	@Override
	public String getMessage() {
		
		return "Your password must contain a minimum of 8 characters, uppercase and lowercase letters, a number and special symbol!\nOr username is already used!"
				+ "\n“he email should be in this format: example@mail.bg/com";
	}
}