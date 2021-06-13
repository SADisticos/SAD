package MySockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MySocket {
    private Socket sck;
    private BufferedReader in;
    private PrintWriter out;
    
    
    public MySocket(String host, int port){
        try{
            sck = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(sck.getInputStream()));
            out = new PrintWriter(sck.getOutputStream(), true);
        } catch (IOException ex){ 
            sck = null;
        } 
    }
     
    
    public MySocket(Socket sck) {
        try{
            this.sck = sck;
            in = new BufferedReader(new InputStreamReader(sck.getInputStream()));
            out = new PrintWriter(sck.getOutputStream(), true);
        } catch (IOException ex){
            sck = null;
        }
    }
    
    
    public boolean isClosed() {
        return sck != null ? sck.isClosed() : true;
    }
    
    public void close() {
        if(sck != null)
            try{
                sck.close();
            }catch (IOException ex) {}
    }
            
    public String readLine(){
        try{
            return in.readLine();
        } catch(IOException e) {
            this.close();
            return null;
        }
    }
    
    public void println(String str){
        if(sck != null){
            out.println(str);
            out.flush();
        }
    }
}