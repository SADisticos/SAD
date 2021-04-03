package Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import Const.Const;

/**
 *
 * @author Enric
 */
public class EditableBufferedReader extends BufferedReader{
    
    /**
     * Editable buffering-input stream which uses default-sized input buffer (8 Kb)
     * @param in
     */
    public EditableBufferedReader(Reader in) {
        super(in);
    }
    
    /**
     * Put terminal in raw mode
     */
    public static void setRaw(){
        try{
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch(IOException| InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Restore terminal to cooked mode
     */
    public static void unsetRaw(){
        try{
            String[] cmd = {"/bin/sh", "-c", "stty sane </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch(IOException| InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Reads a single characters. Uses parsing method to know if it is character or special simbol
     * 
     * @return The character read as an integer or -1 if the end of the stream has been reached
     */
    @Override
    public int read() throws IOException{
        
        int ch;
        // Non special simbols
        if ((ch = super.read()) != Const.ESC)
            
        
        return 0;
    }
    
    @Override
    public String readLine(){return null;}
}
