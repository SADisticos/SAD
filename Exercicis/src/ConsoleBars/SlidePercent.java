package ConsoleBars;

import java.io.*;
import static java.lang.System.in;

/**
 *
 * @author Enric
 */

public class SlidePercent {

    private static void setRaw(){
        // put terminal in raw mode
        try{
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch(IOException| InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    private static void unsetRaw(){
        // restore terminal to cooked mode
        try{
            String[] cmd = {"/bin/sh", "-c", "stty sane </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch(IOException| InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    static final int RIGHT = 0, LEFT = 1;
    
    public static int readArrow() throws IOException{
        int ch;
        StringBuilder arrow = new StringBuilder();
        do{
            try{
                Thread.sleep(2000);
            } catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
            System.out.print(Const.CURSORINITLINE);
            System.out.print(Const.ERASELINE);
            ch = in.read();
            System.out.print(arrow.append((char)ch).toString());
            
            System.out.print(Const.NEXTLINE);
            System.out.print(Const.CURSORINITLINE);
            System.out.print(Const.ERASELINE);
            System.out.print(arrow.toString().equals(LEFT));
            System.out.print(Const.PREVIOUSLINE);
            
            if(arrow.length() > 3 || arrow.charAt(0) != Const.ESC){
                System.out.print("Borro-" + arrow.toString());
                arrow.delete(0, arrow.length());
            }
            
            
            
            /*// read arrow key
            ch = in.read();
            if(arrow.append(ch).toString().equals(LEFT))
                return LEFT;
            else if (arrow.toString().equals(RIGHT))
                return RIGHT; 
            else if (arrow.charAt(0) != Const.ESC)
                arrow.delete(0, arrow.length());*/
        } while (ch != '\r');
        return ch;
    }
    
    public static void main(String[] args) throws IOException{
        int arrow;
        ConsolePercent con = null;
        Value value = null;
        
        try{
            setRaw();
            value = new Value();
            con = new ConsolePercent(value);
            value.addObserver(con);
           
            while((arrow = readArrow()) != '\r')
                if (arrow == RIGHT)
                    value.inc();
                else
                    value.dec();
        }finally{
            unsetRaw();
            // clean-up console
        }
    }
}
