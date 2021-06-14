package Chat;

import MySockets.MyServerSocket;
import MySockets.MySocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Server {
    private MyServerSocket ss;
    private HashMap<String, Properties> clients = new HashMap<String, Properties>();
    
    private class Properties {
        private MySocket sck;
        private String color;
        private static List<String> colors;
        
        private Properties(MySocket sck){
            this.sck = sck;
            if (colors == null){
                colors = new ArrayList<>();
                colors.addAll(View.Const.colorsList.colors);
            } else if (colors.isEmpty()){
                colors.addAll(View.Const.colorsList.colors);
            }
            Collections.shuffle(colors);  // Randomize color pick
            this.color = colors.remove(0);
        }
        
        private MySocket getSck(){
            return sck;
        }
        
        private String getColor(){
            return color;
        }
    }
    
    public Server(int port) {
        ss = new MyServerSocket(port);
    }
    
    public void run() {
        while(!ss.isClosed()){
            MySocket clSck = ss.accept();
            String nick = clSck.readLine();
            clients.put(nick, new Properties(clSck));
            clSck.println(Style.initColor(nick, clients.get(nick).getColor()));
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
                System.out.print(clients.get(snd).getColor());
                System.out.print(snd + " > ");
                System.out.print(View.Const.Colors.RESET);
                System.out.println(rcv);
                
                
                if (rcv.equals(Style.BROADCAST)) {          // BROADCAST
                    for(Properties s : clients.values())
                        if(!s.getSck().equals(sck))
                            s.getSck().println(Style.addColor(l, clients.get(snd).getColor()));
                    
                } else {                                    // UNICAST --> NOT IMPLEMENTED AT ALL
                    MySocket s = clients.get(rcv).getSck();
                    if(s != null)
                        s.println(l);
                }
            }
        }
    }
}