package Proves;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import ConsoleBars.Const;

public class getASCII{
	public static void main(String[] args) throws IOException, InterruptedException{
		String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
		Runtime.getRuntime().exec(cmd).waitFor();
		Reader reader = System.console().reader();
		int character1 = 0;
		int character2 = 0; // saved character
		while((character1 = reader.read()) != 0x0D) // Carriage Return
			if(character1 != character2){
				System.out.print(Const.CSI + Const.ERASELINE);
				System.out.print(Const.CSI + Const.CURSORINITLINE);
				System.out.print(character1);
				character2 = character1;
			}
		cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
		Runtime.getRuntime().exec(cmd).waitFor();
	}
}
