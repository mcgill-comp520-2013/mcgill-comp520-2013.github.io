public abstract class Command{
    
    public Command(){super();}

    //Make it happen
    public abstract void execute(String name, String s, Player p);

    //If the first word matches a command type
    public abstract boolean isCommand(String s);

    public String formatString(String s){
	StringBuilder sb;
	int i;

	sb = new StringBuilder(s);
	i=0;
	//Inserts a new line at blank spaces about every 50 characters
	while ((i = sb.indexOf(" ", i + 50)) != -1) {
	    sb.replace(i, i + 1, "\n");
	}

	return sb.toString();
    }


}
