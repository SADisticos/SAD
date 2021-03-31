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
        
    }
    
    public static int getColumns(){
        try{
            Process p = Runtime.getRuntime().exec("tput cols");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        } catch(IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
        
    }
}
