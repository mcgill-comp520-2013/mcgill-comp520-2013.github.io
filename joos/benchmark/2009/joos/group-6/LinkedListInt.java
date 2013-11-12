import joos.lib.*;

public class LinkedListInt{
	protected int stored;
	protected LinkedListInt nextNode;
	
	public LinkedListInt(){
		super();
		stored = 0;
		nextNode = null;
	}
	public LinkedListInt(int o){
		super();
		stored = o;
		nextNode = null;
	}
	
	//Appends to the end of the list
	public void addNode(int o){
		LinkedListInt newLast;
		LinkedListInt current;

		current = this;
		newLast = new LinkedListInt(o);
		while(!(current.getNextNode() == null)){
			current = current.getNextNode();
		}
		current.setNextNode(newLast);
	}
	
	public int getInt(){
		return stored;
	}

	public void setInt(int o){
		stored = o;
	}
		
	public LinkedListInt getNextNode(){
		return nextNode;
	}

	public void setNextNode(LinkedListInt n) {
		nextNode = n;
	}
	
	public LinkedListInt getNodeN(int n){
		int i;
		LinkedListInt current;
		current = this;
		for(i=0; i<=n; i++){	
			if(current.getNextNode() == null){
				i = n;
			}
			else {
				current = current.getNextNode();
			}
		}
		return current;
	}
	
}
