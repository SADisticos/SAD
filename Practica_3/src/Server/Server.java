package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server {
    private static List<String> users = new ArrayList<String>();
    private Charset charset = Charset.forName("UTF-8");    
    private Selector selector;
    private static final String SEP = ";;";
    private ServerSocketChannel server;
    
    public Server(int port) throws IOException{
        selector = Selector.open();
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);   // Listen mode
        
        System.out.println("Server is listening now...");        
    }
    
    public void run() throws IOException {
        while(true){
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set selectedKeys = selector.selectedKeys(); // Available Channels
            Iterator keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey sk = (SelectionKey) keyIterator.next();
                keyIterator.remove();
                dealWithSelectionKey(sk);
            }
        }
    }
    
    public void dealWithSelectionKey(SelectionKey sk) throws IOException {
        if(sk.isAcceptable()){
            SocketChannel sc = server.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);     // Read Mode
            sk.interestOps(SelectionKey.OP_ACCEPT);            
        }
        if(sk.isReadable()) {
            SocketChannel sc = (SocketChannel) sk.channel();
            ByteBuffer buff = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try{
                while(sc.read(buff) > 0) {
                    buff.flip();
                    content.append(charset.decode(buff));
                }
                sk.interestOps(SelectionKey.OP_READ);
            }catch(IOException io){
                sk.cancel();
                if(sk.channel() != null)
                    sk.channel().close();
            }
            
            if (content.length() > 0) {
                String[] arrayContent = content.toString().split(SEP);
                if (arrayContent.length == 1) {
                    String nick = arrayContent[0];
                    users.add(nick);
                    System.out.println(nick + " connected");

                }
                else if ( arrayContent.length > 1) {
                    String name = arrayContent[0];
                    String message = content.substring(name.length() + SEP.length());
                    message = arrayContent[1];
                    if (users.contains(name)) {
                        broadCast(selector, sc, name, message);
                    }
                }
            }

        }
    }

    public void broadCast(Selector selector, SocketChannel except, String name, String content) throws IOException {
        System.out.println(name + ": " + content);
        String message = name + SEP + content;
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel && targetchannel != except) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(charset.encode(message));
            }
        }
    }


    public static void main(String[] args) throws IOException {
        int port = 12345;
        
        new Server(port).run();
    }
}