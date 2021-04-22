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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class MySocket{

    BufferedReader bf;
    PrintWriter pw;
    String nick;
    Socket sock;
  
   /* PART DEL CODI QUE FA REFERÈNCIA A LA CLASSE SOCKET, MÈTODES PRINCIPALS SOBREESCRITS */
//---------------------------------------------------------------------------------------------------------------------------------------------------
    //He creat 3 constructors bàscis
    
    
    //Ens pasem com a paràmetre el nick del servidor i el port pel qual es fa la conexxió
    public MySocket(String host, int port){
        try {
            sock = new Socket(host, port);
            bf = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Per crear un MySocket amb un nick, un nicke de servidor i el port(Normalment utilitzarem aquest)
    public MySocket(String nick, String host, int port) {
            try {
                   sock = new Socket(host, port);
                   bf = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                   pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
                }   
            catch (IOException e) {
                   e.printStackTrace();
        }
        this.nick = nick;
    }
    
    public MySocket(Socket s) {
	    try {
	           sock = s;
	           bf = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	           pw = new PrintWriter(sock.getOutputStream());
                }
	    catch (IOException e) {
	          System.out.println(e);
	    }
	}
     //Per conectar el Socket amb el respectiu server
    public void connect(SocketAddress endpoint) {
           try {
                  sock.connect(endpoint);
                } 
           catch (IOException e) {
                  e.printStackTrace();
        }
    }
    
    public void bind(SocketAddress bindpoint) throws IOException {
            try {
                sock.bind(bindpoint);
                }
            catch (IOException e){
                e.printStackTrace();
            }        
    }
    
    public SocketAddress getRemoteSocketAddress(){
          
        return sock.getRemoteSocketAddress();
    }
        
    public SocketAddress getLocalSocketAddress(){
          
        return sock.getLocalSocketAddress();
    }
    
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
    //Per llegir un caracter
    public int read() {
        try {
            return bf.read();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    //Per escriure un sol caracter
    public void write(String string) {
        pw.write(string);
    }
  
   
   public String readLine() {
       String s = null;
       try {
           s = this.bf.readLine();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return s;
   }
   
   
   public void writeLine(String s) {
       this.pw.write(s);
   }
   
   /* MÈTODES COMPLEMENTARIS PER AJUDAR A QUE TOT FUNCIONI CORRECTAMENT I SIGUI SENZILL */
 //---------------------------------------------------------------------------------------------------------------------------------------------------

   //Per finalitzar la conexió, no es podra utilitzar pel xat una vegada fet el close()
    public void close() {
        try {
              this.pw.close();
              this.bf.close();
              this.sock.close();
       } 
       catch (IOException e) {
          System.out.println(e);
       }
   }
   
   public Socket getSocket() {
       return this.sock;
   }
   
   public void sendNick() {
       this.pw.println(this.nick);
       System.out.println("Nick enviat: " + this.nick);
   }
   
   public String receiveNick() {
       return this.readLine();
   }
   //Per afegir el nom del nick
   public String setNick() {
       this.nick = this.readLine();
       return this.nick;
    }

}