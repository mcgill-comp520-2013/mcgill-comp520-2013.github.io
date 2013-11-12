import joos.lib.*;

public class LinkedListPosition{
	protected Position stored;
	protected LinkedListPosition nextNode;
	
	public LinkedListPosition(){
		super();
		stored = null;
		nextNode = null;
	}
	public LinkedListPosition(Position o){
		super();
		stored = o;
		nextNode = null;
	}
	
	//Appends to the end of the list
	public void addNode(Position o){
		LinkedListPosition newLast;
		LinkedListPosition current;

		current = this;
		newLast = new LinkedListPosition(o);
		while(!(current.getNextNode() == null)){
			current = current.getNextNode();
		}
		current.setNextNode(newLast);
	}
	
	public Position getPosition(){
		return stored;
	}
	
	public LinkedListPosition getNextNode(){
		return nextNode;
	}

	public void setNextNode(LinkedListPosition n) {
		nextNode = n;
	}
}
