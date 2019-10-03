package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class History implements Serializable {

    private List<Message> messageList = new ArrayList<>();
    private static final String HISTORY_FILE = "history.txt";

    public History(){
        File f = new File(HISTORY_FILE);
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

        if (f.length() != 0){

            try (FileInputStream fileInputStream = new FileInputStream(f);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){

                boolean keepReading = true;
                try {
                    // Read object
                    this.messageList = (List<Message>) objectInputStream.readObject();
                }catch(EOFException e) {
                    keepReading = false;
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void addMessage(Message message) {
        this.messageList.add(message);

//        // write the message in file
//        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
//            DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRENCH);
//            String dateStr = format.format(message.getDate());
//            bufferedWriter.write(dateStr + "_:_" + message.getUsername() + "_:_" + message.getContent() + "\n");
//        } catch (IOException e) {
//            System.out.println("Error in message saving");
//        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(HISTORY_FILE));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write object to file
            objectOutputStream.writeObject(messageList);

            objectOutputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
