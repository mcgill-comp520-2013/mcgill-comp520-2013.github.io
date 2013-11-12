import joos.lib.*;
import java.util.*;

public class Fish extends Food{
    public Fish( int total ){
        super( total );
    }

    public void printImage(){
	f.println(" . __");
	f.println("  /o \\/");
	f.println("  \\__/\\");
    }
}
