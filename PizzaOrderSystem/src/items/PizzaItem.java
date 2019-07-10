package items;

public class PizzaItem extends Item {
	private String size;

	public PizzaItem(String name, int count, String size) {
		super(name, count);
		this.size = size;
	}

	public String toString() {

		return String.format("Pizza: %s, Size: %s, Count: %d", this.getName(), this.size, this.getCount());
	}
}