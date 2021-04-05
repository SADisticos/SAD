package BasicScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import Other.Const;
import Other.TerminalWidth;
import java.util.Scanner;

/**
 *
 * @author Enric
 */
public class EditableBufferedReader extends BufferedReader{
    
    private Scanner sc;
    /**
     * Editable buffering-input stream which uses default-sized input buffer (8 Kb)
     * @param in
     */
    public EditableBufferedReader(Reader in) {
        super(in);
        sc = new Scanner(System.in);
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
     * Reads a single characters.Uses parsing method to know if it is character or special simbol
     * 
     * @return The character read as an integer or -1 if the end of the stream has been reached
     * @throws java.io.IOException
     */
    public int readScanner() throws IOException{
        
        int ch;
        // Non special simbols
        if ((ch = nextChar()) != Const.ESC)
            switch(ch){
                case Const.CR: case Const.LF:
                    return Const.Option.ESC;
                case Const.DEL:
                    return Const.Option.DEL;
                default:
                    return ch;
            }
        
        // Special Simbols beginning with ESC
        if ((ch = nextChar()) == '['){
            if ((ch = nextChar()) == '3')
                if((ch = nextChar()) == '~')
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
            switch(ch = nextChar()){
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
    
    @SuppressWarnings("empty-statement")
    private int nextChar(){
        while(!sc.hasNext());    // Active wait loop until there's another char
        return (int) sc.nextByte();
    }
    
    @Override
    public String readLine() throws IOException{
        int ch;
        Line line = new Line(TerminalWidth.getColumns());
        boolean bell = false;
        
        while ((ch = readScanner()) != Const.Option.ESC){
            switch(ch){
                //case Const.Option.UP:
                //    break;
                //case Const.Option.DOWN:
                //    break;
                case Const.Option.RIGHT:
                    bell = line.moveCursorForward();
                System.out.print(Const.MOVEFORWARD);
                    break;
                case Const.Option.LEFT:
                    bell = line.moveCursorBackward();
                    System.out.print(Const.MOVEBACKWARD);
                    break;
                case Const.Option.SUPR:
                    bell = line.deleteCharForward();
                    break;
                case Const.Option.DEL: // BackSpace 
                    bell = line.deleteCharBackward();
                    System.out.print(Const.MOVEBACKWARD);
                    break;
                case Const.Option.INS:
                    if(line.insert())
                        System.out.print(Const.STARTBLINKCURSOR);
                    else
                        System.out.print(Const.STOPBLINKCURSOR);
                    break;
                case Const.Option.HOME:
                    line.cursorAtStart();
                    break;
                case Const.Option.END:
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
