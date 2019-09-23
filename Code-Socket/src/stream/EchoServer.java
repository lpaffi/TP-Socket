/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

public class EchoServer {

    private static int SERVER_PORT = 5000;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    /**
     * receives a request from client then sends an echo to the client
     *
     * @param clientSocket the client socket
     **/
    static void doService(Socket clientSocket) {
        try {
            String clientAddress = clientSocket.getInetAddress().toString();
            dataInputStream = new DataInputStream(
                    new BufferedInputStream(clientSocket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Quit")) {
                try {
                    line = dataInputStream.readUTF();
                    System.out.println("["+clientAddress+"] : "+line);

                } catch (IOException i) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ServerSocket listenSocket;
        try {
            listenSocket = new ServerSocket(SERVER_PORT);

            System.out.println("Server started on port " + SERVER_PORT);

            System.out.println("Waiting for a client ...");

            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                doService(clientSocket);
                System.out.println("Client closed connection");
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

  
