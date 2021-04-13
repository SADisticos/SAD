package Toolkit;

import java.io.IOException;

/**
 *
 * @author enric
 */
public class CookedRaw {
    public static void setRaw(){
        try{
            Runtime.getRuntime().exec("/bin/stty -f /dev/tty -echo raw").waitFor();
        } catch(IOException|InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static void unsetRaw(){
        try{
            Runtime.getRuntime().exec("/bin/stty -f /dev/tty -echo cooked").waitFor();
        } catch(IOException|InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
}
