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
    public static final String STARTBLINKCURSOR = CSI + "?12h";
    public static final String STOPBLINKCURSOR = CSI + "?12l";
    public static final String MOVEFORWARD = CSI + "C";
    public static final String MOVEBACKWARD = CSI + "D";
    
    // CSI (ESC[) keys
    public static final char UP  = 'A';
    public static final char DOWN = 'B';
    public static final char RIGHT = 'C';
    public static final char LEFT = 'D';
    public static final char BEGIN = 'E';
    public static final char END = 'F';
    public static final char HOME = 'H';
    public static final char INS = '0'; // SPECIAL - NOT ASCII
    
    public static class Key{
        public static final int ESC = -1;
        public static final int UP = -2;
        public static final int DOWN = -3;
        public static final int RIGHT = -4;
        public static final int LEFT = -5;
        public static final int END = -6;
        public static final int DEL = -7;
        public static final int INS = -8;
        public static final int HOME = -9;
        public static final int BS = -10;
    }
    
}
