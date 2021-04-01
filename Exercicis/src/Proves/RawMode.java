package Proves;

import ConsoleBars.Const;
import java.io.Console;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Enric
 */
public class RawMode {
    public static void main(String[] args) throws IOException, InterruptedException{
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        // System.out.print(Const.CSI + "3h");
        Runtime.getRuntime().exec(cmd).waitFor();
        Console console = System.console();
        Reader reader = console.reader();
        StringBuilder password = new StringBuilder();
        System.out.println("Enter your 8 character password: ");
        for (int i = 0; i < 8; i++)
            password.append(reader.read());
        cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
    }
}
