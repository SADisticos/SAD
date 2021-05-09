/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import consolexat.MySocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Client {
    BufferedReader bf;

    public  Client(){
        bf = new BufferedReader(new InputStreamReader(System.in));
    }

    public void threadInput(MySocket sc) throws IOException {
    String s;

    try{
        while((s=bf.readLine()) != null){
            sc.writeLine(s);
            System.out.println(" Mensaje enviado");  
        }
    }catch(NullPointerException ex){
           sc.close();   
    }
    System.out.println(" Conexión cerrada");   
    }

    public void threadOutput(MySocket sc){
        try{
        String lectura = sc.readLine();
        System.out.println("\u001b[31m"+lectura.split(":")[0]+":"+"\u001b[0m"+lectura.split(":")[1]+"\n"); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

public static void main(String[] args){
    Client ec = new Client();
    System.out.print("Introduzca nick: ");
    String nick = "";
    try{
        nick = ec.bf.readLine();
        System.out.println("Bienvenido:  " + nick );
    }catch(Exception e){
        e.printStackTrace();
    }
    MySocket sc = new MySocket(nick, "localhost", 40000);
    sc.writeLine(nick);

    new Thread(){
        public void run(){
            try{
                    ec.threadInput(sc);
            }catch(Exception e ){
                e.printStackTrace();
            }
        }
    }.start();

    new Thread(){
        public void run(){
            while(true){
                ec.threadOutput(sc); 
            }         
        }
    }.start();


}

}