import joos.lib.*;

public class LinkedListLinkedListInt{
	protected LinkedListInt stored;
	protected LinkedListLinkedListInt nextNode;
	
	public LinkedListLinkedListInt(){
		super();
		stored = null;
		nextNode = null;
	}
	public LinkedListLinkedListInt(LinkedListInt o){
		super();
		stored = o;
		nextNode = null;
	}
	
	//Appends to the end of the list
	public void addNode(LinkedListInt o){
		LinkedListLinkedListInt newLast;
		LinkedListLinkedListInt current;

		current = this;
		newLast = new LinkedListLinkedListInt(o);
		while(!(current.getNextNode() == null)){
			current = current.getNextNode();
		}
		current.setNextNode(newLast);
	}
	
	public LinkedListInt getLinkedListInt(){
		return stored;
	}
	public void setLinkedListInt(LinkedListInt o){
		stored = o;
	}
	
	
	public LinkedListLinkedListInt getNextNode(){
		return nextNode;
	}

	public void setNextNode(LinkedListLinkedListInt n) {
		nextNode = n;
	}
	
	//return null if Out of bounds
	public LinkedListLinkedListInt getNodeN(int n){
		int i;
		LinkedListLinkedListInt current;
		current = nextNode;
		for(i=0; i<=n; i++){
			if (current.getNextNode() == null) {
				i = n;
			}
			else {
				current = current.getNextNode();
			}
		}
		return current;
	}
	
}
