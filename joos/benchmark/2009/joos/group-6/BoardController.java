import joos.lib.*;

public class BoardController {
	protected Board board;
	protected JoosIO io;
	
	public BoardController(){
	    super();
		io  = new JoosIO();
		board = null;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void run(){
		String input;
		int slope;
		int wall;
		input = "arbitrarilyChosenString";
		wall = 1;		

		while (!(wall == 0)) {
			io.println("Player 1 and 2 agree on a wall (1 stone, 2 rubber, 3 wool, 4 glass) or type 0 to exit:");
			wall =  io.readInt();
			
			if (!(wall == 0)) {
				if (wall == 1) {
						board = new Board(new Wall(), this);		
				}
				else if (wall == 2) {
						board = new Board(new RubberWall(), this);		
				}
				else if (wall == 3) {
						board = new Board(new WoolWall(), this);		
				}
				else {
						board = new Board(new GlassWall(), this);		
				}
				
				io.println("Player 2 choose an integer slope for your first shot:");
				slope = io.readInt();
				
				this.askPaddle(2);
				board.shootBall(slope);
				// if we are here somebody lost
			}
		}
	}
	
	public void askPaddle(int i) {
		int paddley;
		
		io.println("Player " + i + " choose a position for your paddle center (" + board.getPaddle(i).getHeight()/2 + " <= y <= " + (board.getHeight() - board.getPaddle(i).getHeight()/2) + "):");
		paddley =  io.readInt();
		if (board.getPaddle(i).getHeight()/2 <= paddley && paddley <= (board.getHeight() - board.getPaddle(i).getHeight()/2)) {
			board.getPaddle(i).setPosition(paddley + board.getPaddle(i).getHeight()/2, paddley - board.getPaddle(i).getHeight()/2);
		}
		board.toString();
	}
	
	public void DrawPath() {
		
	}

	public JoosIO getIO() {
		return io;
	}
}
