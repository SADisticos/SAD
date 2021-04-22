/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolexat;
    import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
/**
 *
 * @author alber
 */
    public class MyServerSocket extends ServerSocket {
     /* PART DEL CODI QUE FA REFERÈNCIA A LA CLASSE SERVERSOCKET, MÈTODES PRINCIPALS SOBREESCRITS */
//---------------------------------------------------------------------------------------------------------------------------------------------------

    public MyServerSocket(int port) throws IOException {
        super(port);
    }
    
    public MyServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException{
        super(port, backlog, bindAddr);
    }
    
      //  Escolta la connexió a aquest sockert i l’accepta.
    
    public Socket accept(){
        try{
            return super.accept();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public MySocket myAccept(){
        try{
            Socket socket = super.accept();
		    return new MySocket(socket);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    
        // Retorna el canal
    public ServerSocketChannel getChannel(){
        return super.getChannel();
    }

   
      //  Tanca el connector de servidor actual
    public void close(){
        try{
            super.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
     public MySocket myClose(){
        try{
        Socket MySocket = super.close();
	    return new MySocket(socket);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
        //Enllaça el socket a una adreça específica (IP + PORT)
    public void bind(SocketAddress endpoint, int backlog){
        try{
            super.bind(endpoint, backlog);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public InetAddress getInetAddress(){
            return super.getInetAddress();
    }

    public boolean isClosed(){
        return super.isClosed();
    }

    public void setReceiveBufferSize(int size){
        try{
            super.setReceiveBufferSize(size);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setSoTimeout(int timeout){
        try{
            super.setSoTimeout(timeout);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}

    
    
    
     /* PART DEL CODI QUE FA REFERÈNCIA A LES CLASSES D'ESCRIPUTRA I LECTURA , MÈTODES PRINCIPALS SOBREESCRITS */
 //---------------------------------------------------------------------------------------------------------------------------------------------------    
    
    
    
    
    
    
    /* MÈTODES COMPLEMENTARIS PER AJUDAR A QUE TOT FUNCIONI CORRECTAMENT I SIGUI SENZILL */
 //---------------------------------------------------------------------------------------------------------------------------------------------------
    
    

