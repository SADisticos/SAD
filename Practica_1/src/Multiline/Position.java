package Multiline;

/**
 *
 * @author enric
 */
public class Position {  // It is sort of struct
    private int row, col, pos;
    private static final int WIDTH = Other.TerminalWidth.getColumns();
    
    public Position(int row, int col){
        while(col >= WIDTH){
            row++;
            col -= WIDTH;
        }
        this.row = row;
        this.col = col;
        pos = (this.col -1) +(this.row-1)*WIDTH;
    }
    
    public Position(){
        this(1,1);
    }
    
    public Position(int pos){
        this.pos = pos;
        row = ((int) pos/WIDTH) + 1;
        col = (pos - (row-1)*WIDTH) + 1;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getPos(){
        return pos;
    }
}
