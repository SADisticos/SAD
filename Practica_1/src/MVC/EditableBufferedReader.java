package MVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import Other.Const;
import Other.TerminalWidth;


/**
 *
 * @author Enric
 */

/* CONTROLLER CLASS */

public class EditableBufferedReader extends BufferedReader{
    private Line line;
    private Console con;
    /**
     * Editable buffering-input stream which uses default-sized input buffer (8 Kb)
     * @param in
     */
    public EditableBufferedReader(Reader in) {
        super(in);
        line = new Line(TerminalWidth.getColumns());
        con = new Console();
        line.addObserver(con);
    }
    
    /**
     * Put terminal in raw mode
     */
    public static void setRaw(){
        try{
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch(IOException| InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Restore terminal to cooked mode
     */
    public static void unsetRaw(){
        try{
            String[] cmd = {"/bin/sh", "-c", "stty sane </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch(IOException| InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Reads a single characters. Uses parsing method to know if it is character or special simbol
     * 
     * @return The character read as an integer or -1 if the end of the stream has been reached
     */
    @Override
    public int read() throws IOException{
        
        int ch;
        // Non special simbols
        if ((ch = super.read()) != Const.ESC)
            switch(ch){
                case Const.CR: case Const.LF:
                    return Const.option.ESC;
                case Const.DEL:
                    return Const.option.DEL;
                default:
                    return ch;
            }
        
        // Special Simbols beginning with ESC
        if ((ch = super.read()) == '['){
            if ((ch = super.read()) == '3')
                if((ch = super.read()) == '~')
                    return Const.option.SUPR;
            switch(ch){  // Arrows
                case Const.RIGHT:
                    return Const.option.RIGHT;
                case Const.LEFT:
                    return Const.option.LEFT;
                case Const.UP:
                    return Const.option.UP;
                case Const.DOWN:
                    return Const.option.DOWN;
            }
        }
        else if(ch == 'O')                 // F_ Characters
            switch(ch = super.read()){
                case Const.F1:
                    return Const.option.HOME;
                case Const.F2:
                    return Const.option.INS;
                case Const.F3:
                    return Const.option.END;
            }
        else if(ch == Const.ESC)
            return Const.option.ESC;
        return ch;
    }
    
    @Override
    public String readLine() throws IOException{
        int ch;
        
        while ((ch = read()) != Const.option.ESC){
            switch(ch){
                //case Const.Option.UP:
                //    break;
                //case Const.Option.DOWN:
                //    break;
                case Const.option.RIGHT:
                    line.moveCursorForward();
                    break;
                case Const.option.LEFT:
                    line.moveCursorBackward();
                    break;
                case Const.option.SUPR:
                    line.deleteCharForward();
                    break;
                case Const.option.DEL: // BackSpace 
                    line.deleteCharBackward();
                    break;
                case Const.option.INS:
                    line.insert();
                    break;
                case Const.option.HOME:
                    line.cursorAtStart();
                    break;
                case Const.option.END:
                    line.cursorAtEnd();
                    break;
                default:
                    line.addChar((char) ch);
                    break;
            }
        }
        return line.toString();
    }
}
