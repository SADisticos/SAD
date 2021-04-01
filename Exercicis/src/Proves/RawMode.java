package Proves;

import ConsoleBars.Const;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enric
 */
public class RawMode {
    public static void main(String[] args){
        //System.out.print(Const.CSI + "3h");
        try{
            Runtime.getRuntime().exec("echo hola");
        } catch (IOException ex) {
            Logger.getLogger(RawMode.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            System.out.print("Pollas");
        }
    }
}
