/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;


public class EchoClient {

    private static String SERVER_ADDRESS = "127.0.0.1";
    private static int SERVER_PORT = 5000;


    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException {

        Socket clientToServerSocket = null;
        DataOutputStream socOut = null;
        DataInputStream socIn = null;
        BufferedReader stdIn = null;

        try {
            // creation socket ==> connexion
            clientToServerSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server");

            socIn = new DataInputStream(clientToServerSocket.getInputStream());

            socOut = new DataOutputStream(clientToServerSocket.getOutputStream());

            stdIn = new BufferedReader(new InputStreamReader(System.in));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + SERVER_ADDRESS);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + SERVER_ADDRESS);
            System.exit(1);
        }

        String line;
        while (true) {
            line = stdIn.readLine();
            if (line.equals(".")) break;
            socOut.writeUTF(line);
            System.out.println("echo: " + socIn.readLine());
        }

        //Close connection
        try {
            socOut.close();
            socIn.close();
            stdIn.close();
            clientToServerSocket.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}


