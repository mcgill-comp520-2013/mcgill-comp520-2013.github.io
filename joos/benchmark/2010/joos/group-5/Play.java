import joos.lib.*;
import java.util.*;

public class Play extends Behavior{

    protected Farm feedFarm;

    public Play( Farm farm ){
        super();
        feedFarm = farm;
        neededEnergy = 20;
    }
    
    public void printImage(){
	f.println("    ____");
	f.println("  .'    '.");
	f.println(" /'-....-'\\");
	f.println(" |        |");
	f.println(" \\.-''''-./");
	f.println("  '.____.'");
    }
}

