package Proves;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Enric
 */
public class RawMode {
    public static void main(String[] args) throws IOException, InterruptedException{
        /*  Alternative using sh as an interpreter  */
        // String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"}; 
        
        /* Direct mode (without interpreter) */
        String cmd = "/bin/stty -f /dev/tty -echo raw";
        
        Runtime.getRuntime().exec(cmd).waitFor();
        
        Reader reader = System.console().reader();
        StringBuilder line = new StringBuilder();
        int character = 0;
        System.out.print("Enter a phrase: ");
        while((character = reader.read()) != 0x0D)  // Carriage return
            line.append((char)character);
	System.out.println(line.toString());
        //cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
        cmd = "/bin/stty -f /dev/tty -echo cooked";
        Runtime.getRuntime().exec(cmd).waitFor();
    }
}
