package Proves;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Enric
 */
public class TerminalWidth {
    public static void main(String[] args){
        System.out.println(getColumns()); 
    }
    
    public static int getColumns(){
        try{
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "tput cols 2> /dev/tty"});
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            String line = reader.readLine();  // There is only one line to read
            return Integer.parseInt(line);
        } catch(IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
        return 0; // To make the compiler shut up
    }
}
