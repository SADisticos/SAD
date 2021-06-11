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
    ServerSocket serverst;
    BufferedReader br;
    PrintWriter pw;

    public MyServerSocket(final int port) {
        try {
            serverst = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Aquets dos mètodes serveixen per crear i tancar un socket
    public MySocket accept() {
        try {
            return new MySocket(serverst.accept());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            serverst.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
}