package Multiline;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/**
 *
 * @author Enric
 */

/* MODEL CLASS */
public class MultiLine{
    private Position pos;
    private StringBuilder line;
    private boolean insertMode;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public MultiLine(){
        pos = new Position();
        insertMode = false;
        line = new StringBuilder();
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l){
        this.pcs.addPropertyChangeListener(l);
    }
    
    public void addChar(char c){
        Position oldPos = pos;
        String oldLine = this.toString();
        
        if(insertMode && pos.getPos() < line.length()) // insert in the middle
            line.deleteCharAt(pos.getPos());
        line.insert(pos.getPos(),c);
        pos = new Position(oldPos.getPos()+1);
        pcs.firePropertyChange("text", oldLine, this.toString());
        pcs.firePropertyChange("pos", oldPos, pos);
    }
    
    public void deleteChar(int move){
        Position oldPos = pos;
        String oldLine = this.toString();
        
        if (move == 1 && pos.getPos() < line.length()){
            line.deleteCharAt(pos.getPos());
            pcs.firePropertyChange("text", oldLine, this.toString());
        }
        else if (move == -1 && pos.getPos() > 0){
            line.deleteCharAt(pos.getPos()-1);
            pos = new Position(oldPos.getPos()-1);
            pcs.firePropertyChange("text", oldLine, this.toString());
            pcs.firePropertyChange("pos", oldPos, pos);
        }
        else
            pcs.firePropertyChange("bell", false, true);
    }
    
    public void moveCursor(int row, int col){
        Position oldPos = pos;
        pos = new Position(oldPos.getRow() + row, oldPos.getCol() + col);
        if (pos.getPos() > line.length() || pos.getPos() < 0){
            pos = oldPos; // It can be done that trick 'cause we only 1 block move
            pcs.firePropertyChange("bell", false, true);
        }
        pcs.firePropertyChange("pos", oldPos, pos);
    }
    
    public void cursorAtStart(){
        Position oldPos = pos;
        pos = new Position(0);
        pcs.firePropertyChange("pos", oldPos, pos);
    }
    
    public void cursorAtEnd(){
        Position oldPos = pos;
        pos = new Position(line.length());
        pcs.firePropertyChange("pos", oldPos, pos);
    }
    
    public void insert(){
        pcs.firePropertyChange("insert", insertMode, insertMode = !insertMode);
    }
    
    public Position getPos(){
        return pos;
    }
    
    @Override
    public String toString(){
        return line.toString();
    }
    
    /* Simplified Functions */
    // deleteChar
    public void deleteCharForward(){
        deleteChar(1);
    }
    
    public void deleteCharBackward(){
        deleteChar(-1);
    }
    
    // moveCursor
    public void moveCursorForward(){
        moveCursor(0,1);
    }
    
    public void moveCursorBackward(){
        moveCursor(0,-1);
    }
    
    public void moveCursorUp(){
        moveCursor(-1,0);
    }
    
    public void moveCursorDown(){
        moveCursor(1,0);
    }
}