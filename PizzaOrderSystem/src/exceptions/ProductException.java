package exceptions;

@SuppressWarnings("serial")
public class ProductException extends Exception {

	@Override
	public String getMessage() {
		
		return "We don`t find product with that id! Sorry :(";
	}
}