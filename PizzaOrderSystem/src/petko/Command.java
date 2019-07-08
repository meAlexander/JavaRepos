package petko;

public interface Command {
	
	public Command execute(Command parent);
	
}