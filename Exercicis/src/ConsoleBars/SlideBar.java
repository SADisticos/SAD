package ConsoleBars;

import java.io.*;
import static java.lang.System.in;
/**
 *
 * @author Enric
 */
public class SlideBar {
    
    private static void setRaw(){
        // pùt terminal in raw mode
        try{
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (IOException|InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    private static void unsetRaw(){
        // restore terminal to cooked mode
        try{
            String[] cmd = {"/bin/sh", "-c", "stty sane </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch(IOException | InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    static final int RIGHT = 0, LEFT = 1;
    
    public static int readArrow() throws IOException{
        int ch;
        
        do{
            // read arrow key
            if((ch = in.read()) == Const.ESC)
                if((ch = in.read()) == '[')
                    if ((ch = in.read()) == 'C')
                        return RIGHT;
                    else if (ch == 'D')
                        return LEFT;
        } while(ch != '\r');
        return ch;
    }
    
    public static void main(String[] args) throws IOException{
        int arrow;
        ConsoleBar con = null;
        ValueBar value = null;
        
        try{
            setRaw();
            value = new ValueBar();
            con = new ConsoleBar(value);
            value.addObserver(con);
            
            while ((arrow = readArrow()) != '\r')
                if (arrow == RIGHT)
                    value.inc();
                else
                    value.dec();
        } finally{
            unsetRaw();
            // clean-up console
        }
    }
}
