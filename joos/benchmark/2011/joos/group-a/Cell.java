public class Cell {

    protected int BOARD_SIZE;
    protected int DEAD;
    protected int ALIVE;
    
    protected int x;    
    protected int y;
    protected int position;

    protected int status;
    protected int nextStatus;
    protected Vector neighbours;
    protected Vector neighbourCells;


    public Cell(int sizeParam, int deadParam, int aliveParam, int pos) {
        super();
        BOARD_SIZE = sizeParam;
        DEAD = deadParam;
        ALIVE = aliveParam;

        position = pos;
        x = pos % BOARD_SIZE;
        y = pos / BOARD_SIZE;
        status     = DEAD;
        nextStatus = DEAD;

        // get the neighbouring cells
        neighbours = new Vector(8);
        
        //above
        if (y != 0) {
            neighbours.addElement(new Integer(pos - BOARD_SIZE));
            //above left
            if (x != 0)
                neighbours.addElement(new Integer(pos - BOARD_SIZE - 1));
            else
                neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
            //above right
            if (x != BOARD_SIZE)
                neighbours.addElement(new Integer(pos - BOARD_SIZE + 1));
            else
                neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
        }
        else {
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
        }
        //below
        if (y != BOARD_SIZE - 1) {
            neighbours.addElement(new Integer(pos + BOARD_SIZE));
            //below left
            if (x != 0)
                neighbours.addElement(new Integer(pos+BOARD_SIZE - 1));
            else
                neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
            //below right
            if (x != BOARD_SIZE - 1)
                neighbours.addElement(new Integer(pos+BOARD_SIZE + 1));
            else
                neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
        }
        else {
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
        }
        //left
        if (x != 0)
            neighbours.addElement(new Integer(pos-1));
        else
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
        //right
        if (x != BOARD_SIZE - 1)
            neighbours.addElement(new Integer(pos+1));
        else
            neighbours.addElement(new Integer(BOARD_SIZE*BOARD_SIZE));
        
    }


    public int getX() { 
        return x;
    }

    public int getY() {
        return y; 
    }
    
    public int getPosition() {
        return position; 
    }

    public int getStatus() { 
        return status; 
    }

    public void setStatus(int s) {
        status = s;
    }

    public int getNextStatus() { 
        return nextStatus; 
    }

    public void setNextStatus(int s) {
        nextStatus = s;
    }

    public Vector getNeighbours() { 
        return neighbours; 
    }
}
