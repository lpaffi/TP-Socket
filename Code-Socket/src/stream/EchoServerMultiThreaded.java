/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import domain.Conversation;
import domain.History;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class EchoServerMultiThreaded {

    /**
     * main method
     *
     * @param EchoServer port
     **/
    public static void main(String args[]) {

        ServerSocket listenSocket;

        List<ObjectOutputStream> writers = new ArrayList<>();

        History history = new History();

        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }

        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept(); // new client socket created
                writers.add(new ObjectOutputStream(clientSocket.getOutputStream()));

                System.out.println("Connexion from: " + clientSocket.getInetAddress().toString());

                ClientThread ct = new ClientThread(clientSocket, writers);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
            e.printStackTrace();
        }
    }
}

