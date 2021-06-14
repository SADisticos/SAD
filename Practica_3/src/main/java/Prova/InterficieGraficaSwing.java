/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prova;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author alber
 */

public class InterficieGraficaSwing extends JPanel {
    
        static JFrame frame;
        SocketChannel socket;
        String nick ="ENRIC";
        JPanel userList;
        
        JList<String> list;
        private DefaultListModel<String> listModel = new DefaultListModel<>();
        
        private JScrollPane listScrollPane = new JScrollPane(list);

        public InterficieGraficaSwing(){
           super(new BorderLayout());
           EmptyBorder eborder = new EmptyBorder(10, 10, 10, 10);

        list = new JList<String>(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(500, 500));
        listScrollPane.setBorder(eborder);
        // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.LINE_AXIS));

        JTextField text = new JTextField(); //size
        text.setPreferredSize(new Dimension(100, 30));

        
        //Afegim primer el botó d'enviar, i li apliquem un actionListener per a que s'activi i mostri el missatge quan apretem
        JButton enviar = new JButton("Enviar"); 
        enviar.setBackground(Color.GREEN);
        enviar.setPreferredSize(new Dimension(90, 30));
        
        enviar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String msg = text.getText();
                if((msg.length() != 0)){
                    
                    listModel.addElement(nick +": "+ msg + "\n");
                    text.setText("");

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(msg.getBytes());
                    buffer.flip();
                    try {
                        socket.write(buffer);
                    } catch (IOException ex) {
                        Logger.getLogger(InterficieGraficaSwing.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

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
        
        JComponent newContentPane = new InterficieGraficaSwing();
        frame.setContentPane(newContentPane);
      
        //Display the window centered.
        frame.pack();
        frame.setSize(300,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(400, 400));
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterficieGraficaSwing inter= new InterficieGraficaSwing();
                inter.createAndShowGUI();
                
            }
        });
    }
}

