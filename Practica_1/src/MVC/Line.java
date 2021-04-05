package MVC;

import Other.Const;
import java.util.Observable;
/**
 *
 * @author Enric
 */

/* MODEL CLASS */
public class Line extends Observable{
    private int index;
    private final int maxLen;
    private StringBuilder line;
    private boolean insertMode;
    
    public Line(int maxLen){
        index = 0;
        insertMode = false;
        this.maxLen = maxLen;
        line = new StringBuilder();
    }
    
    public void addChar(char c){
        setChanged();
        if(insertMode && index < line.length()) // insert in the middle
            line.deleteCharAt(index);
        if(line.length() < (maxLen-1)){ // Can be inserted
            line.insert(index++,c);
            notifyObservers(new Console.Command(Console.Opcode.REFRESH));
        }
        else
            notifyObservers(new Console.Command(Console.Opcode.BELL));
    }
    
    public void deleteCharForward(){
        deleteChar(1);
    }
    
    public void deleteCharBackward(){
        deleteChar(-1);
    }
    
    public void deleteChar(int move){
        setChanged();
        if (move == 1 && index < line.length()){
            line.deleteCharAt(index);
            notifyObservers(new Console.Command(Console.Opcode.REFRESH));
        }
        else if (move == -1 && index > 0){
            line.deleteCharAt((index--)-1);
            notifyObservers(new Console.Command(Console.Opcode.REFRESH));
        }
        else
            notifyObservers(new Console.Command(Console.Opcode.BELL));
    }
    
    public void moveCursorForward(){
        moveCursor(1);
    }
    
    public void moveCursorBackward(){
        moveCursor(-1);
    }
    
    public void moveCursor(int move){
        setChanged();
        index += move;
        if (index > line.length()){
            index = line.length();
            notifyObservers(new Console.Command(Console.Opcode.REFRESH));
        }
        else if (index < 0){
            index = 0;
            notifyObservers(new Console.Command(Console.Opcode.REFRESH));
        }
        else
            notifyObservers(new Console.Command(Console.Opcode.BELL));
    }
    
    public void cursorAtStart(){
        setChanged();
        index = 0;
        notifyObservers(new Console.Command(Console.Opcode.REFRESH));
    }
    
    public void cursorAtEnd(){
        setChanged();
        index = line.length();
        notifyObservers(new Console.Command(Console.Opcode.REFRESH));
    }
    
    public boolean insert(){
        return insertMode = !insertMode;
    }
    
    public int getIndex(){
        return index;
    }
    
    @Override
    public String toString(){
        return line.toString();
    }
}
