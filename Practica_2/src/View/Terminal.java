package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
    private String line;
    private BufferedReader in;
    private static final String SEP = " > ";
    private String color = Const.Colors.WHITE;
    private String user;
    
    public Terminal(String user) {
        in = new BufferedReader(new InputStreamReader(System.in));
        this.user = user;
        setLine();
    }
    
    public Terminal(String user, String color) {
        this(user);
        this.color = color;
    }
    
    public void newMessage(String[] msg) {
        System.out.print(Const.Terminal.DELETE);
        System.out.println(Const.Terminal.CR + msg[4] +  msg[0] + SEP + Const.Colors.RESET + msg[2]);
        System.out.print(line);
    }
    
    public String readLine() {
        try{
            String l = in.readLine();
            System.out.print(line);
            return l;
        } catch (IOException ex) {}
        return null;
    }
    
    public void setColor(String color){
        this.color = color;
        setLine();
    }
    
    private void setLine(){
        line = color + user + SEP + Const.Colors.RESET;
        System.out.print(Const.Terminal.DELETE + Const.Terminal.INITLINE);
        System.out.print(line);
    }
    
}
