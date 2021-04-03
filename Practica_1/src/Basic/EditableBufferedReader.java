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
            return ch;
        // Special Simbols beginning with ESC
        if ((ch = super.read()) != '[')
            return ch;
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
                case Const.RIGHT:
                    bell = line.moveCursorForward();
                    break;
                case Const.LEFT:
                    bell = line.moveCursorBackward();
                    break;
                case Const.DEL:
                    bell = line.deleteCharForward();
                    break;
                case Const.BS: // BackSpace 
                    bell = line.deleteCharBackward();
                    break;
                case Const.INS:
                    if(line.insert())
                        System.out.print(""); // Enable Cursor Blinking TODO
                    else
                        System.out.print(""); // Disable Cursor Blinking TODO
                    break;
                case Const.HOME:
                    line.cursorAtStart();
                    break;
                case Const.END:
                    line.cursorAtEnd();
                    break;
                default:
                    bell = line.addChar((char) ch);
                    break;
            }
            if (bell) 
                System.out.print(Const.BEEP);
            System.out.print(line.text());
        }
        return line.toString();
    }
}
