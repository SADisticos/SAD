package ConsoleBars;

import java.util.*;
import java.io.*;

class ConsoleBar implements Observer {
	static enum Opcode {
	  INC, DEC, BELL
	}

	static class Command {
	  Opcode op;
	  
	  Command(Opcode op) {
	    this.op = op;
	  }
	}

	int cmax;
	Value model;
	static final char BLOCK = 0x2588; // ASCII Character
  
	ConsoleBar(Value value) {
		model = value;
		
		// get max columns
                cmax = Proves.TerminalWidth.getColumns();
                model.setMax(cmax);
	}
	
	int getMax() {
		return cmax;
	}
	
	public void update(Observable o, Object arg) {
		Command comm = (Command) arg;
		switch (comm.op) {
			case INC:
				System.out.print(BLOCK);
				break;
			case DEC:
                                // delete char to the left
                                System.out.print(Const.BACKWARD);
                                System.out.print(Const.DELETECHAR);
				break;
			case BELL:
				System.out.print('\007');
				break;
		}
	}
}

