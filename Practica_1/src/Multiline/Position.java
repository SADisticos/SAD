package Multiline;

/**
 *
 * @author enric
 */
public class Position {  // It is sort of struct
    private int row, col, pos;
    
    public Position(int row, int col, int maxWidth){
        this.row = row;
        this.col = col;
        pos = col+row*maxWidth;
    }
    
    public Position(int maxWidth){
        this(0,0,maxWidth);
    }
    
    public Position(int pos, int maxWidth){
        this.pos = pos;
        row = (int) pos/maxWidth;
        col = pos - row*maxWidth;
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
