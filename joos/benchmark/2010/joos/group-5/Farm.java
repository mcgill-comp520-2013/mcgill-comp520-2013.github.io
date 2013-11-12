import joos.lib.*;
import java.util.*;

public class Farm{
   
    protected Hashtable animals;
    protected Hashtable foods;
    protected Hashtable behaviorList;
    protected JoosIO f;
    protected Farmer farmer;
    protected Item activeItem;
    
    public Farm(){
	super();
	f = new JoosIO();
	this.initAnimals();
	foods = new Hashtable();
	this.initFoods();
	farmer = new Farmer();
    }

    public void initFoods(){
	Water water;
	Fish fish;
	Bone bone;
	water = new Water(12);
	foods.put("water", water);
	fish = new Fish(5);
	foods.put("fish", fish);
	bone = new Bone(5);
	foods.put("bone", bone);
    }

    public void initAnimals(){
	Cat cat;
	Dog dog;

	animals = new Hashtable();
	cat = new Cat();
	animals.put("cat", cat);
	dog = new Dog();
	animals.put("dog", dog);	
    }

    public void parseCommand( String s ){
	Animal animal;
	Feed feed;
	Play play;

	if ( s.equals("cat")){
	    activeItem = (Animal) animals.get("cat"); 
	    animal = (Cat) activeItem;
	    animal.printNormal();
	}

	if ( s.equals("dog")){
	    activeItem = ( Animal) animals.get("dog");
	    animal = (Dog) activeItem;
	    animal.printNormal();
	}

	if ( s.equals("feed")){
	    feed = new Feed( this );
	    farmer.spendEnergy( feed.getNeededEnergy());
	    activeItem.reaction( feed );
	}
	
	if ( s.equals("play")){
            play = new Play( this );
	    play.printImage();
            farmer.spendEnergy( play.getNeededEnergy());
            activeItem.reaction( play );
        }
	
    }

    public Food getFood( String name){
	Food food;
	food = ( Food ) foods.get( name);
	return food;
    }
    
    public int calculate(){
	int size;
	int i;
	int score;
	Object obj;
	Item item;
	String s;
	
	size = animals.size();
	i = 0;
	score = 0;
	while ( i<size ){
	    obj = animals.elementAt(i);
	    item = ( Item ) obj;
	    score = score + item.getHappyIndex();
	    i=i+1;
	}

	return score;
    }
    
    public void run(){
	String s;
	f.println("Welcome to the Happy Farm!");
	while ( true ){
	    
	    s = f.readLine().trim();
	    if ( s.equals("exit") )
		return;
	    if ( farmer.leftE() < 5 )
		return;
	    this.parseCommand(s);
	}
    }

    public void evaluate( int score ){
	if( score < 20 ){
	    f.println("too bad!");
	    return;
	}

	if( score < 60 ){
	    f.println("not bad!");
	    f.println("");
            f.println(" 0   0 ");
            f.println("   o   ");
            f.println(" \\___/");
	    return;
	}

	if( score < 80 ){
	    f.println("good!");
	    f.println(".''.    .'',");
	    f.println("|  |   /  /");
	    f.println("|  |  /  /");
	    f.println("|  | /  /");
	    f.println("|  |/  ;-._ ");
	    f.println("}  ` _/  / ;");
	    f.println("|  /` ) /  /");
	    f.println("| /  /_/\\_/\\");
	    f.println("|/  /      |");
	    f.println("(  ' \\ '-  |");
	    f.println(" |      |");
	    f.println(" |      |");
	    return;
	}

	if( score >= 80 ){
	    f.println("      Bravo!");
	    f.println("   ____ ");
	    f.println(" (|    |)");
	    f.println("   \\  /");
	    f.println("    )(");
	    f.println("  _|~~|_");
	    f.println(" |______|");
	    return;
	}

    }

    public static void main ( String arg[] ){
	Farm farm;
	int score;
	String s;
	JoosIO f;

	f = new JoosIO();
	s = "";
	farm = new Farm();
	farm.run();
	score = farm.calculate();
	s = s + score;
	f.println("final score: "+ s);
	farm.evaluate( score );
    }
}