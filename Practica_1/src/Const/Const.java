package Const;

/**
 *
 * @author Enric
 */
public class Const {
    // Characters
    public static final int ESC = 27;
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
    
    // Arrows
    public static final String UP = "279165";       // ESC[A
    public static final String DOWN = "279166";     // ESC[B
    public static final String RIGHT = CSI + "C";    // ESC[C
    public static final String LEFT = CSI + "D";     // ESC[D
}
