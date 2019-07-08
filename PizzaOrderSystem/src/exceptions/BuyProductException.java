package exceptions;

@SuppressWarnings("serial")
public class BuyProductException extends Exception {

	@Override
	public String getMessage() {
		
		return "Don`t offer such a product! Sorry :(";
	}
}
