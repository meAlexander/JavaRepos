package items;

public class PizzaItem extends Item {
	private String size;
	private String ingredients;
	
	public PizzaItem(String name, int count, String ingredients, String size, double price) {
		super(name, count, price);
		this.size = size;
		this.ingredients = ingredients;
	}
	
	public PizzaItem(String name, String ingredients, String size, double price) {
		this(name, 0, ingredients, size, price);
	}
	
	public PizzaItem(String name, int count, String size) {
		this(name, count, "NULL", size, 0);
	}
	
	public PizzaItem(String name, String size) {
		this(name, 0, "NULL", size, 0);
	}
	
	public String getSize() {
		return size;
	}
	
	public String getIngredients() {
		return ingredients;
	}

	public String toString() {

		return String.format("Pizza: %s, Size: %s, Count: %d", this.getName(), this.size, this.getCount());
	}
}