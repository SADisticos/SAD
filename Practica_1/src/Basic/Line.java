package Basic;

/**
 *
 * @author Enric
 */
public class Line {
    private int pos, maxLen;
    private StringBuilder line;
    private boolean insertMode;
    
    public Line(int maxLen){
        pos = 0;
        insertMode = false;
        this.maxLen = maxLen;
        line = new StringBuilder();
    }
    
    public boolean addChar(char c){
        if(insertMode && pos < line.length()) // insert in the middle
            line.deleteCharAt(pos);
        if(line.length() < (maxLen-1)){ // Can be inserted
            line.insert(pos,c);
            ++pos;
            return false;
        }
        return true;
    }
    
    public boolean deleteCharForward(){
        return deleteChar(1);
    }
    
    public boolean deleteCharBackward(){
        return deleteChar(-1);
    }
    
    public boolean deleteChar(int move){
        if (move == 1 && pos < line.length()){
            line.deleteCharAt(pos);
            return false;
        }
        if (move == -1 && pos > 0){
            line.deleteCharAt(pos-1);
            --pos;
            return false;
        }
        return true;
    }
    
    public boolean moveCursorForward(){
        return moveCursor(1);
    }
    
    public boolean moveCursorBackward(){
        return moveCursor(-1);
    }
    public boolean moveCursor(int move){
        pos += move;
        if (pos > line.length()){
            pos = line.length();
            return true;
        }
        if (pos < 0){
            pos = 0;
            return true;
        }
        return false;
    }
    
    public void cursorAtStart(){
        pos = 0;
    }
    
    public void cursorAtEnd(){
        pos = line.length();
    }
    
    public boolean insert(){
        return insertMode = !insertMode;
    }
    
    public String text(){
        return null;
    }
    
    @Override
    public String toString(){
        return line.toString();
    }
}
