package items;

public class SaladItem extends Item{
	private String ingrediens;
	
	public SaladItem(String name, int count, double price, String ingrediens) {
		super(name, count, price);
		this.ingrediens = ingrediens;
	}
	
	public SaladItem(String name, double price, String ingrediens) {
		this(name, 0, price, ingrediens);
	}
	
	public SaladItem(String name, int count) {
		this(name, count, 0, "NULL");
	}
	
	public SaladItem(String name) {
		this(name, 0, 0, "NULL");
	}
	
	public String getIngrediens() {
		return ingrediens;
	}

	public String toString() {
		
		return String.format("Salad: %s, Count: %d", this.getName(), this.getCount());
	}
}