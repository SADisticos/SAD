package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Client{
    private static String nick;
    private final static String IN_MSG = "in_msg";
    private final static String OUT_MSG = "out_msg";
    
    public class ClientConnection{
        private Selector selector = null;
        private Charset charset = Charset.forName("UTF-8");
        private SocketChannel sc = null;
        private final static String SEP = ";;";
        
        public ClientConnection(int port) throws IOException{
            
            selector = Selector.open();
            sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
            new Thread(new ClientThread()).start();
            // register
            sc.write(charset.encode(nick));
        }

        private class ClientThread implements Runnable, PropertyChangeListener, java.io.Serializable {
            private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
            
            public void run() {
                ClientGUI send = new ClientGUI();
                //pcs.addPropertyChangeListener(IN_MSG)
                //PropertyChangeEvent event = new PropertyChangeEvent(send)
                send.addPropertyChangeListener(send); // Receive messages
                
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
            
            //private class PropertyChangeAdapter implements PropertyChangeListener{
            //    public void propertyChangeEvent e){
            //       reporter.reportChange(e);
            //}

            private void dealWithSelectionKey(SelectionKey sk) throws IOException {
                if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();

                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    StringBuilder content = new StringBuilder();
                    while (sc.read(buff) > 0) {
                        buff.flip();
                        content.append(charset.decode(buff));
                    }
                    System.out.println(content);
                    pcs.firePropertyChange(IN_MSG, "", content);
                    sk.interestOps(SelectionKey.OP_READ);
                }
            } 

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Enviando");
                String line = (String) evt.getNewValue();
                if("".equals(nick)){
                    nick = line;
                    line = nick + SEP;
                } else
                    line = nick + SEP + line;
                try {
                    sc.write(charset.encode(line));
                } catch (IOException ex) {}
            }
            
            public void addPropertyChangeListener(PropertyChangeListener l){
                this.pcs.addPropertyChangeListener(l);
            }

            public void removePropertyChangeListener(PropertyChangeListener l){
                this.pcs.removePropertyChangeListener(l);
            }
        }
    }
    
    public static class ClientGUI extends JPanel implements PropertyChangeListener, java.io.Serializable{
        private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
        private static JFrame frame;
        private JPanel userList;
        
        private  JList<String> list;
        private DefaultListModel<String> listModel = new DefaultListModel<>();
        
        private JScrollPane listScrollPane = new JScrollPane(list);
        private JTextField text;

        public ClientGUI(){
            super(new BorderLayout()); // Always super above
           
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


            //Afegim primer el botó d'enviar, i li apliquem un actionListener per a que s'activi i mostri el missatge quan apretem
            JButton enviar = new JButton("Enviar"); 
            enviar.setBackground(Color.GREEN);
            enviar.setPreferredSize(new Dimension(90, 30));

            enviar.addActionListener(new ActionListener(){      // ESCRIBIR
                @Override
                public void actionPerformed(ActionEvent e){
                    String msg = text.getText();
                    
                    listModel.addElement(nick +": "+ msg + "\n");
                    text.setText("");
                    
                    if((msg.length() != 0))
                        pcs.firePropertyChange(OUT_MSG, "", msg);
                }
            });


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
        
        // SI M'HAS DE PASAR PRÀMETRES, QUE SIGUIN EL SOCKETCHANEL, I EL NICK
        private void createAndShowGUI() {
        
            try{
            //Set the look and feel.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
            }catch(Exception e) {e.printStackTrace();

            }    
            //Make sure we have nice window decorations.
            JFrame.setDefaultLookAndFeelDecorated(true);

            // Create and set up the window.
            //DINS DE JFRAME PODRIEM FICAR EL NICK
            frame = new JFrame("CHAT ROOM");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComponent newContentPane = new ClientGUI();
            frame.setContentPane(newContentPane);

            //Display the window centered.
            frame.pack();
            frame.setSize(300,400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setMinimumSize(new Dimension(400, 400));
        }
        
        @Override 
        public void propertyChange(PropertyChangeEvent evt){
            if(evt.getPropertyName().equals(IN_MSG)){
                listModel.addElement("Recibido" + "\n");
                text.setText("");
            }
        }
        
        public void addPropertyChangeListener(PropertyChangeListener l){
            this.pcs.addPropertyChangeListener(l);
        }
        
        public void removePropertyChangeListener(PropertyChangeListener l){
            this.pcs.removePropertyChangeListener(l);
        }
    }
    

    public static void main(String[] args) {
        nick = "anonymouse";
        int port = 12345;
        
        if (args.length == 1){
            nick = args[0];
        }
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientGUI inter= new ClientGUI();
                inter.createAndShowGUI();
                
            }
        });
    }
}

