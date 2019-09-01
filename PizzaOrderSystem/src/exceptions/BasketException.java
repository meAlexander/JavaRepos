package exceptions;
@SuppressWarnings("serial")
public class BasketException extends Exception{
	
	@Override
	public String getMessage() {
		return "Basket is empty!";
	}
}