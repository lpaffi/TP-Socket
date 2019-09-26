package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {


    private String username;

    private String content;

    private Date date;

    public Message(String username, String content, Date date) {
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "[ "+username+" à "+new SimpleDateFormat("hh:mm:ss dd-M-yyyy").format(date) +" ]: "+content;
    }
}
