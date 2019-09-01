package exceptions;
@SuppressWarnings("serial")
public class RegistrationEmailException extends Exception{
	
	@Override
	public String getMessage() {
		return "Òhe email must be in this format: example@mail.bg/com or the email is already used";
	}
}