/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package server;

import domain.History;
import domain.Message;
import domain.MulticastRoom;
import domain.SystemMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;

public class ClientThread
        extends Thread {

    private Socket clientSocket;

    private HashMap<String, ObjectOutputStream> writers;

    private History history;

    private String socketId;

    private InetAddress inetAdress;

    private int port;

    private static int MULTICAST_SERVER_PORT = 6789;

    private static String MULTICAST_SERVER_ADDRESS = "228.5.6.7";

    ClientThread(Socket socket, HashMap<String, ObjectOutputStream> writers, History history) {
        this.clientSocket = socket;
        this.writers = writers;
        this.history = history;
        this.socketId = clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort();

        try {
            this.inetAdress = InetAddress.getByName(MULTICAST_SERVER_ADDRESS);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = MULTICAST_SERVER_PORT;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        System.out.println("Running Client Thread");
        //Send port, ip and history to the client
        MulticastRoom multicastRoom = new MulticastRoom();
        multicastRoom.setIpAddress(inetAdress);
        multicastRoom.setPort(port);
        multicastRoom.setHistory(history);

        try {
            System.out.println("Envoi multicastRoom" + clientSocket.toString());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(multicastRoom);
        } catch (Exception e) {
            System.err.println("Error in ClientThread:" + e);
            e.printStackTrace();
        }
    }

}

