/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolexat;

/**
 *
 * @author alber
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;


public class MySocket{

    BufferedReader bf;
    PrintWriter pw;
    String nick;
    Socket sock;
  
   /* PART DEL CODI QUE FA REFERÈNCIA A LA CLASSE SOCKET, MÈTODES PRINCIPALS SOBREESCRITS */
//---------------------------------------------------------------------------------------------------------------------------------------------------
    //He creat 2 constructors bàscis
     public MySocket(Socket s) {
	    try {
	           sock = s;
	           bf = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	           pw = new PrintWriter(sock.getOutputStream());
                }
	    catch (IOException  e) {
	          System.out.println(e);
	    }
	}
    //Per crear un MySocket amb un nick, un nicke de servidor i el port(Normalment utilitzarem aquest)
    public MySocket(String n, String host, int port) {
            try {
                   nick = n;
                   sock = new Socket(host,port);
                   bf = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                   pw = new PrintWriter(sock.getOutputStream());
                }   
            catch (IOException e) {
                   e.printStackTrace();
        }  
    }
     //Per conectar el Socket amb el respectiu server
  public void setReceiveBufferSize(int size) throws SocketException{
      try {      
      sock.setReceiveBufferSize(size);
      }
      catch (SocketException e){
                e.printStackTrace();
            }        
  }
    public int getReceiveBufferSize() throws SocketException{
        return sock.getReceiveBufferSize();
    }
    
    /* PART DEL CODI QUE FA REFERÈNCIA A LES CLASSES D'ESCRIPUTRA I LECTURA , MÈTODES PRINCIPALS SOBREESCRITS */
 //---------------------------------------------------------------------------------------------------------------------------------------------------    
    //Per llegir
   public String readLine() {
       String s = "";
       try {
           s = bf.readLine();
       } catch (IOException e) {
           return null;
       }
       return s;
   }
    //Per escriure 
  
  public void writeLine(String s) {
       if(s!=null){
            pw.println(s);
            pw.flush();
   }
   }  
   /* MÈTODES COMPLEMENTARIS PER AJUDAR A QUE TOT FUNCIONI CORRECTAMENT I SIGUI SENZILL */
 //---------------------------------------------------------------------------------------------------------------------------------------------------

   //Per finalitzar la conexió, no es podra utilitzar el xat una vegada fet el close()
    public void close() {
        try {
              pw.close();
              bf.close();
              sock.close();
       } 
       catch (Exception e) {
          System.out.println(e);
       }
   }
   
   public Socket getSocket() {
       return sock;
   }
   
   public String getNick(){
        return nick;
        }
   
   
}