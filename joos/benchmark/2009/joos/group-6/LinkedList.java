import joos.lib.*;

public class LinkedList{
	protected Object stored;
	protected LinkedList nextNode;
	
	public LinkedList(){
		super();
		stored = null;
		nextNode = null;
	}
	public LinkedList(Object o){
		super();
		stored = o;
		nextNode = null;
	}
	
	//Appends to the end of the list
	public void addNode(Object o){
		LinkedList newLast;
		LinkedList current;

		current = this;
		newLast = new LinkedList(o);
		while(!(current.getNextNode() == null)){
			current = current.getNextNode();
		}
		current.setNextNode(newLast);
	}
	
	public Object getObject(){
		return stored;
	}
	
	public void setObject(Object o){
		stored = o;
	}
	
	public LinkedList getNextNode(){
		return nextNode;
	}

	public void setNextNode(LinkedList n) {
		nextNode = n;
	}
	//Starting from 0
	//Returns null if there is no nth element
	public LinkedList getNodeN(int n){
		int i;
		LinkedList current;
		current = null;
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
