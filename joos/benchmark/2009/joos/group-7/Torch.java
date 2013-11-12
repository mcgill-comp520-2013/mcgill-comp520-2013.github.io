import joos.lib.*;
import java.util.*;

public class Torch extends Item {
    
    public Torch(){
	super();
	name = "TORCH";
    }
    
    public boolean canLook(){
	return true;
    }

    public boolean canTake(){
	return true;
    }

    public String getDescription(){
	return super.formatString("This disgusting TORCH emanates a sickly light. The aroma is mighty unpleasant but it looks like you can TAKE it.");
    }

}