package domain;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private String name;

    private InetAddress inetAddress;

    private List<Conversation> conversationsList;

    public User(String name, InetAddress inetAddress, List<Conversation> conversationsList) {
        this.name = name;
        this.inetAddress = inetAddress;
        this.conversationsList = conversationsList;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public List<Conversation> getConversationsList() {
        return conversationsList;
    }

    public void setConversationsList(List<Conversation> conversationsList) {
        this.conversationsList = conversationsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(inetAddress, user.inetAddress) &&
                Objects.equals(conversationsList, user.conversationsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inetAddress, conversationsList);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", inetAddress=" + inetAddress +
                ", conversationsList=" + conversationsList +
                '}';
    }
}
