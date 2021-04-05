package Proves;

import ConsoleBars.Const;

public class index{
    public static void main(String[] args) throws InterruptedException{
        System.out.println("0123456789");
        Thread.sleep(2000);
        System.out.print(text());
        Thread.sleep(2000);
    }
    
    private static String text(){
        String text = new String("");
        text += Const.CURSORINITLINE;
        text += Const.ERASELINE;
        text += "abcdefghij";
        text += Const.CSI + 3 + "G";
        return text;
    }
}   
