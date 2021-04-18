package Multiline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import Other.Const;
import Other.Trie;


/**
 *
 * @author Enric
 */

/* CONTROLLER CLASS */

public class EditableBufferedReader extends BufferedReader{
    private final MultiLine multiLine;
    private final Console con;
    private final Trie trie;
    /**
     * Editable buffering-input stream which uses default-sized input buffer (8 Kb)
     * @param in
     */
    public EditableBufferedReader(Reader in) {
        super(in);
        multiLine = new MultiLine();
        con = new Console();
        multiLine.addPropertyChangeListener(con);
        trie = new Trie(Const.trieMap.trieMap);
    }
    
    /**
     * Put terminal in raw mode
     */
    public static void setRaw(){
        try{
            Runtime.getRuntime().exec("/bin/stty -f /dev/tty -echo raw").waitFor();
        } catch(IOException| InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Restore terminal to cooked mode
     */
    public static void unsetRaw(){
        try{
            Runtime.getRuntime().exec("/bin/stty -f /dev/tty -echo cooked").waitFor();
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
    @SuppressWarnings("empty-statement")
    public int read() throws IOException{
        
        StringBuilder str = new StringBuilder();
        int option;
        
        while((option = trie.search(str.append((char) super.read()).toString())) == Const.option.YES);
        
        if(option == Const.option.NO)
            return str.charAt(str.length()-1);
        return option;
    }
    
    @Override
    public String readLine() throws IOException{
        int ch;
        
        while ((ch = read()) != Const.option.ESC){
            switch(ch){
                case Const.option.UP -> multiLine.moveCursorUp();
                case Const.option.DOWN -> multiLine.moveCursorDown();
                case Const.option.RIGHT -> multiLine.moveCursorForward();
                case Const.option.LEFT -> multiLine.moveCursorBackward();
                case Const.option.SUPR -> multiLine.deleteCharForward();
                case Const.option.DEL -> multiLine.deleteCharBackward();  // BackSpace
                case Const.option.INS -> multiLine.insert();
                case Const.option.HOME -> multiLine.cursorAtStart();
                case Const.option.END -> multiLine.cursorAtEnd();
                case Const.option.TAB -> {
                    for(int i = 0; i < 5; i++)
                        multiLine.addChar(' ');
                }
                default -> multiLine.addChar((char) ch);
            }
        }
        return multiLine.toString();
    }
}
