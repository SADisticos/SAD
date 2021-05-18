/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolexat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class MyServerSocket {
    ServerSocket ss;
    BufferedReader br;
    PrintWriter pw;

    public MyServerSocket(final int port) {
        try {
            ss = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MySocket accept() {
        try {
            return new MySocket(ss.accept());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}