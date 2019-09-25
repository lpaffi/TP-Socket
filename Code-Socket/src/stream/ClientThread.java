/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

public class ClientThread
        extends Thread {

    private Socket clientSocket;

    private HashMap<String, ObjectOutputStream> writers;

    private final String EXIT_MESSAGE = "quit";

    private final String QUIT_MESSAGE = "Disconnecting";

    ClientThread(Socket socket, HashMap<String, ObjectOutputStream> writers) {
        this.clientSocket = socket;
        this.writers = writers;
    }

    private void broadcastMessage(Message message) throws IOException {
        for (ObjectOutputStream writer : writers.values()) {
            writer.writeObject(message);
        }
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        System.out.println("Running Client Thread");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                Message clientMessage = (Message) objectInputStream.readObject();
                System.out.println(clientMessage.toString());
                if (clientMessage.getContent().equals(EXIT_MESSAGE)) {
                    String socketId = clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort();
                    ObjectOutputStream clientQuitting = writers.get(socketId);
                    Message quitMessage = new Message("Server", QUIT_MESSAGE, new Date());
                    clientQuitting.writeObject(quitMessage);
                    writers.remove(socketId);
                    clientSocket.close();
                    break;
                } else {
                    broadcastMessage(clientMessage);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in ClientThread:" + e);
            e.printStackTrace();
        }
    }

}

