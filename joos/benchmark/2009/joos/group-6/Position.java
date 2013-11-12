public class Position {
	protected int x;
	protected int y;
	
	//Default constructor initializes to (0,0)
	public Position(){
		super();
		x = 0;
		y = 0;
	}
	
	public Position(int xx, int yy){
		super();
		x = xx;
		y = yy;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int n){
		x = n;
	}
	
	public void setY(int n){
		y = n;
	}
	
	public void setPos(int xx, int yy){
		x = xx;
		y = yy;
	}
	
	public boolean equalsP(Position other){
		boolean r;
		Position p;

		p = other;

		r = false;
		if(other.getX() == x &&other.getY() == y){
			r = true;
		}
		return r;
	}
}
