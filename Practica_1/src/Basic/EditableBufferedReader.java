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
        if ((ch = super.read()) != Const.SpecialChars.ESC)
            switch(ch){
                case Const.SpecialChars.CR: case Const.SpecialChars.LF:
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
                case Const.arrows.RIGHT:
                    return Const.option.RIGHT;
                case Const.arrows.LEFT:
                    return Const.option.LEFT;
                case Const.arrows.UP:
                    return Const.option.UP;
                case Const.arrows.DOWN:
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
        else if(ch == Const.SpecialChars.ESC)
            return Const.option.ESC;
        return ch;
    }
    
    @Override
    public String readLine() throws IOException{
        int ch;
        Line line = new Line(TerminalWidth.getColumns());
        boolean bell = false;
        
        while ((ch = read()) != Const.option.ESC){
            switch(ch){
                //case Const.Option.UP:
                //    break;
                //case Const.Option.DOWN:
                //    break;
                case Const.option.RIGHT:
                    bell = line.moveCursorForward();
                System.out.print(Const.MOVEFORWARD);
                    break;
                case Const.option.LEFT:
                    bell = line.moveCursorBackward();
                    System.out.print(Const.MOVEBACKWARD);
                    break;
                case Const.option.SUPR:
                    bell = line.deleteCharForward();
                    break;
                case Const.option.DEL: // BackSpace 
                    bell = line.deleteCharBackward();
                    System.out.print(Const.MOVEBACKWARD);
                    break;
                case Const.option.INS:
                    if(line.insert())
                        System.out.print(Const.STARTBLINKCURSOR);
                    else
                        System.out.print(Const.STOPBLINKCURSOR);
                    break;
                case Const.option.HOME:
                    line.cursorAtStart();
                    break;
                case Const.option.END:
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
