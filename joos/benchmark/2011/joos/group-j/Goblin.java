
public class Goblin extends Creature
{
	public Goblin(Room r)
	{
		super();
		name = "Goblin";
		offense = 2;
		defense = 0;
		health = 6;
		maxhealth = 6;
		room = r;
		room.getCreatures().add(this);
	}
}
