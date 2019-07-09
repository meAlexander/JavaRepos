package exceptions;

@SuppressWarnings("serial")
public class BuyProductException extends Exception {

	@Override
	public String getMessage() {
		
		return "Failed to buy the product! Sorry :(";
	}
}
