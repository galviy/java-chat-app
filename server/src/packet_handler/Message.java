package packet_handler;
import com.google.gson.Gson;




public class Message {
    public String type;
    public String message;
    public String from;
    

    public Message(String message,String from) {
        this.type = "chat";
        this.message = message;
        this.from = from;
    }

    public String build_Message() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
   
}
