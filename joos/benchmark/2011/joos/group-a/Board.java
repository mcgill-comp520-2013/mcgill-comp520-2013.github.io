import joos.lib.*;

public class Board {

    protected int BOARD_SIZE;
    protected int DEAD;
    protected int ALIVE;
    protected int N_NEIGHBOURS;

    protected int age;
    protected Vector content;


    public Board(int sizeParam, int deadParam, int aliveParam) {
        super();
        int i;
        Cell c;

        BOARD_SIZE = sizeParam;
        DEAD = deadParam;
        ALIVE = aliveParam;
        N_NEIGHBOURS = 8;

        age = 0;

        // The extra cell is used to represent the cells
        // surrounding the border of the board
        content = new Vector(BOARD_SIZE * BOARD_SIZE + 1);
        i = 0;
        while (i <= BOARD_SIZE * BOARD_SIZE) {
            content.insertElementAt(new Cell(BOARD_SIZE, DEAD, ALIVE, i), i);
            i = i + 1;
        }
    }

    public Cell getCellAt(int position) {
        return (Cell) content.elementAt(position);
    }

    public int getStatusAt(int position) {
        Cell c;
        c = (Cell) content.elementAt(position);
        return c.getStatus();
    }

    
    public void setStatusAt(int status, int position) {
        Cell c;
        c = (Cell) content.elementAt(position);
        c.setStatus(status);

        content.setElementAt(c, position);
    }

    public int getNextStatusAt(int position) {
        Cell c;
        c = (Cell) content.elementAt(position);
        return c.getNextStatus();
    }

    public void setNextStatusAt(int status, int position) {
        Cell c;
        c = (Cell) content.elementAt(position);
        c.setNextStatus(status);

        content.setElementAt(c, position);
    }


    // modify the board through one generation
    public void nextState() {

        int i;

        // Find the next status for each cell
        for (i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            this.getNextCellState(i);
        }

        // Send each cell to its next status
        for (i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            this.setStatusAt(this.getNextStatusAt(i), i);
        }

        age = age + 1;
    }
    
    // Determine the next state of the cell
    public void getNextCellState(int position) {
        
        int i;
        int liveCount;
        Integer neighbourPos;
        Vector neighbours;
        Cell c;
        
        c = (Cell) content.elementAt(position);
        neighbours = c.getNeighbours();
        liveCount = 0;

        // Count the number of live neighbours
        for (i = 0; i < N_NEIGHBOURS; i++) {
            neighbourPos = (Integer) neighbours.elementAt(i);
            if (this.getStatusAt(neighbourPos.intValue()) == ALIVE) {
                liveCount = liveCount + 1;
            }
        }

        // Decide if cell should be alive or dead (depending on rules)
        // Rules are:
        //  - if a cell is alive and has less than 2 living neighbours,
        //    it dies of solitude
        //  - if a cell is alive and has more than 3 living neighbours,
        //    it dies (of choking or something)
        //  - if a cell is dead and has exactly 3 living neighbours,
        //    it spontaneously comes to life
        if(this.getStatusAt(position) == ALIVE) {
            if (liveCount < 2 || liveCount > 3) {
                this.setNextStatusAt(DEAD, position);
            }
            else this.setNextStatusAt(ALIVE, position);
        }
        else if (this.getStatusAt(position) == DEAD && liveCount == 3) {
            this.setNextStatusAt(ALIVE, position);
        }
        else this.setNextStatusAt(DEAD, position);
    }

    public void printBoard() {
        JoosIO io;
        int row;
        int col;
        int pos;

        io = new JoosIO();

        //print top border
        io.print(" _");
        col = 0;
        while(col<BOARD_SIZE) {
            io.print("__");
            col = col+1;
        }
        io.println(" ");


        //print rows
        row = 0;
        while(row<BOARD_SIZE) {
            io.print("| ");
            col = 0;
            while(col<BOARD_SIZE) {
                pos = row * BOARD_SIZE + col;
                if(this.getStatusAt(pos) == ALIVE)
                    io.print("@ ");
                else
                    io.print(". ");

                col = col+1;
            }
            io.println("|");
            row = row+1;
        }

        //print bottom border
        io.print("|_");
        col = 0;
        while(col<BOARD_SIZE) {
            io.print("__");
            col = col+1;
        }
        io.println("| Generation: " + age);
    }

}
