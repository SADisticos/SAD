package Chat;

import MySockets.MySocket;
import View.Terminal;

public class Client {
    private MySocket sck;
    private Terminal view;
    private String nick;

    public Client(String nick, String host, int port) {
        this.nick = nick;
        sck = new MySocket(host, port);
        view = new Terminal(this.nick);
    }
    
    public void run() {
        ReceiverThread rcvThread = new ReceiverThread(sck);
        SenderThread sndThread = new SenderThread(sck);
        
        sck.println(nick);
        String l = sck.readLine();
        String[] msg = Style.parse(l);
        view.setColor(msg[1]);
        
        new Thread(rcvThread).start();
        new Thread(sndThread).start();
    }

    public class SenderThread implements Runnable {
        private MySocket sck;
        
        public SenderThread(MySocket sck) {
            this.sck = sck;
        }
        
        public void run() {
            String l;
            while ((l = view.readLine()) != null) {
                sck.println(Style.format(nick, Style.BROADCAST, l));
            }
        }
    }
    
    public class ReceiverThread implements Runnable {
        private MySocket sck;
        
        public ReceiverThread(MySocket sck) {
            this.sck = sck;
        }
        
        public void run() {
            String l;
            while ((l = sck.readLine()) != null){
                view.newMessage(Style.parse(l));
            }
        }
    }
}