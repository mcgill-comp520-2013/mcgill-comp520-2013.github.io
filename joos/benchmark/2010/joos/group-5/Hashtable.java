import joos.lib.*;
import java.util.*;

public class Hashtable{
    protected Vector elements;
    protected Vector names;
    
    public Hashtable(){
	super();
	elements = new Vector();
	names = new Vector();
    }

    public Object put(Object key, Object value){
	elements.addElement( value );
	names.addElement( key );
	if ( elements.indexOf( value ) == names.indexOf( key ) )
	    return value;
	else return null;
    }

    public boolean contains(Object o){
	return elements.contains( o );
    }
    
    public Object get(Object key){
	int index;
	index = names.indexOf( key );
	return elements.elementAt( index );
    }

    public Object elementAt( int x){
	return elements.elementAt(x);
    }

    public int size(){
	return names.size();
    }
}