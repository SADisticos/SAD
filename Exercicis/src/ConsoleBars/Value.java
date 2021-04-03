package ConsoleBars;

import java.util.*;

// Modificar ConsolePercent a ConsoleBar y viceversa

class Value extends Observable {
  int value, max;
  
  Value() {
    value = 0;
  }
  
  void inc() {
    setChanged();
    if (value == max){
      notifyObservers(new ConsolePercent.Command(ConsolePercent.Opcode.BELL)); // push style
    }else {
      value++;
      System.out.println("Valor: " + value + "/" + max);
      notifyObservers(new ConsolePercent.Command(ConsolePercent.Opcode.INC));
    }
  }
  
  void dec() {
    setChanged();
    if (value == 0)
      notifyObservers(new ConsolePercent.Command(ConsolePercent.Opcode.BELL));
    else {
      value--;
      notifyObservers(new ConsolePercent.Command(ConsolePercent.Opcode.DEC));
    }
  }
  
  int get() {
  	return value;
  }
  
  void setMax(int max) {
    this.max = max;
  }
}
