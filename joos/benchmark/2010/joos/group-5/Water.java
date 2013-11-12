import joos.lib.*;
import java.util.*;

public class Water extends Food{
    public Water( int total ){
	super( total );
    }

    public void printImage(){
	f.println("|~~~~~~~| ");
	f.println("|~~~~~~~| ");
	f.println(" \\_____/  ");
    }
}