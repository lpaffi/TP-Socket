/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package Server;

import domain.History;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class EchoServerMultiThreaded {

    /**
     * main method
     *
     * @param EchoServer port
     **/
    public static void main(String args[]) {
        ServerSocket listenSocket;

        HashMap<String, ObjectOutputStream> writers = new HashMap<>();

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

                System.out.println("Connexion from: " + clientSocket.getInetAddress().toString());

                ClientThread ct = new ClientThread(clientSocket, writers, history);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
            e.printStackTrace();
        }
    }
}

