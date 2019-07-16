package exceptions;

@SuppressWarnings("serial")
public class OrderException extends Exception{
	
	@Override
	public String getMessage() {
		
		return "Order not accepted!";
	}
}