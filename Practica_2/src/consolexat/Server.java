/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolexat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static void main(String[] args) {
        Map<String, MySocket> m = new ConcurrentHashMap<String,MySocket>();
        MyServerSocket ss = new MyServerSocket(40000);
        
        while(true){
            MySocket s = ss.accept();
            
            new Thread() {
                public void run() {
                    try{
                    String nick = s.readLine();
                    m.put(nick,s);
                    System.out.println(" Conexión Establecida ");

                    String line;
                    while ((line = s.readLine()) != null) {
                        System.out.println("Mensaje recibido en el servidor: " + line);
                        for (String n : m.keySet()){
                            if(n != nick){
                                m.get(n).writeLine(nick+": " + line);
                                System.out.println("Mensaje reenviado a: " + n);
                            }
                        }  
                    }
                    System.out.println(" Conexión Finalizada: " + nick);
                    m.remove(nick);
                    s.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                }
            }.start(); 
    }
}
}