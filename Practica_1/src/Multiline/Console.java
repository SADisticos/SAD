package Multiline;

import Other.Const;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Enric
 */

/* VIEW CLASS */

public class Console implements PropertyChangeListener, java.io.Serializable{
    private Position pos = new Position(Other.TerminalWidth.getColumns());
    
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "bell":
                System.out.print(Const.BEEP);
                break;
            
            case "text":
                System.out.print(Const.CLEAR);
                System.out.print(Const.POSINIT); // Position [1;1]
                System.out.print((String)evt.getNewValue());
                System.out.print(Const.CSI + pos.getRow() + ";" + pos.getCol() + "H");
                break;

                
            case "pos":
                pos = (Position) evt.getNewValue();
                System.out.print(Const.CSI + pos.getRow() + ";" + pos.getCol() + "H");
                break;

                
            case "insert":
                if((boolean) evt.getNewValue())
                    System.out.print(Const.STARTBLINKCURSOR);
                else
                    System.out.print(Const.STOPBLINKCURSOR);
                break;
        }
    }
}
