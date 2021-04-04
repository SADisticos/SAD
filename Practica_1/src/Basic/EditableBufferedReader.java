package Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import Other.Const;
import Other.TerminalWidth;
/**
 *
 * @author Enric
 */
public class EditableBufferedReader extends BufferedReader{
    
    /**
     * Editable buffering-input stream which uses default-sized input buffer (8 Kb)
     * @param in
     */
    public EditableBufferedReader(Reader in) {
        super(in);
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
                    return Const.ESC;
                case Const.BS:
                    return Const.Key.BS;
                default:
                    return ch;
            }
        
        // Special Simbols beginning with ESC
        if ((ch = super.read()) == '[')
            switch(super.read()){
                case Const.RIGHT:
                    return Const.Key.RIGHT;
                case Const.LEFT:
                    return Const.Key.LEFT;
                case Const.UP:
                    return Const.Key.UP;
                case Const.DOWN:
                    return Const.Key.DOWN;
                case Const.HOME:
                    return Const.Key.HOME;
                case Const.DEL:
                    return Const.Key.DEL;
            }
        // CSI - ESC [ 
        if ((ch = super.read()) == '2'){ // Insert char - ESC [ 2 ~
            if((ch = super.read()) == '~')
                return Const.INS;
            return ch;
        }
        return ch;  // Characters like arrows or home and end
    }
    
    @Override
    public String readLine() throws IOException{
        int ch;
        Line line = new Line(TerminalWidth.getColumns());
        boolean bell = false;
        
        while ((ch = read()) != Const.ESC){
            switch(ch){
                //case Const.UP:
                //    break;
                //case Const.DOWN:
                //    break;
                case Const.Key.RIGHT:
                    bell = line.moveCursorForward();
                System.out.print(Const.MOVEFORWARD);
                    break;
                case Const.Key.LEFT:
                    bell = line.moveCursorBackward();
                    System.out.print(Const.MOVEBACKWARD);
                    break;
                case Const.Key.DEL:
                    bell = line.deleteCharForward();
                    System.out.print(Const.MOVEFORWARD);
                    break;
                case Const.Key.BS: // BackSpace 
                    bell = line.deleteCharBackward();
                    System.out.print(Const.MOVEBACKWARD);
                    break;
                case Const.Key.INS:
                    if(line.insert())
                        System.out.print(Const.STARTBLINKCURSOR);
                    else
                        System.out.print(Const.STOPBLINKCURSOR);
                    break;
                case Const.Key.HOME:
                    line.cursorAtStart();
                    break;
                case Const.Key.END:
                    line.cursorAtEnd();
                    break;
                default:
                    bell = line.addChar((char) ch);
                    break;
            }
            if (bell) 
                System.out.print(Const.BEEP);
            System.out.print(line.textToPrint());
        }
        return line.toString();
    }
}
