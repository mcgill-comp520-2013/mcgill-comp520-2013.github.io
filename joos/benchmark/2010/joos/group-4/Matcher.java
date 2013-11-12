import joos.lib.*;

/* Gale-Shapley algorithm */
public class Matcher
{
	protected int N; // number of boys and number of girls
	protected LList BList;
	protected LList GList;
	protected LList freeBList;

	public Matcher()
	{
		super();
		BList = new LList();
		GList = new LList();
		freeBList = new LList();

	} 

	public void parse()
	{
		JoosIO f;
		int i, j, id;
		Person tmp;

		f = new JoosIO();
		N = f.readInt();
		/* parse boy info */
		for (i = 0; i < N; i++) {
			tmp = new Person(i + 1, new LList());
			for (j = 0; j < N; j++) {
				id = f.readInt();
				tmp.getPrefList().insertLast(new Integer(id));
			}
			BList.insertLast(tmp);
			freeBList.insertLast(tmp);
		}
		/* parse boy info */
		for (i = 0; i < N; i++) {
			tmp = new Person(i + 1, new LList());
			for (j = 0; j < N; j++) {
				id = f.readInt();
				tmp.getPrefList().insertLast(new Integer(id));
			}
			GList.insertLast(tmp);
		}
	}

	public void match()
	{
		Person boy, girl, poorGuy;
		JoosIO f;

		f = new JoosIO();
		while (!freeBList.isEmpty()) {
			boy = (Person)freeBList.pop();
			girl = (Person)GList.getNthElement(boy.getNextCandidate());
			f.println("Boy " + boy.getID() + " asks girl " + girl.getID() + " out.");
			if (girl.isFree()) {
				boy.setPartID(girl.getID());
				girl.setPartID(boy.getID());
				f.println("Girl " + girl.getID() + " is single.");
				f.println("Boy " + boy.getID() + " and girl " + girl.getID() + " are going out.");
			} else if (girl.perferAtoB(boy.getID(), girl.getPartID())) {
				poorGuy = (Person)BList.getNthElement(girl.getPartID());
				boy.setPartID(girl.getID());
				girl.setPartID(boy.getID());
				poorGuy.setPartID(-1);
				freeBList.push(poorGuy);
				f.println("Girl " + girl.getID() + " was going out with boy " + poorGuy.getID() + ".");
				f.println("But she ditches him, now she's going out with boy " + boy.getID());
			} else {
				f.println("But girl " + girl.getID() + " is not interested in him.");
				if (boy.getNextID() <= N) { // still has some girl left to propose
					freeBList.push(boy);
				}
			}
			f.println("");
		}
		f.println("And everyone is happy now.");
	}

	public static void main(String[] args)
	{
		Matcher m;

		m = new Matcher();
		m.parse();
		m.match();
	}
}
