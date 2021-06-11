/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolexat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Client {
    BufferedReader bf;

    public  Client(){
        bf = new BufferedReader(new InputStreamReader(System.in));
    }

    //Aquí creo els dos threads que ens demana pel client, el de input i el de output
    public void threadInput(MySocket soc) throws IOException {
    String st;
    try{
        while((st=bf.readLine()) != null){
            soc.writeLine(st);
            System.out.println(" Mensaje enviado ");  
        }
    }catch(NullPointerException e){
           soc.close();   
    }
    System.out.println(" Conexión cerrada ");   
    }

    public void threadOutput(MySocket soc){
        try{
        String lectura = soc.readLine();
        System.out.println( lectura.split(":")[0]+":"+" "+lectura.split(":")[1]+"\n"); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

public static void main(String[] args){
    Client ec = new Client();
    System.out.print("Introduce tu nick: ");
    String nick = "";
    try{
        nick = ec.bf.readLine();
        System.out.println("Bienvenido:  " + nick );
    }catch(Exception e){
        e.printStackTrace();
    }
    MySocket soc = new MySocket(nick, "localhost" , 40000);
    soc.writeLine(nick);

    new Thread(){
        public void run(){
            try{
                    ec.threadInput(soc);
            }catch(Exception e ){
                e.printStackTrace();
            }
        }
    }.start();

    new Thread(){
        public void run(){
            while(true){
                ec.threadOutput(soc); 
            }         
        }
    }.start();


}

}