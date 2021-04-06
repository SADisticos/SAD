package Other;

/**
 *
 * @author Enric
 */
public class Const {
    // Special Characters
    public static final byte ESC = 0x1B;         // Escape
    public static final byte CR = 0x0D;         // Carriage Return
    public static final byte LF = 0x0A;         // Line feed
    public static final byte DEL = 0x7F;        // Delete
    
    // Special F- Characters
    public static final byte F1 = 0x50;  // F1 (P)
    public static final byte F2 = 0x51;  // F2 (Q)
    public static final byte F3 = 0x52;  // F3 (R)
    
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
    
    
    // Entry sequences + CSI
    public static final char UP  = 'A';
    public static final char DOWN = 'B';
    public static final char RIGHT = 'C';
    public static final char LEFT = 'D';
        
    // Entry sequences for Trie
    public static class Trie{
        public static final String ESC = String.valueOf(Const.ESC);
        public static final String CR = String.valueOf(Const.CR);
        public static final String LF = String.valueOf(Const.LF);
        public static final String DEL = String.valueOf(Const.DEL);
        public static final String SUPR = CSI + "3~";
        public static final String UP  = CSI + 'A';
        public static final String DOWN = CSI + 'B';
        public static final String RIGHT = CSI + 'C';
        public static final String LEFT = CSI + 'D';
        public static final String F1 = ESC + "O" + Const.F1;
        public static final String F2 = ESC + "O" + Const.F2;
        public static final String F3 = ESC + "O" + Const.F3;
    }
    
    public static class Option{
        public static final int ESC = -1;
        public static final int UP = -2;
        public static final int DOWN = -3;
        public static final int RIGHT = -4;
        public static final int LEFT = -5;
        public static final int END = -6;
        public static final int DEL = -7;
        public static final int INS = -8;
        public static final int HOME = -9;
        public static final int SUPR = -10;
    }
    
}
