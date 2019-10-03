package Client;


import Client.Util.ByteSerializationUtil;
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

    private User user;

    private MulticastRoom multicastRoom;

    public ClientWriteThread(MulticastRoom multicastRoom, User user) {
        this.user = user;
        this.multicastRoom = multicastRoom;
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
                byte[] serializedMessage = ByteSerializationUtil.serialize(message);
                DatagramPacket datagramPacket = new DatagramPacket(serializedMessage, serializedMessage.length, multicastRoom.getIpAddress(), multicastRoom.getPort());
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
