public class LocationA3 extends Location { 

    
    public LocationA3() { 
	super();
	roomTitle = "Chamber of Forgiving";
	roomDescription = "The sounds of the reanimated are less threatening here. There is some light from a TORCH burning rancid oil.";
	items.put("TORCH", new Torch());
    } 

    public void removeItem(Item i){
	items.remove(i.getName()); 
	roomDescription = "The sounds of the reanimated are less threatening here. There room has become somewhat dark save for the torch you hold.";
    }
        
} 
