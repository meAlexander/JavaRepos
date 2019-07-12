package exceptions;

@SuppressWarnings("serial")
public class OrderStatusException extends Exception {
	
	@Override
	public String getMessage() {
		
		return "Updated status failed!";
	}
}