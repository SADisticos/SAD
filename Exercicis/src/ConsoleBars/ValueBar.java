package ConsoleBars;

import java.util.*;

// Modificar ConsolePercent a ConsoleBar y viceversa

class ValueBar extends Observable {
  int value, max;
  
  ValueBar() {
    value = 0;
  }
  
  void inc() {
    setChanged();
    if (value == max){
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.BELL)); // push style
    }else {
      value++;
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.INC));
    }
  }
  
  void dec() {
    setChanged();
    if (value == 0)
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.BELL));
    else {
      value--;
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.DEC));
    }
  }
  
  int get() {
  	return value;
  }
  
  void setMax(int max) {
    this.max = max;
  }
}