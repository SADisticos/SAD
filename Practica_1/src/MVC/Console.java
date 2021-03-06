package MVC;

import java.util.Observable;
import java.util.Observer;
import Other.Const;

/**
 *
 * @author Enric
 */

/* VIEW CLASS */

public class Console implements Observer{
    static enum Opcode{
        BELL, REFRESH;
    }
    
    static class Command{
        Opcode op;
        
        Command(Opcode op){
            this.op = op;
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        Command comm = (Command) arg;
        
        switch(comm.op){
            case BELL:
                System.out.print(Const.BEEP);
                break;
            case REFRESH:
                System.out.print(Const.CURSORINITLINE);
                System.out.print(Const.ERASELINE);
                System.out.print(((Line)o).toString());
                System.out.print(Const.CSI + (((Line)o).getIndex()+1) + "G"); // Absolute Position
                char option = ((Line)o).getInsertMode()?'h':'l';
                System.out.print(Const.CSI + "?12" + option); // Blinking                        
                break;
        }
    }
}
