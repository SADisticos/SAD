package Proves;

import Toolkit.CookedRaw;
import Toolkit.Const;
import Toolkit.TerminalWidth;
/**
 *
 * @author enric
 */
public class TerminalPosition {
    public static void main(String[] args) throws InterruptedException{
        CookedRaw.setRaw();
        System.out.print(Const.CSI + "2J");
        System.out.print(Const.CSI + "1;" + (TerminalWidth.getColumns()+1) + "H");
        System.out.print("Hola");
        Thread.sleep(2000);
        CookedRaw.unsetRaw();
    }
}