package domain;

import java.io.Serializable;
import java.net.InetAddress;

public class MulticastRoom implements Serializable {

    public int port;

    public InetAddress ipAddress;

    public History history;

    public MulticastRoom() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "MulticastRoom{" +
                "port=" + port +
                ", ipAddress=" + ipAddress +
                ", history=" + history +
                '}';
    }
}
