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
	System.out.print(comm.op);
	switch (comm.op) {
            case INC:
		System.out.print("Hola1");
                model.inc();
                break;
            case DEC:
		System.out.print("Hola2");
                model.dec();
                break;
            case BELL:
		System.out.print("Hola3");
                System.out.print(Const.BEEP);
                break;
            }
	System.out.print("Hola4");
        System.out.print(model.get() + "%");
    }
        
    private void restore(){
        //System.out.print(Const.ERASELINE);
        //System.out.print(Const.CURSORINITLINE);
    }
}
