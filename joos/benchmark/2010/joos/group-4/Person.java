import joos.lib.*;

public class Person
{
	protected int ID;
	protected int partnerID;
	protected LList prefList;
	protected int next; // next person in pref_list to propose

	public Person(int ID_a, LList prefList_a)
	{
		super();
		ID = ID_a;
		prefList = prefList_a;
		partnerID = -1; // this person is initially free
		next = 1;
	}

	public boolean isFree()
	{
		return partnerID == -1;
	}

	public boolean perferAtoB(int aID, int bID)
	{
		int tmp;
		Node head;

		head = prefList.getHead();
		while (head != null) {
			tmp = ((Integer)head.getData()).intValue();
			if (tmp == aID) {
				return true;
			} else if (tmp == bID) {
				return false;
			}
			head = head.getNext();
		}
		return false; // shouldn't happen
	}

	public int getID()
	{
		return ID;
	}

	public int getPartID()
	{
		return partnerID;
	}

	public void setPartID(int i)
	{
		partnerID = i;
	}

	public int getNextCandidate()
	{
		next++;
		return ((Integer)prefList.getNthElement(next - 1)).intValue();
	}

	public int getNextID()
	{
		return next;
	}

	public LList getPrefList()
	{
		return prefList;
	}
}
