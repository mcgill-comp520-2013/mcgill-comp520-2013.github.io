import joos.lib.*;

public class GameOfLife {

    public GameOfLife() {super();}

    public static void main (String [] args) {
        JoosIO io;
        JoosThread t; // used for sleep() function

        Board board;
        int i;
        int j;
        int n;
        String line;
        
        int BOARD_SIZE;
        int DEAD;
        int ALIVE;

        io = new JoosIO();
        t = new JoosThread(new Thread());

        // Define "global constants"
        BOARD_SIZE = io.readInt();
        DEAD       = 0;
        ALIVE      = 1;

        board = new Board(BOARD_SIZE, DEAD, ALIVE);

        //read number of generations from stdin
        n = io.readInt();

        //read board from stdin
        for(i = 0; i < BOARD_SIZE; i++) {
            line = io.readLine();
            for (j = 0; j < BOARD_SIZE; j++) {
                if (line.charAt(2*j) == '0') 
                    board.setStatusAt(ALIVE, i*BOARD_SIZE + j);
            }
        }

        board.printBoard();

        i = 0;
        while (i<n) {
            t.sleep(150);
            board.nextState();
            board.printBoard();
            i = i+1;
        }
    }
}
