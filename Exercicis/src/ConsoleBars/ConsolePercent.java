package ConsoleBars;

import java.util.*;
import java.io.*;

class ConsolePercent implements Observer {
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
  
	ConsolePercent(Value value) {
		model = value;
		
		// get max columns
		cmax = Proves.TerminalWidth.getColumns();
		// write 0%
                model.setMax(cmax);
		System.out.println(model.get() + "%");      // Starts at 0
	}
	
	int getMax() {
		return cmax;
	}
	
        @Override
	public void update(Observable o, Object arg) {
		restore();
		Command comm = (Command) arg;
		switch (comm.op) {
			case INC:
                            model.inc();
                            break;
			case DEC:
                            model.dec();
                            break;
			case BELL:
                            System.out.print(BEEP);
                            break;
		}
                System.out.println(model.get() + "%");
	}
        
        private static final String CSI = "\033[";
        private static final String ERASELINE = "2K";
        private static final String CURSORINITLINE = "G";
        private static final char BEEP = '\007';
        
        private void restore(){
            System.out.print(CSI + ERASELINE);
            System.out.print(CSI + CURSORINITLINE);
        }
}
