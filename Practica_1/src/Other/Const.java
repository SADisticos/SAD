package Other;

/**
 *
 * @author Enric
 */
public class Const {
    // Special Characters
    public static final int ESC = 0x1b;         // Escape
    public static final char CR = 0x0D;         // Carriage Return
    public static final char LF = 0x0A;         // Line feed
    public static final char BS = 0x08;         // Backspace
    public static final char DEL = 0x7f;        // Delete
    
    // Other Character (ASCII + Other)
    public static final char BEEP = '\007';
    public static final char BLOCK = 0x2588;

    
    // Sequences
    public static final String CSI = "\033["; // ESC [
    public static final String ERASELINE = CSI + "2K";
    public static final String CURSORINITLINE = CSI + "G";
    public static final String NEXTLINE = CSI + "E";
    public static final String PREVIOUSLINE = CSI + "F";
    public static final String BACKWARD = CSI + "D";
    public static final String DELETECHAR = CSI + "P";
    
    // CSI (ESC[) keys
    public static final char UP  = 'A';
    public static final char DOWN = 'B';
    public static final char RIGHT = 'C';
    public static final char LEFT = 'D';
    public static final char BEGIN = 'E';
    public static final char END = 'F';
    public static final char HOME = 'H';
    public static final char INS = '0'; // SPECIAL - NOT ASCII
    
}
