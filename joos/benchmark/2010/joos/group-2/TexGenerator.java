import joos.lib.*;

public abstract class TexGenerator
{
	protected String texCode;

	public TexGenerator() 
	{
		super();
		texCode = "";
	}
	
	public abstract boolean willReplace(String texStr);
	public abstract String replace(String texStr);
	
	public void processTex(String texStr)
	{
		String s;
		boolean replace;
			
		replace = this.willReplace(texStr);
		
		if(replace)
			s = this.replace(texStr);
		else
			s = texStr;
			
		if (s == null)
			return;
		
		texCode = texCode + s;
			
		return;
	}
	
	public String toString()
	{
		return texCode;
	}

}