package Proves;

import ConsoleBars.Const;
import java.io.IOException;

/**
 *
 * @author Enric
 */
public class RawMode {
    public static void main(String[] args) throws IOException{
        //System.out.print(Const.CSI + "3h");
        Runtime.getRuntime().exec("echo hola");
        
    }
}
