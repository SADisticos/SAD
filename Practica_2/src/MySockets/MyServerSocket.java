package MySockets;

import java.io.IOException;
import java.net.ServerSocket;

public class MyServerSocket {
    private ServerSocket ss;
    
    public MyServerSocket(int port) {
        try{
            ss = new ServerSocket(port);
        } catch (IOException ex){
            ss = null;
        }
    }
    
    
    public MySocket accept(){
        try {
            return new MySocket(ss.accept());
        } catch (IOException ex) {}
        return null;   
    }
    
    public boolean isClosed() {
        return ss != null ? ss.isClosed() : true;
    }
}