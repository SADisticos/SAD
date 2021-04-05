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
                    return Const.Option.ESC;
                case Const.DEL:
                    return Const.Option.DEL;
                default:
                    return ch;
            }
        
        // Special Simbols beginning with ESC
        if ((ch = super.read()) == '['){
            if ((ch = super.read()) == '3')
                if((ch = super.read()) == '~')
                    return Const.Option.SUPR;
            switch(ch){  // Arrows
                case Const.RIGHT:
                    return Const.Option.RIGHT;
                case Const.LEFT:
                    return Const.Option.LEFT;
                case Const.UP:
                    return Const.Option.UP;
                case Const.DOWN:
                    return Const.Option.DOWN;
            }
        }
        else if(ch == 'O')                 // F_ Characters
            switch(ch = super.read()){
                case Const.F1:
                    return Const.Option.HOME;
                case Const.F2:
                    return Const.Option.INS;
                case Const.F3:
                    return Const.Option.END;
            }
        else if(ch == Const.ESC)
            return Const.Option.ESC;
        return ch;
    }
    
    @Override
    public String readLine() throws IOException{
        int ch;
        
        while ((ch = read()) != Const.Option.ESC){
            switch(ch){
                //case Const.Option.UP:
                //    break;
                //case Const.Option.DOWN:
                //    break;
                case Const.Option.RIGHT:
                    line.moveCursorForward();
                    break;
                case Const.Option.LEFT:
                    line.moveCursorBackward();
                    break;
                case Const.Option.SUPR:
                    line.deleteCharForward();
                    break;
                case Const.Option.DEL: // BackSpace 
                    line.deleteCharBackward();
                    break;
                case Const.Option.INS:
                    line.insert();
                    break;
                case Const.Option.HOME:
                    line.cursorAtStart();
                    break;
                case Const.Option.END:
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
