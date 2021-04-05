package MVCProperty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/**
 *
 * @author Enric
 */

/* MODEL CLASS */
public class Line{
    private int index;
    private final int maxLen;
    private StringBuilder line;
    private boolean insertMode;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public Line(int maxLen){
        index = 0;
        insertMode = false;
        this.maxLen = maxLen;
        line = new StringBuilder();
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l){
        this.pcs.addPropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener l){
        this.pcs.removePropertyChangeListener(l);
    }
    
    public void addChar(char c){
        String oldLine = this.toString();
        int oldIndex = index;
        
        if(insertMode && index < line.length()) // insert in the middle
            line.deleteCharAt(index);
        if(line.length() < (maxLen-1)){ // Can be inserted
            line.insert(index++,c);
            pcs.firePropertyChange("text", oldLine, this.toString());
            pcs.firePropertyChange("index", oldIndex, index);
        }
        else
            pcs.firePropertyChange("bell", false, true);
    }
    
    public void deleteCharForward(){
        deleteChar(1);
    }
    
    public void deleteCharBackward(){
        deleteChar(-1);
    }
    
    public void deleteChar(int move){
        String oldLine = this.toString();
        int oldIndex = index;
        
        if (move == 1 && index < line.length()){
            line.deleteCharAt(index);
            pcs.firePropertyChange("text", oldLine, this.toString());
        }
        else if (move == -1 && index > 0){
            line.deleteCharAt((index--)-1);
            pcs.firePropertyChange("text", oldLine, this.toString());
            pcs.firePropertyChange("pos", oldIndex, index);
        }
        else
            pcs.firePropertyChange("bell", false, true);
    }
    
    public void moveCursorForward(){
        moveCursor(1);
    }
    
    public void moveCursorBackward(){
        moveCursor(-1);
    }
    
    public void moveCursor(int move){
        String oldLine = this.toString();
        int oldIndex = index;
        
        index += move;
        if (index > line.length()){
            index = line.length();
            pcs.firePropertyChange("bell", false, true);
        }
        else if (index < 0){
            index = 0;
            pcs.firePropertyChange("bell", false, true);
        }
        else{
            pcs.firePropertyChange("text",oldLine, this.toString());
            pcs.firePropertyChange("pos", oldIndex, index);
        }
    }
    
    public void cursorAtStart(){
        int oldIndex = index;
        index = 0;
        pcs.firePropertyChange("pos", oldIndex, index);
    }
    
    public void cursorAtEnd(){
        int oldIndex = index;
        index = line.length();
        pcs.firePropertyChange("pos", oldIndex, index);
    }
    
    public void insert(){
        pcs.firePropertyChange("insert", insertMode, insertMode = !insertMode);
    }
    
    public int getIndex(){
        return index;
    }
    
    @Override
    public String toString(){
        return line.toString();
    }
}
