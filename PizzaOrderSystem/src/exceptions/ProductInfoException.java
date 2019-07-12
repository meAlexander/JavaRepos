package exceptions;

@SuppressWarnings("serial")
public class ProductInfoException extends Exception {

	@Override
	public String getMessage() {
		
		return "We don`t find product with that name! Sorry :(";
	}
}
