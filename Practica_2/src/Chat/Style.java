package Chat;

public class Style {
    private static final String SEP = ";;";
    public static final String BROADCAST = "ALL";
    
    public static String format(String snd, String rcv, String l) {
        return snd + SEP + rcv + SEP + l;
    }
    
    public static String[] parse(String msg) {
        return msg.split(SEP);
    }
}
