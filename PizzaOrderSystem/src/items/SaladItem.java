package items;

public class SaladItem extends Item{

	public SaladItem(String name, int count) {
		super(name, count);
	}
	
	public String toString() {
		
		return String.format("Salad: %s, Count: %d", this.getName(), this.getCount());
	}
}