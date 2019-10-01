package domain;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class History implements Serializable {

    private List<Message> messageList = new ArrayList<>();
    private static final String HISTORY_FILE = "history.txt";

    public History() {
        File f = new File("history.txt");
        if(!f.isFile()) {
            try{
                f.createNewFile();
            }
            catch (IOException e){
                System.out.println("Impossible to get persistant history");
            }
        }

        // load history from file
        String time, name, message;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] splits = line.split("_:_");

                time = splits[0];
                DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRENCH);
                Date date = format.parse(time);

                name = splits[1];
                message = splits[2];

                messageList.add(new Message(name, message, date));

                line = bufferedReader.readLine();
            }
        } catch (IOException | ParseException e) {
            System.out.println("No history available");
            System.err.println(e);
        }


    }

    public void addMessage(Message message) {
        this.messageList.add(message);

        // write the message in file
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRENCH);
            String dateStr = format.format(message.getDate());
            bufferedWriter.write(dateStr + "_:_" + message.getUsername() + "_:_" + message.getContent() + "\n");
        } catch (IOException e) {
            System.out.println("Error in message saving");
        }
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
