package ConsoleBars;

/**
 *
 * @author Enric
 */
public class Const {
    // Characters
    public static final int ESC = 27;
    // Sequences
    public static final String CSI = "\033["; // ESC [
    public static final String ERASELINE = "2K";
    public static final String CURSORINITLINE = "G";
    public static final char BEEP = '\007';
    
    // Arrows
    public static final String UP = "279165";       // ESC[A
    public static final String DOWN = "279166";     // ESC[B
    public static final String RIGHT = "279167";    // ESC[C
    public static final String LEFT = "279168";     // ESC[D
}
