package Basic;

import java.io.*;
/**
 *
 * @author enric
 */

public class TestReadLine {
    public static void main(String[] args){
        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;
        try{
            EditableBufferedReader.setRaw();
            str = in.readLine();
        } catch(IOException ex){
            ex.printStackTrace();
        }finally{
            EditableBufferedReader.unsetRaw();
        }
        System.out.println("\nline is: " + str);
    }
}
