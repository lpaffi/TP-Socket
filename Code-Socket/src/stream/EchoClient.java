/***
 * EchoClient
 * Example of a TCP client
 * Date: 10/01/04
 * Authors:
 */
package stream;

import domain.Message;
import domain.User;

import java.io.*;
import java.net.*;
import java.util.Date;


public class EchoClient {

    private static void join(String ipAdress, int port, BufferedReader stdIn) throws IOException {
        // args[0] args[1]

        Socket clientToServerSocket = null;


        try {
            // creation socket ==> connexion
            clientToServerSocket = new Socket(ipAdress, port);
            System.out.println("Connected to server " + clientToServerSocket.getInetAddress());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + ipAdress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + ipAdress);
            System.exit(1);
        }

        System.out.println("Quel est votre nom d'utilisateur ? ");

        String name = stdIn.readLine();

        System.out.println("Bienvenue à la salle de conversation, " + name + " !");

        System.out.println("Pour quitter la conversation, il suffit d'envoyer le message  'quit' ");

        User user = new User();
        user.setName(name);

        ClientWriteThread clientWriteThread = new ClientWriteThread(new ObjectOutputStream(clientToServerSocket.getOutputStream()), user);
        ClientReadThread clientReadThread = new ClientReadThread(new ObjectInputStream(clientToServerSocket.getInputStream()));
        clientWriteThread.start();
        clientReadThread.start();
        while(clientReadThread.isAlive() && clientWriteThread.isAlive()) {
        }
    }

    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Pour rejoindre, entrez : join");
            String option = stdIn.readLine();

            if (option.equals("join")){
                join(args[0], new Integer(args[1]).intValue(), stdIn);

            }
        }
    }
}

