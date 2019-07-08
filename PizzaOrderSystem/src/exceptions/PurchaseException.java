package exceptions;

@SuppressWarnings("serial")
public class PurchaseException extends Exception {
	
	@Override
	public String getMessage() {
		
		return "Unsuccessful purchase! :(";
	}
}