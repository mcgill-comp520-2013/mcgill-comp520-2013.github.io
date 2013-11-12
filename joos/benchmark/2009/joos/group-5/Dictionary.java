
import java.util.*;

public class Dictionary 
{
    protected Vector _keys;
    protected Vector _values;
    protected BeepIO f;
    
    public Dictionary() 
    { 
        super(); 
        _keys = new Vector();
        _values = new Vector();
        f = new BeepIO();
    }
    
    public void addElement(String key, String value)
    {
        _keys.addElement(key);
        _values.addElement(value);
    }
    
    public String get(String key)
    {
        boolean finish;
        int i;
        finish = false;
        i = 0;
        
        for(i = 0; i < _keys.size() && !finish; i++)
        {
            if(((String)key).equals((String)_keys.elementAt(i)))
            {
                finish = true;
            }
        }
        if(i >= _keys.size())
            return null;
        
        return (String)_values.elementAt(i - 1);
    }
}
