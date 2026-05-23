package packet_handler;
import com.google.gson.Gson;


public class Message {
    public String type;
    public String message;
    public String destination;
    

    public Message(String message,String destination) {
        this.type = "chat";
        this.message = message;
        this.destination = destination;
    }

    public String build_Message() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
   
}
