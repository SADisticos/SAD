package ConsoleBars;

import java.io.*;
import static java.lang.System.in;

/**
 *
 * @author Enric
 *
 * BUGS -> SOLUTION
 *       · Double bell -> Only one class and not reuse
 *       · Not respect at all MVC standard -> Only one class
 *       · Separated ValuePercent and ValueBar -> Only one class
 *       · Init show Percent up -> Only one class 
 */
public class SlideBarPercent {

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
        
        do{
            // read arrow key
            if((ch = in.read()) == Const.ESC)
                if((ch = in.read()) == '[')
                    if ((ch = in.read()) == 'C')
                        return RIGHT;
                    else if (ch == 'D')
                        return LEFT;
        } while (ch != '\r');
        return ch;
    }
    
    public static void main(String[] args) throws IOException{
        int arrow;
        ConsoleBar conBar = null;
        ConsolePercent conPer = null;
        ValuePercent valuePer = null;
        ValueBar valueBar = null;
        
        try{
            setRaw();
            
            valuePer = new ValuePercent();
            conPer = new ConsolePercent(valuePer);
            valuePer.addObserver(conPer);
            
            valueBar = new ValueBar();
            conBar = new ConsoleBar(valueBar);
            valueBar.addObserver(conBar);
            
            while((arrow = readArrow()) != '\r')
                if (arrow == RIGHT){
                    valueBar.inc();
                    System.out.print(Const.NEXTLINE); // Mala praxis (no seguir MVC pero hay que cambiar todo sino)
                    valuePer.inc();
                    System.out.print(Const.PREVIOUSLINE);
                    System.out.print(Const.CSI + valueBar.get() +'G'); // Move Absolute row
                }
                else{
                    valueBar.dec();
                    System.out.print(Const.NEXTLINE);
                    valuePer.dec();
                    System.out.print(Const.PREVIOUSLINE);
                    System.out.print(Const.CSI + valueBar.get() + 'G');
                }
        } finally {
            unsetRaw();
        }
    }
}
