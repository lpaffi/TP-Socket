/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import domain.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread
        extends Thread {

    private Socket clientSocket;

    ClientThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                Message clientMessage = (Message) objectInputStream.readObject();
                System.out.println(clientMessage.toString());
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}

