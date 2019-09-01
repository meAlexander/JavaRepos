package exceptions;
@SuppressWarnings("serial")
public class OrderException extends Exception{
	
	@Override
	public String getMessage() {
		return "We don`t find order with that id!";
	}
}