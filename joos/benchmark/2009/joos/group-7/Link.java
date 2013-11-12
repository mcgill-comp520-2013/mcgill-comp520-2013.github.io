public class Link {

    protected Location destination;
    protected String name;
    protected String description;
    
    public Link (Location to, String iname, String idescription) {
	super();
	destination = to;
	name = iname;
	description = idescription;
    }

    public String toString() { 
	return name;
    } 

    public void setName( String dirname ) { 
	name = dirname;
    } 

    public String getName() { 
	return name;
    } 

    public String getDescription() { 
	return description;
    } 

    public void setLinksTo ( Location idestination ) { 
	destination = idestination;
    }

    public Location linksTo ( ) { 
	return destination;
    } 

}
