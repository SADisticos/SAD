package View;

import java.io.BufferedReader;
import View.Const.ConstTerm;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
    private final String line;
    private BufferedReader in;
    private static final String SEP = " > ";
    
    public Terminal(String u) {
        in = new BufferedReader(new InputStreamReader(System.in));
        line = ConstTerm.RED + u + SEP + ConstTerm.RESET;
        System.out.print(line);
    }
    
    public void newMessage(String[] msg) {
        System.out.print(ConstTerm.DELETE);
        System.out.println(ConstTerm.CR + ConstTerm.GREEN + msg[0] + SEP + ConstTerm.RESET + msg[2]);
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
}
