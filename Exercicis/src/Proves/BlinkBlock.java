package Proves;

import ConsoleBars.Const;

public class BlinkBlock{
    public static void main (String[] args) throws InterruptedException{
        for(int i = 0; i < 3; i++)
            System.out.print(Const.BLOCK);
        Thread.sleep(2000);
        for(int i = 0; i < 2; i++){
            System.out.print(Const.CSI + "D");
            System.out.print(Const.CSI + "P");
        }
    }
}
