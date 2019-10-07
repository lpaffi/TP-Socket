package Client;

import domain.Message;
import domain.User;

import javafx.scene.control.TextArea;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EchoClient extends Thread {

    private String serverAdress;

    private int serverPort;

    private User client;

    private Socket clientToServerSocket = null;

    private ObjectOutputStream objectOutputStream = null;

    private ObjectInputStream objectInputStream = null;

    private ClientWriteThread clientWriteThread;

    private ClientReadThread clientReadThread;

    private TextArea textArea;

    public EchoClient(String serverAdress, int serverPort, User client, TextArea textArea) {
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;
        this.client = client;
        this.textArea = textArea;
        try {
            this.clientToServerSocket = new Socket(serverAdress, serverPort);
            this.objectOutputStream = new ObjectOutputStream(clientToServerSocket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(clientToServerSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.clientWriteThread = new ClientWriteThread(objectOutputStream, client);
        this.clientReadThread = new ClientReadThread(objectInputStream, this.textArea);
    }

    public void sendMessage(Message message) {
        System.out.println("sendMessage in Test Client");
        clientWriteThread.sendMessage(message);
    }

    public void run() {
        System.out.println("Running Test Client");

        // creation socket ==> connexion
        System.out.println("Connected to server " + clientToServerSocket.getInetAddress());
        clientWriteThread.start();
        clientReadThread.start();
        while (clientReadThread.isAlive() && clientWriteThread.isAlive()) {
        }
        System.out.println("Vous êtes deconnecté");
    }
}
