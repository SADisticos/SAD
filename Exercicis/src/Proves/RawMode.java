package Proves;

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
        Runtime.getRuntime().exec(cmd).waitFor();
        Console console = System.console();
        Reader reader = console.reader();
        StringBuilder line = new StringBuilder();
        int character = 0;
        System.out.println("Enter your 8 character password: ");
        while((character = reader.read()) != -1)
            line.append((char)character);
        cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
    }
}
