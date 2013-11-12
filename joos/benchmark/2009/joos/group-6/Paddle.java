public class Paddle {
	protected int x;
	protected int top;
	protected int bot;
	
	public Paddle(int xx, int t, int b){
		super();
		x = xx;
		top = t;
		bot = b;
	}
	
	public int getX(){
		return x;
	}
	
	public int getTop(){
		return top;
	}
	
	public int getBottom(){
		return bot;
	}
	
	public int getHeight(){
		return top-bot;
	}
	
	public void setPosition(int t, int b) {
		top = t;
		bot = b;
	}
}
