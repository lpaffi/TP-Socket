package domain;

import java.util.ArrayList;
import java.util.List;

public class History {

    List<Message> messageList = new ArrayList<>();

    public History(List<Message> messageList) {
        this.messageList = messageList;
    }

    public History() {
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return "History{" +
                "messageList=" + messageList +
                '}';
    }
}
