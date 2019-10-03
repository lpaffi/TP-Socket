package Client;


import domain.Message;
import domain.MulticastRoom;
import domain.SystemMessage;
import domain.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class ClientWriteThread extends Thread {

    private ObjectOutputStream objectOutputStream;

    private User user;

    private MulticastRoom multicastRoom;


    public ClientWriteThread(MulticastRoom multicastRoom, ObjectOutputStream objectOutputStream, User user) {
        this.objectOutputStream = objectOutputStream;
        this.user = user;
        this.multicastRoom = multicastRoom;
    }
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }


    public void run() {
        System.out.println("Running Client Write Thread");
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (true) {
            try {
                line = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message message = new Message(user.getName(), line, new Date());
            try {
                byte[] serializedMessage = serialize(message);
                DatagramPacket datagramPacket = new
                        DatagramPacket(serializedMessage, serializedMessage.length, multicastRoom.getIpAddress(), multicastRoom.getPort());
                datagramSocket.send(datagramPacket);
                if (message.getContent().equals(SystemMessage.QUIT.toString())) {
                    this.stop();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
