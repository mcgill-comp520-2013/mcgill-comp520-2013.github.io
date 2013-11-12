public class Board {	
	protected int height;
	protected int width;
	protected Paddle leftPaddle;
	protected Paddle rightPaddle;
	protected Ball ball;
	protected BoardController boardController;
	protected Wall wall;

	//Size of paddle and board and ball may need adjusting
	public Board(Wall w, BoardController b){
		super();
		height = 10;
		width = 40;
		leftPaddle = new Paddle(0,height/2 + 1,height/2 - 1);//Paddle Length 3
		rightPaddle = new Paddle(width,height/2 + 1, height/2 - 1);//Paddle Length 3
		ball = new Ball(0,height/2);
		wall = w;
		boardController = b;
	}

	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}

	public Paddle getPaddle(int i) {
		if (i == 1) {
			return leftPaddle;
		}
		else {
			return rightPaddle;		
		}
	}

	public Ball getBall() {
		return ball;
	}
	
	public BoardController getBoardController() {
		return boardController;
	}

	public String getPathDrawing(LinkedListPosition points){
		int i;
		int j;
		int k;
		int l;
		int currentX;
		int currentY;
		int nextX;
		int nextY;
		int deltaY;
		int deltaX;
		boolean isNegativeSlope;
		String r;
		LinkedListLinkedListInt aCurrent;
		LinkedListInt lineCurrent;
		LinkedListPosition current;
		LinkedListLinkedListInt Array;

		int here;

		here = 0;

		Array = new LinkedListLinkedListInt(new LinkedListInt(0));		
		current = points;
		aCurrent = Array;
		lineCurrent = aCurrent.getLinkedListInt();
		for(j = 0; j<width-2; j++){
			lineCurrent.addNode(0);
		}
		for(i = 0; i<height+3; i++){
			aCurrent.addNode(new LinkedListInt(0));
			lineCurrent = aCurrent.getLinkedListInt();
			for(j = 0; j<width-2; j++){
				lineCurrent.addNode(0);
			}
			aCurrent = aCurrent.getNextNode();
		}
		
		
		
		/*boardController.getIO().println("getting here");
		boardController.getIO().println("Point1 " + points.getPosition().getX() + "," + points.getPosition().getY());
		points = points.getNextNode();
		boardController.getIO().println("Point2 " + points.getPosition().getX() + "," + points.getPosition().getY());
		*/
		//Array Initialized
		
//boardController.getIO().println("here " + here);
//here = here + 1;

		while(!(current.getNextNode() == null)){
			//boardController.getIO().println("Pair of points");
			
			currentX = current.getPosition().getX();
			currentY = current.getPosition().getY();
			nextX = current.getNextNode().getPosition().getX();
			nextY = current.getNextNode().getPosition().getY();
			deltaX = nextX-currentX;
			deltaY = nextY -currentY;
			if(deltaY >0 && deltaX>0){
				isNegativeSlope = false;
			}
			else if(deltaY <0 && deltaX<0){
				isNegativeSlope = false;
			}
			else{
				isNegativeSlope = true;
			}
			//check slope type
			//Slope of -1
			//boardController.getIO().println(""+currentX + ","+currentY +" to " + nextX + "," +nextY);
//	boardController.getIO().println("here " + here);
//here = here + 1;
		
			if(deltaY == -deltaX){
//				boardController.getIO().println("here a");
		

				//down->up
				if(deltaY>0){
					for(i = currentY; i<=nextY; i++){
						lineCurrent = Array.getNodeN(i).getLinkedListInt();
						lineCurrent.getNodeN(-i+currentY +currentX).setInt(1);
						
					}
				}
				else{
					for(i = currentY; i>=nextY; i = i - 1){
						lineCurrent = Array.getNodeN(i).getLinkedListInt();
						lineCurrent.getNodeN(-i+currentY +currentX).setInt(1);
						
					}
				}
			}//Horizontal Line
			else if(deltaY == 0){
				lineCurrent = Array.getNodeN(currentY).getLinkedListInt();
				//Left->Right
				if(deltaX>0){
					for(i = currentX; i<= nextX; i++){
						lineCurrent.getNodeN(i).setInt(1);
					}
				}
				//Left<--Right
				else{
					for(i = currentX; i>= nextX; i = i - 1){
						lineCurrent.getNodeN(i).setInt(1);
					}
				}
			}
			

			//Y-alligned positive slope
			else if(deltaY > deltaX && !isNegativeSlope){
//boardController.getIO().println("YallignedPositive");
		

				for(i = currentY; i<=nextY; i++){
					lineCurrent = Array.getNodeN(i).getLinkedListInt();
					lineCurrent.getNodeN( ((i-currentY)*deltaX) /deltaY + currentX  ).setInt(1);
					
					
					//boardController.getIO().println("Adjusted a Pixel");
					//boardController.getIO().println("Point being set to Path: (" +  (((i-currentY)*deltaX) /deltaY + currentX) + "," +i+")");
				}
			}//Y-alligned negative slope
			else if (deltaY < deltaX && isNegativeSlope){
//boardController.getIO().println("YallignNeg");
		

				for(i = currentY; i>= nextY; i = i - 1){
					lineCurrent = Array.getNodeN(i).getLinkedListInt();
					lineCurrent.getNodeN( ((i-currentY)*deltaX) /deltaY + currentX  ).setInt(1);
					
					
					
					//boardController.getIO().println("Adjusted a PixelNeg");
				}
			}//X-aligned positive slope
			else if(deltaY< deltaX && !isNegativeSlope){
//boardController.getIO().println("XalignPos");
		

				for(i = currentX; i<= nextX; i++){
					lineCurrent = Array.getNodeN( ((i-currentX)*deltaY)/deltaX -currentY).getLinkedListInt();
					lineCurrent.getNodeN(i).setInt(1);
					
				}
			}//X-aligned negative slope
			else if(deltaY< deltaX && isNegativeSlope){
//boardController.getIO().println("XalignNeg");
		

				for(i = currentX; i>= nextX; i = i - 1){
					lineCurrent = Array.getNodeN( ((i-currentX)*deltaY)/deltaX -currentY).getLinkedListInt();
					lineCurrent.getNodeN(i).setInt(1);
					
				}
			}//Slope of 1
			else if(deltaY == deltaX){
//boardController.getIO().println("here f");
		


				//down->up
				if(deltaY>0){
					for(i = currentY; i<=nextY; i++){
//boardController.getIO().println("here f1");

						lineCurrent = Array.getNodeN(i).getLinkedListInt();
						lineCurrent.getNodeN(i-currentY +currentX).setInt(1);
						
						
					}
				}
				else{
					for(i = currentY; i>=nextY; i = i - 1){
						lineCurrent = Array.getNodeN(i).getLinkedListInt();
						lineCurrent.getNodeN(i-currentY +currentX).setInt(1);
					}
				}
			}//Last condition is a vertical Line
			else{
//boardController.getIO().println("here h");
		

				//down-->up
				if(deltaY >0){
					for(i = currentY; i<=nextY; i++){
						Array.getNodeN(i).getLinkedListInt().getNodeN(currentX).setInt(1);
					}
				}
				else{
					for(i = currentY; i>=nextY; i = i - 1){
						Array.getNodeN(i).getLinkedListInt().getNodeN(currentX).setInt(1);
					}
				}
			}
			current = current.getNextNode();
		}
//boardController.getIO().println("here " + here);
//here = here + 1;

		//The 2-D bit Map of everything except the edges are filled in  Now turn it into a string
		
		
		
		r = "";
		
		// draw top wall
		r = r + " ";
		for (j = -2; j <= width -1; j++) {
			r = r + "-";
		} 
		r = r + " \n";

		for(i = height; i >= 0; i = i -1){
			// left wall
			r = r + "| ";
			if(leftPaddle.getBottom() <= i && leftPaddle.getTop() >= i){
				r = r + "|";
			}
			else{
				r = r + " ";
			}
			for(j = 1; j <= width-3; j++){
				if(Array.getNodeN(i).getLinkedListInt().getNodeN(j).getInt() == 1){
					r = r + "*";
				}
				else{
					r = r + " ";
				}
			}
			if(rightPaddle.getBottom() <=i && rightPaddle.getTop() >= i){
				r = r + "|";
			}
			else{
				r = r + " ";
			}
			// right wall
			r = r + " |\n";
		}

		// draw bottom wall
		r = r + " ";
		for (j = -2; j <= width -1; j++) {
			r = r + "-";
		} 
		r = r + " \n";
	
		//boardController.getIO().println(r);
		return r;
	}
	
	//Not appropriately generalized for paddles in places other than the edges
	public String toString(){
		int i;
		int j;
		String r;

		r = "";

/* DEACTIVATED
		// r = r + "ball x " + ball.getPosition().getX() + " y " + ball.getPosition().getY() + "\n";
		// r = r + "lpad from " + leftPaddle.getBottom() + " to " + leftPaddle.getTop() + " rpad from " + rightPaddle.getBottom() + " to " + rightPaddle.getTop() + "\n";
		
		// draw top wall
		r = r + " ";
		for (j = -2; j <= width + 2; j++) {
			r = r + "-";
		} 
		r = r + " \n";
		
		for (i = height; i >= 0; i = i-1){
			// left wall
			r = r + "| ";
			if (leftPaddle.getBottom() <= i && leftPaddle.getTop() >= i){
				r = r + "|";
			}
			else {
				r = r + " ";
			}
			for (j = 0; j <= width; j++){
				if (ball.getPosition().getY() == i && ball.getPosition().getX() == j){
					r = r + "*";
				}
				else{
					r = r + " ";
				}
			}
			if (rightPaddle.getBottom() <= i && rightPaddle.getTop() >= i){
				r = r + "|";
			}
			else {
				r = r + " ";
			}
			// right wall
			r = r + " |\n";
		}

		// draw bottom wall
		r = r + " ";
		for (j = -2; j <= width + 2; j++) {
			r = r + "-";
		} 
		r = r + " \n";
	
		boardController.getIO().println(r);
	
*/

		return r;
	}
	
	public void reflectBallAtWall(int slope, Paddle p, LinkedListPosition l) {
		int x;
		int newy;
		int newslope;
		
		if (wall.getAngleFactor() == 0) {
		// GAME END. Player 1 looses. 
		boardController.getIO().println("Player 2: You really thought you could play with a glass wall?\n Player 1: Stop making me break the fourth wall dammit!");
		}
		else { 
			newslope = (- slope) * wall.getAngleFactor();
			if (p.getX() == width) {
			// going right
				if (slope > 0) {
				// top wall 
				x = ((height - ball.getPosition().getY()) / slope ) + ball.getPosition().getX();
				ball.setPosition(new Position(x, height)); 	// Position of the ball on the wall
				this.toString();
				l.addNode(ball.getPosition());
				newy = height + newslope * (width - x);
					if (newy < 0) {
					// The ball is going to hit the bottom wall
					this.reflectBallAtWall(newslope, p, l);
					}
					else {
					// The ball is going to hit the right wall 
					ball.setPosition(new Position(width, newy));
					this.toString();
					l.addNode(ball.getPosition());
					this.reflectBallAtPaddle(newslope, p, l);
					}
				} 
				else {
				// bottom wall
				x = ((0 - ball.getPosition().getY())/ slope) + ball.getPosition().getX(); 
				ball.setPosition(new Position(x, 0));
				this.toString();
				l.addNode(ball.getPosition());
				newy = 0 + newslope * (width - x);
					if (newy > height) {
					// The ball is going to hit the top wall
					this.reflectBallAtWall(newslope, p, l);
					}
					else {
					// The ball is going to hit the right wall
					ball.setPosition(new Position(width, newy));
					this.toString();
					l.addNode(ball.getPosition());
					this.reflectBallAtPaddle (newslope, p, l);
					}
				}
			}
			else {
			// going left
				if (slope > 0) {
				// bottom wall
				x = ball.getPosition().getX() - ((ball.getPosition().getY() - 0) / slope);
				ball.setPosition(new Position(x, 0));
				this.toString();
				l.addNode(ball.getPosition());
				newy = - newslope * x;
					if (newy > height) {
					// The ball is going to hit the top wall
					this.reflectBallAtWall(newslope, p, l);
					}			
					else {
					// The ball is going to hit the left wall
					ball.setPosition(new Position (0, newy));
					this.toString();
					l.addNode(ball.getPosition());
					this.reflectBallAtPaddle (newslope, p, l);
					}
				}
				else {
				// top wall
				x = ball.getPosition().getX() - ((ball.getPosition().getY() - height) / slope );
				ball.setPosition(new Position(x, height));
				this.toString();
				l.addNode(ball.getPosition());
				newy = height - newslope * x;
					if (newy < 0) {
					// The ball is going to hit the bottom wall
					this.reflectBallAtWall(newslope, p, l);
					} 
					else {
					// The ball is going to hit the left wall
					ball.setPosition(new Position(0, newy));
					this.toString();
					l.addNode(ball.getPosition());
					this.reflectBallAtPaddle(newslope, p, l);
					}
				}
			}

		}
                		      
	}
                		        
	public void shootBall(int slope) {
		LinkedListPosition l;
		int y;

		l = new LinkedListPosition (ball.getPosition());
		y = slope * width + ball.getPosition().getY();
		if (y < 0 || y > height) {
			this.reflectBallAtWall(slope, rightPaddle, l);
		}
		else {
			ball.setPosition(new Position(width, height / 2));
			l.addNode(ball.getPosition());
			this.reflectBallAtPaddle(slope, rightPaddle, l);
		}
	}
	
	public void reflectBallAtPaddle(int slope, Paddle p, LinkedListPosition l) {
		int y;
		int newslope; 
		int newy;
		String path;

		y = ball.getPosition().getY();
		newslope = -slope;
		
		//I  have altered this piece to what I think you were trying to do-----------
		//this.PathDrawing(l);  we draw before letting the next player play or before saying that the game ended.
		path = this.getPathDrawing(l);
		boardController.getIO().println(path);
		
		//---------------------------------------------------------------------------
		
		if (y <= p.getTop() && p.getBottom() <= y) {
		// got the paddle		
			if (p.getX() == width) {
			// right paddle
			this.getBoardController().askPaddle(1);
			newy = y - newslope * width;  // Could have written (y - newslope * ball.getPosition().getX() )
				if (newy < 0 || newy > height){
				// The ball is going to hit the top or the bottom wall
				this.reflectBallAtWall(newslope, leftPaddle, l);
				}
				else {
				// The ball is going to hit the left wall 
				ball.setPosition(new Position(0, newy));
				this.toString();
				l.addNode(ball.getPosition());
				this.reflectBallAtPaddle(newslope, leftPaddle, l);
				}
			}
			else {
			// left paddle
			this.getBoardController().askPaddle(2);
			newy = newslope * width + y;  
				if (newy < 0 || newy > height){
				// The ball is going to hit the top or the bottom wall
				this. reflectBallAtWall(newslope, rightPaddle, l);
				}
				else {
				// The ball is going to hit the right wall 
				ball.setPosition(new Position(width, newy));
				this.toString();
				l.addNode(ball.getPosition());
				this.reflectBallAtPaddle(newslope, rightPaddle, l);
				}
			}	
		}
		else {
			if (p.getX() == width) {
			// Player 2 looses
			boardController.getIO().println("Player 2 hath been pwn3d");
			}
			else {
			// Player 1 looses
			boardController.getIO().println("Player 1 hath been pwn3d");		
			}
		}
		this.toString();
	}
}

