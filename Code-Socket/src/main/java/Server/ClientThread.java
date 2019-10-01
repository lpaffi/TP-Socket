/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package Server;

import domain.History;
import domain.Message;
import domain.SystemMessage;

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

    private History history;

    private String socketId;

    ClientThread(Socket socket, HashMap<String, ObjectOutputStream> writers, History history) {
        this.clientSocket = socket;
        this.writers = writers;
        this.history = history;
        this.socketId = clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort();
    }

    private void broadcastMessage(Message message) throws IOException {
        for (ObjectOutputStream writer : writers.values()) {
            writer.writeObject(message);
        }
    }

    private void disconnectClient(Socket clientSocket) throws IOException {
        ObjectOutputStream clientQuitting = writers.get(socketId);
        Message quitMessage = new Message("Server", SystemMessage.DISCONNECTED.toString(), new Date());
        clientQuitting.writeObject(quitMessage);
        writers.remove(socketId);
        clientSocket.close();
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        System.out.println("Running Client Thread");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Envoi historique de la discussion à " + clientSocket.toString());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(history.getMessageList());
            writers.put(socketId, objectOutputStream);

            while (true) {
                Message clientMessage = (Message) objectInputStream.readObject();
                System.out.println(clientMessage.toString());
                if (clientMessage.getContent().equals(SystemMessage.QUIT.toString())) {
                    disconnectClient(clientSocket);
                    Message clientDisconnectedMessage = new Message("Server", clientMessage.getUsername() + " s'est deconnecté", new Date());
                    broadcastMessage(clientDisconnectedMessage);
                    break;
                } else {
                    history.addMessage(clientMessage);
                    broadcastMessage(clientMessage);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in ClientThread:" + e);
            e.printStackTrace();
        }
    }

}

