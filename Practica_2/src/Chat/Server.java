package Chat;

import MySockets.MyServerSocket;
import MySockets.MySocket;
import java.util.HashMap;

public class Server {
    private MyServerSocket ss;
    private HashMap<String, MySocket> clients = new HashMap<String, MySocket>();
    
    public Server(int port) {
        ss = new MyServerSocket(port);
    }
    
    public void run() {
        while(!ss.isClosed()){
            MySocket clSck = ss.accept();
            String nick = clSck.readLine();
            clients.put(nick, clSck);
            new Thread(new Worker(clSck)).start();
        }
    }
    
    private class Worker implements Runnable {
        private MySocket sck;
        
        public Worker(MySocket sck) {
            this.sck = sck;
        }
        
        @Override
        public void run() {
            String l, snd, rcv;
            String[] msg;
            
            while ((l = sck.readLine()) != null) {
                msg = Style.parse(l);
                snd = msg[0];
                rcv = msg[1];
                System.out.println(String.format("%s > %s", snd, rcv));
                
                if (rcv.equals(Style.BROADCAST)){ // Broadcast
                    for(MySocket s : clients.values())
                        if(!s.equals(sck))
                            s.println(l);
                } else { // Unicast
                    MySocket s = clients.get(rcv);
                    if (s != null) s.println(l);
                }
            }
        }
    }
}