/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import domain.Conversation;
import domain.History;
import domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ClientThread
        extends Thread {

    private Socket clientSocket;

    private List<ObjectOutputStream> writers;

    ClientThread(Socket s, List<ObjectOutputStream> writers) {
        this.clientSocket = s;
        this.writers = writers;
    }

    private void broadcastMessage(Message message) throws IOException {
        for (ObjectOutputStream writer : writers) {
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
                broadcastMessage(clientMessage);
            }
        } catch (Exception e) {
            System.err.println("Error in ClientThread:" + e);
            e.printStackTrace();
        }
    }

}

