package MVCProperty;

import Other.Const;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Enric
 */

/* VIEW CLASS */

public class Console implements PropertyChangeListener{

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
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
                System.out.print(Const.CSI + (((Line)o).getIndex()+1) + "G");
                break;
        }
    }
}
