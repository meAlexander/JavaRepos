package exceptions;

@SuppressWarnings("serial")
public class AddProductException extends Exception {
	
	@Override
	public String getMessage() {
		
		return "Failed to add the product! :(";
	}
}