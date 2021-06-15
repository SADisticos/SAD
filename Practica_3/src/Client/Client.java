package Client;

import static Client.Client.nick;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class Client extends JPanel{
    /* Constants */
    public static String nick;
    public static final String IN_MSG = "in_msg";
    public static final String OUT_MSG = "out_msg";
    
    /* GUI */
    private static JFrame frame;
    private  JList<String> list;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JTextField text;
    
    /* Connection */
    private Selector selector = null;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel sc = null;
    private final static String SEP = ";;";
    private int port;
    

    public Client(int port) throws IOException{
        /* GUI */
        super(new BorderLayout());
        EmptyBorder eborder = new EmptyBorder(10, 10, 10, 10);
        list = new JList<String>(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(500, 500));
        listScrollPane.setBorder(eborder);
        // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.LINE_AXIS));

        text = new JTextField(); //size
        text.setPreferredSize(new Dimension(100, 30));
        
        /* Connection */
        this.port = port;
        selector = Selector.open();
        sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        // register
        sc.write(charset.encode(Client.nick));
        new Thread(new ClientThread()).start();

        /* GUI */
        //Afegim primer el botó d'enviar, i li apliquem un actionListener per a que s'activi i mostri el missatge quan apretem
        JButton enviar = new JButton("Enviar"); 
        enviar.setBackground(Color.GREEN);
        enviar.setPreferredSize(new Dimension(90, 30));

        enviar.addActionListener(new ActionListener(){      // ESCRIBIR
            @Override
            public void actionPerformed(ActionEvent e){
                String msg = text.getText();

                listModel.addElement(Client.nick +": "+ msg + "\n");
                text.setText("");

                /* Connection */
                if((msg.length() != 0)){
                    if("".equals(Client.nick)){
                        Client.nick = msg;
                        msg = Client.nick + SEP;
                    } else
                        msg = Client.nick + SEP + msg;
                    try {
                        sc.write(charset.encode(msg));
                    } catch (IOException ex) {}
                }
            }
        });
        frame.getRootPane().setDefaultButton(enviar);

        /* GUI */
        //Amb això si s'apreta enter també s'envia
        input.add(text);
        input.add(enviar);
        input.setMinimumSize(new Dimension(200, 40));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(listScrollPane, BorderLayout.NORTH);
        add(input, BorderLayout.SOUTH);
        setBorder(eborder);
            //Set enter to activate send button
        list.setBackground(Color.white);
        list.setForeground(Color.black);

        list.setVisibleRowCount(-1);
    }

    public static void createAndShowGUI(int port) throws IOException {

        try{
            //Set the look and feel.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e) {e.printStackTrace();}

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        frame = new JFrame("Chat Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new Client(port);
        frame.setContentPane(newContentPane);

        //Display the window centered.
        frame.pack();
        frame.setSize(300,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(400, 400));
    }
    
    private class ClientThread implements Runnable {
        /* Connection READ message */
        private void dealWithSelectionKey(SelectionKey sk) throws IOException {
            if (sk.isReadable()) {
                SocketChannel sc = (SocketChannel) sk.channel();

                ByteBuffer buff = ByteBuffer.allocate(1024);
                StringBuilder content = new StringBuilder();
                while (sc.read(buff) > 0) {
                    buff.flip();
                    content.append(charset.decode(buff));
                }
                String[] message = content.toString().split(SEP);
                String nickSender = message[0];
                String msg = message[1];
                System.out.println(nickSender + ": " + msg);
                sk.interestOps(SelectionKey.OP_READ);
                /* GUI */
                listModel.addElement(nickSender +": "+ msg + "\n");
                text.setText("");
            }
        }

        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) continue;
                    Set selectedKeys = selector.selectedKeys();
                    Iterator keyIterator = selectedKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                }
            } catch (IOException io) {}
        }
    }
    
    public static void main(String[] args) throws IOException {
        nick = "anonymouse";
        int port = 12345;
        
        if (args.length == 1){
            nick = args[0];
        }

        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                try {
                    createAndShowGUI(port);
                } catch (IOException ex) {}
            }
        });
    }
}