package Chat;

public class Style {
    private static final String SEP = ";;";
    public static final String BROADCAST = "ALL";
    
    public static String format(String snd, String rcv, String l) {
        return snd + SEP + rcv + SEP + l + SEP;
    }
    
    public static String addColor(String l, String color) {
        return l + SEP + color + SEP;
    }
    
    public static String initColor(String nick, String color){
        return nick + SEP + color + SEP;
    }
    
    public static String[] parse(String msg) {
        return msg.split(SEP);
    }
    
}
