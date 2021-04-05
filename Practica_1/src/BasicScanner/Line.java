package BasicScanner;

import Other.Const;
/**
 *
 * @author Enric
 */
public class Line {
    private int index, maxLen;
    private StringBuilder line;
    private boolean insertMode;
    
    public Line(int maxLen){
        index = 0;
        insertMode = false;
        this.maxLen = maxLen;
        line = new StringBuilder();
    }
    
    public boolean addChar(char c){
        if(insertMode && index < line.length()) // insert in the middle
            line.deleteCharAt(index);
        if(line.length() < (maxLen-1)){ // Can be inserted
            line.insert(index,c);
            index++;
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
        if (move == 1 && index < line.length()){
            line.deleteCharAt(index);
            return false;
        }
        if (move == -1 && index > 0){
            line.deleteCharAt(index-1);
            index--;
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
        index += move;
        if (index > line.length()){
            index = line.length();
            return true;
        }
        if (index < 0){
            index = 0;
            return true;
        }
        return false;
    }
    
    public void cursorAtStart(){
        index = 0;
    }
    
    public void cursorAtEnd(){
        index = line.length();
    }
    
    public boolean insert(){
        return insertMode = !insertMode;
    }
    
    public String textToPrint(){
        String text = new String("");
        text += Const.CURSORINITLINE;
        text += Const.ERASELINE;
        text += line.toString();
        text += Const.CSI + (index+1) + "G"; // Cursor absolute index
        return text;
    }
    
    @Override
    public String toString(){
        return line.toString();
    }
}
