package exceptions;

@SuppressWarnings("serial")
public class DeleteProductException extends Exception {
	
	@Override
	public String getMessage() {
		
		return "Failed to delete the product! :(";
	}
}