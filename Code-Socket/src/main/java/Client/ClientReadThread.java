package Client;

import Client.Util.ByteSerializationUtil;
import domain.Message;
import domain.MulticastRoom;
import domain.SystemMessage;

import java.io.*;
import java.net.*;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    private MulticastRoom multicastRoom;

    public ClientReadThread(MulticastRoom multicastRoom, ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
        this.multicastRoom = multicastRoom;
    }

    public void run() {
        System.out.println("Running Client Read Thread");

        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);

        try {
            MulticastRoom receivedMulticastRoom = (MulticastRoom) objectInputStream.readObject();
            multicastRoom.setHistory(receivedMulticastRoom.getHistory());
            multicastRoom.setPort(receivedMulticastRoom.getPort());
            multicastRoom.setIpAddress(receivedMulticastRoom.getIpAddress());

            MulticastSocket multicastSocket = new MulticastSocket(multicastRoom.getPort());
            multicastRoom.setMulticastSocket(multicastSocket);

            multicastRoom.getHistory().getMessageList().forEach(message -> System.out.println(message.toString()));
            //multicastSocket.joinGroup(multicastRoom.getIpAddress());
            //ligne mysteriuse que fait le code marcher sur mon mac(????)
            multicastSocket.joinGroup(new InetSocketAddress(multicastRoom.getIpAddress(), multicastRoom.getPort()), NetworkInterface.getByName("p2p0"));

            while (true) {
                try {
                    multicastSocket.receive(recv);
                    Message serverMessage = (Message) ByteSerializationUtil.deserialize(recv.getData());
                    if (serverMessage.getContent().equals(SystemMessage.DISCONNECTED.toString())) {
                        multicastSocket.leaveGroup(multicastSocket.getInetAddress());
                        this.stop();
                    }
                    System.out.println(serverMessage.toString());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
