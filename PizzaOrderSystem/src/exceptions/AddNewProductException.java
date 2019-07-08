package exceptions;

@SuppressWarnings("serial")
public class AddNewProductException extends Exception {
	
	@Override
	public String getMessage() {
		
		return "Failed to add the product! :(";
	}
}