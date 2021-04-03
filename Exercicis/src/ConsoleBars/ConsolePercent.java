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
        System.out.print(model.get() + "%");      // Starts at 0
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
            case DEC:
                break;
            case BELL:
                System.out.print(Const.BEEP);
                break;
        }
    }
        
    private void restore(){
        System.out.print(Const.ERASELINE);
        System.out.print(Const.CURSORINITLINE);
        System.out.print((int)model.get()/100 + "%");
    }
}
