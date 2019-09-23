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


    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket clientToServerSocket = null;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            clientToServerSocket = new Socket(args[0], new Integer(args[1]).intValue());
            System.out.println("Connected to server " + clientToServerSocket.getInetAddress());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + args[0]);
            System.exit(1);
        }

        ObjectInputStream objectInputStream = new ObjectInputStream(clientToServerSocket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientToServerSocket.getOutputStream());

        String serverMessage = (String) objectInputStream.readObject();

        System.out.println("Server said: " + serverMessage);

        System.out.println("Quel est votre nom d'utilisateur ? ");

        String name = stdIn.readLine();

        System.out.println("Bienvenue à la salle de conversation, " + name + " !");

        User user = new User();
        user.setName(name);

        String line;
        while (true) {
            line = stdIn.readLine();
            if (line.equals(".")) break;
            Message message = new Message(name, line, new Date());
            objectOutputStream.writeObject(message);
            System.out.println("echo: " + message.getContent());
        }
        stdIn.close();
        clientToServerSocket.close();
    }
}

