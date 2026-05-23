package User_handle;
import org.java_websocket.client.WebSocketClient;

import com.google.gson.JsonObject;

import packet_handler.*;
import com.google.gson.JsonParser;

public class User {

    private String ip_destination;
    private String port_destination;
    private String username;
    private String password;
    private WebSocketClient client;

    public User(String ip, String port,WebSocketClient client) {
        this.ip_destination = ip;
        this.port_destination = port;

        this.client = client;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }

    public void sendChat(String message, String destination) {

        Message packet = new Message(message, destination);
        String json = packet.build_Message();
        client.send(json.toString());

    }

     public void sendLogin(String username, String password) {

        Login packet = new Login(username, password);
        String json = packet.build_login();
        client.send(json.toString());

    }

     public void sendCreateAccount(String username, String password) {

        CreateAccount packet = new CreateAccount(username, password);
        String json = packet.build_create_account();
        client.send(json.toString());

    }

    public void onMessage(String message) {
        try {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();
            String from = json.has("from") ? json.get("from").getAsString() : "System";
            String content = json.has("message") ? json.get("message").getAsString() : message;


            System.out.println("\n[" + from + "]: " + content);
        } catch (Exception e) {
            // Kalau bukan JSON, print aja langsung
            System.out.println("\n[Server]: " + message);
        }
       
    }
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("\nDisconnected....");
        System.exit(0);
    }

}