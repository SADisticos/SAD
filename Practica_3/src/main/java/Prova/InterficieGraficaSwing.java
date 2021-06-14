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
/**
 *
 * @author alber
 */

public class InterficieGraficaSwing {
    
        JFrame frame;
        SocketChannel socket;
        String nick;
        
        JPanel userList;
        private JTextArea messages;

        private DefaultListModel<String> listModel = new DefaultListModel<>();
        private JList list = new JList<>(listModel);
        private JScrollPane listScrollPane = new JScrollPane(list);

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
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
        JPanel output = new JPanel(new GridLayout(1,2));
        messages = new JTextArea(20, 40); //Ho he canviat a 20,40 per a que quedes ben dividit
        messages.setBackground(new Color(150,150,150));
        messages.setEditable(false);
        
        
        output.add(new JScrollPane(messages));
        // Create an input JPanel and add a JTextField(25) and a JButton
        JPanel input = new JPanel(new FlowLayout()); 
        JTextField text = new JTextField(25);
        input.add(text);
        input.setBackground(Color.black);
        
        //Afegim primer el botó d'enviar, i li apliquem un actionListener per a que s'activi i mostri el missatge quan apretem
        JButton enviar = new JButton("Enviar"); 
        enviar.setBackground(Color.GREEN);
        enviar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String msg = text.getText();
                if((msg.length() != 0)){
                    
                    messages.append(nick +": "+ msg + "\n");
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
        input.add(enviar);
        //Botó de SORTIR
        JButton sortir = new JButton("Sortir");
        sortir.setBackground(Color.RED);
        // Per a que pasi algo quan s'apreti Exit
        sortir.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e){
                //Aqui  va el codi que volem que s'executi quan té lloc la acció.
                frame.dispose();                  
            }
        });
        input.add(sortir);
        //Amb això si s'apreta enter també s'envia
        frame.getRootPane().setDefaultButton(enviar);
        
        //User List
        userList = new JPanel(new GridLayout(1,2));
        list.setBackground(Color.black);
        list.setForeground(Color.gray);
        list.setVisibleRowCount(-1);
        
        userList.add(listScrollPane, BorderLayout.CENTER);
        
        // add panels to main frame
        frame.add(output, BorderLayout.CENTER);
        frame.add(input, BorderLayout.PAGE_END);
        frame.add(userList, BorderLayout.EAST);
        
        //Display the window centered.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterficieGraficaSwing inter = new InterficieGraficaSwing();
                  inter.createAndShowGUI();
                
            }
        });
    }
}

