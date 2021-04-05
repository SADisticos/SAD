package MVCProperty;

import Other.Const;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Enric
 */

/* VIEW CLASS */

public class Console implements PropertyChangeListener, java.io.Serializable{
    private int index;
    
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "bell":
                System.out.print(Const.BEEP);
                break;
            
            case "text":
                System.out.print(Const.CURSORINITLINE);
                System.out.print(Const.ERASELINE);
                System.out.print(evt.getNewValue());
                System.out.print(Const.CSI + (index+1) + "G"); // Absolute Position
                break;
                
            case "index":
                index = (int) evt.getNewValue();
                System.out.print(Const.CSI + (index+1) + "G");
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
