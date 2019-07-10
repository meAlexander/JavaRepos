package exceptions;

@SuppressWarnings("serial")
public class UpdateOrderStatusException extends Exception{
	
	@Override
	public String getMessage() {
		
		return "Failed to update order status! :(";
	}
}
