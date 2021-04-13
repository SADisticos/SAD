package Other;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 *
 * @author Enric
 */
public final class Const {
    // Special Characters
    public static final String ESC = "\033";         // Escape
    public static final String CR = "\r";         // Carriage Return
    public static final String LF = "\n";         // Line feed
    public static final char DEL = 127;        // Delete
    public static final String TAB = "\t";      // Tab horizontal
    
    public final static class SpecialChars{
        public static final int ESC = 27;
        public static final int CR = 13;
        public static final int LF = 10;
        public static final int DEL = 127;
    }
    
    public final static class arrows{
        public static final char UP = 'A';
        public static final char DOWN = 'B';
        public static final char RIGHT = 'C';
        public static final char LEFT = 'D';
    }
    
    // Special F- Characters
    public static final char F1 = 'P';  // F1 (P)
    public static final char F2 = 'Q';  // F2 (Q)
    public static final char F3 = 'R';  // F3 (R)
    
    // Other Character (ASCII + Other)
    public static final char BEEP = '\007';
    public static final char BLOCK = 0x2588;

    
    // Sequences Terminal
    public static final String CSI = ESC + "["; // ESC [
    public static final String ERASELINE = CSI + "2K";
    public static final String CURSORINITLINE = CSI + "G";
    public static final String NEXTLINE = CSI + "E";
    public static final String PREVIOUSLINE = CSI + "F";
    public static final String BACKWARD = CSI + "D";
    public static final String DELETECHAR = CSI + "P";
    public static final String STARTBLINKCURSOR = CSI + "?12h";
    public static final String STOPBLINKCURSOR = CSI + "?12l";
    public static final String MOVEFORWARD = CSI + "C";
    public static final String MOVEBACKWARD = CSI + "D";
    public static final String CLEAR = CSI + "2J";
    public static final String POSINIT = CSI + "1;1H";
    
        
    // Entry sequences for Trie
    public final static class sequence{
        public static final String ESC = Const.ESC + Const.ESC;
        public static final String CR = Const.CR;
        public static final String LF = Const.LF;
        public static final String DEL = Character.toString(Const.DEL);
        public static final String SUPR = Const.CSI + "3~";
        public static final String UP  = Const.CSI + 'A';
        public static final String DOWN = Const.CSI + 'B';
        public static final String RIGHT = Const.CSI + 'C';
        public static final String LEFT = Const.CSI + 'D';
        public static final String F1 = Const.ESC + "O" + Const.F1;
        public static final String F2 = Const.ESC + "O" + Const.F2;
        public static final String F3 = Const.ESC + "O" + Const.F3;
        public static final String TAB = Const.TAB;     // Solves TAB bug
    }
    
    public final static class option{ // DO NOT MODIFY - ORDER FOR TRIE 
        public static final int ESC = -1;
        public static final int CR = -1;
        public static final int LF = -1;
        public static final int DEL = -2;
        public static final int SUPR = -3;
        public static final int UP = -4;
        public static final int DOWN = -5;
        public static final int RIGHT = -6;
        public static final int LEFT = -7;
        public static final int HOME = -8;
        public static final int INS = -9;
        public static final int END = -10;
        public static final int TAB = -11;      // Solves TAB bug
        public static final int YES = -1000; // Character for continuing in the trie
        public static final int NO = 0;      // Not continue in trie
    }
    
    public class trieMap {
        public static final HashMap<String, Integer> trieMap = new HashMap<>();
        
        static{
            
            /*      AUTOMATICALLY CREATES THE HASHMAP       */
            /* ArrayList with the constants from SEQUENCE */
            List<String> sequences = Arrays.stream(sequence.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field-> {
                    try{
                        return (String) field.get(sequence.class);
                    } catch(IllegalAccessException ex){
                        throw new RuntimeException(ex);
                    }
                })
                .filter(name -> ! name.equals("NOT_NEEDED_CONSTANT")) // filter out if needed
                .collect(Collectors.toList());
        
            /* ArrayList with the constants from OPTION */
            List<Integer> options = Arrays.stream(option.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field-> {
                    try{
                        return (Integer) field.get(option.class);
                    } catch(IllegalAccessException ex){
                        throw new RuntimeException(ex);
                    }
                })
                .filter(name -> ! name.equals("NOT_NEEDED_CONSTANT")) // filter out if needed
                .collect(Collectors.toList());
        
            ListIterator its = sequences.listIterator();
            ListIterator ito = options.listIterator();
        
            while(its.hasNext() && ito.hasNext())
               trieMap.put((String) its.next(), (Integer) ito.next());
            
            /*trieMap.entrySet().forEach(entry -> {
                System.out.println(entry.getKey() + " " + entry.getValue());
            });*/
        }
    }
    
    
  // PRIVATE //

  /**
   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
   and so on. Thus, the caller should be prevented from constructing objects of 
   this class, by declaring this private constructor. 
  */
  private Const(){
    //this prevents even the native class from 
    //calling this ctor as well :
    throw new AssertionError();
  }
}
