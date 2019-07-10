package items;

public class DrinkItem extends Item{
	private String brand;
	
	public DrinkItem(String name, int count, String brand) {
		super(name, count);
		this.brand = brand;
	}
	
	public String toString() {
		
		return String.format("Drink: %s, Brand: %s, Count: %d", this.getName(), this.brand, this.getCount());
	}
}