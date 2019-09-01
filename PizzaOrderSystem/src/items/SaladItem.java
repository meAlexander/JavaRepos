package items;

public class SaladItem extends Item{
	private int saladID;
	private String ingrediens;
	
	public SaladItem(int saladID, String name, int amount, double price, String ingrediens) {
		super(name, amount, price);
		this.saladID = saladID;
		this.ingrediens = ingrediens;
	}
	
	public SaladItem(String name, double price, String ingrediens) {
		this(0, name, 0, price, ingrediens);
	}
	
	public SaladItem(int saladID, int amount) {
		this(saladID, "NULL", amount, 0, "NULL");
	}
	
	public SaladItem(String name, int amount, double price) {
		this(0, name, amount, price, "NULL");
	}
	
	public SaladItem(int saladID) {
		this(saladID, "NULL", 0, 0, "NULL");
	}
	
	public String getIngrediens() {
		return ingrediens;
	}
	
	public int getSaladID() {
		return saladID;
	}

	public String toString() {
		
		return String.format("Salad: %s, Count: %d", this.getName(), this.getAmount());
	}
}