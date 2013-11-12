public class Ball {
	protected Position pos;
	protected boolean inGame;
	
	public Ball() {
		super();
	}

	public Ball(Position p){
		super();
		pos = p;
	}

	public Ball(int x, int y){
		super();
		pos = new Position(x,y);
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean i) {
		inGame = i;
	}

	public Position getPosition(){
		return pos;
	}
	
	public void setPosition(Position p){
		pos = p;
	}
}
