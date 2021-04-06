package MVCProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import Other.Const;
import Other.TerminalWidth;
import Other.Trie;
import Other.Trie.TrieNode;


/**
 *
 * @author Enric
 */

/* CONTROLLER CLASS */

public class EditableBufferedReader extends BufferedReader{
    private Line line;
    private Console con;
    private TrieNode root;
    /**
     * Editable buffering-input stream which uses default-sized input buffer (8 Kb)
     * @param in
     */
    public EditableBufferedReader(Reader in) {
        super(in);
        line = new Line(TerminalWidth.getColumns());
        con = new Console();
        line.addPropertyChangeListener(con);
        root = new TrieNode();
        Trie.initTree();
        
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
        
        StringBuilder str = new StringBuilder();
        int option = Const.option.YES;
        
        while(option == Const.option.YES){
            option = Trie.search(str.append(super.read()).toString());
        }
        if(option == Const.option.NO)
            return str.charAt(str.length());
        return option;
    }
    
    @Override
    public String readLine() throws IOException{
        int ch;
        
        while ((ch = read()) != Const.option.ESC){
            switch(ch){
                //case Const.Option.UP:
                //    break;
                //case Const.Option.DOWN:
                //    break;
                case Const.option.RIGHT:
                    line.moveCursorForward();
                    break;
                case Const.option.LEFT:
                    line.moveCursorBackward();
                    break;
                case Const.option.SUPR:
                    line.deleteCharForward();
                    break;
                case Const.option.DEL: // BackSpace 
                    line.deleteCharBackward();
                    break;
                case Const.option.INS:
                    line.insert();
                    break;
                case Const.option.HOME:
                    line.cursorAtStart();
                    break;
                case Const.option.END:
                    line.cursorAtEnd();
                    break;
                default:
                    line.addChar((char) ch);
                    break;
            }
        }
        return line.toString();
    }
}
