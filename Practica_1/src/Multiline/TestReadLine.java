package Multiline;

import Other.Const;
import java.io.*;
/**
 *
 * @author enric
 */

public class TestReadLine {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args){
        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;
        EditableBufferedReader.setRaw();
        init();
        try{
            str = in.readLine();
        } catch(IOException ex){
            ex.printStackTrace();
        }finally{
            EditableBufferedReader.unsetRaw();
        }
        System.out.print(Const.POSINIT);
        System.out.print(Const.CLEAR);
        System.out.println("line is: " + str);
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    private static void init(){
        try{
            System.out.print(Const.CLEAR);
            System.out.print(Const.POSINIT);
            System.out.print("There are special characters. Be careful because some special char combination may be not available.");
            Thread.sleep(2000);
            System.out.print(Const.CLEAR);
            System.out.print(Const.POSINIT);
        } catch(InterruptedException ex){
            ex.printStackTrace();
            System.exit(0);
        }     
    }
}
