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
		int ch = 0;  // character
		int sch = 0; // saved character
		StringBuilder line = new StringBuilder();
		System.out.println("Linea 1");
		System.out.print(Const.CURSORINITLINE);
		System.out.print("Linea 2");
		System.out.print(Const.PREVIOUSLINE);
		Thread.sleep(2000);
		while((ch = reader.read()) != 0x0D){ // Carriage Return
			line.append((char) ch); // For special character supress (char) and convert on ASCII table
			System.out.print(Const.ERASELINE);
			System.out.print(line.toString());
			System.out.print(Const.CURSORINITLINE);
			
			if(ch != sch ){
				System.out.print(Const.NEXTLINE);
				System.out.print(Const.ERASELINE);
				System.out.print(ch);
				System.out.print(Const.PREVIOUSLINE);
				sch = ch;
			}
		}
		cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
		Runtime.getRuntime().exec(cmd).waitFor();
	}
}
