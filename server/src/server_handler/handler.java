package server_handler;

import java.util.HashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import packet_handler.*;
import user_handler.User;
import utilities_handler.mysqlAction;




public class handler{
    private HashMap<WebSocket, User> users = new HashMap<>();
    private String server_name;
    private mysqlAction db;

    public handler (String server_name){
        setServer_name(server_name);
        this.db = new mysqlAction();
    }

    public void setServer_name(String n){
        this.server_name = n;
    }
    public String getServer_name(){
        return this.server_name;
    }

    public int send_broadcast_all(String message){
        Message packet = new Message(message, "System");
        String json = packet.build_Message();
        int successCount = 0;

        for (User available : users.values()) {
           if (available.getConn().isOpen()) { 
                available.send(json);
                successCount++;
            }   
        }
        return successCount;
    }
    public int send_broadcast_all(String message,String from){
        int successCount = 0;
        Message packet = new Message(message, from);
        String json = packet.build_Message();

        for (User available : users.values()) {
           if (available.getConn().isOpen()) { 
                available.send(json);
                successCount++;
            }   
        }
        return successCount;
    }

    public void send_to_client(WebSocket conn,String message){
        Message packet = new Message(message, "System");
        String json = packet.build_Message();

        System.out.println(json);
        conn.send(json);
    }

    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Client connected: " + conn.getRemoteSocketAddress());
    }

     public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        User user = users.get(conn);

        if (user != null){
            System.out.println(user.getUsername() + " has been disconnected");
            //send_broadcast_all(user.getUsername() + " has been disconnected from server!");
        }else {
            //send_broadcast_all("Unknown/guest has been disconnected from server!");
        }

        users.remove(conn);

    }
     public void onMessage(WebSocket conn, String message) {
        try {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();
            String type = json.get("type").getAsString();
            User user = users.get(conn);

            if (user == null && (!type.equals("login") && !type.equals("create_account"))) {
                //
                send_to_client(conn, "Disconnected...Kamu belum login!");
                conn.close();
                return;
            }

            switch(type) {
                case "chat": {
                    String chat = json.get("message").getAsString();
                    String destination = json.get("destination").getAsString();
                    if(destination.equals("all")){
                        //System.out.println(user.getUsername() + " nama username");
                        int sent = send_broadcast_all(chat, user.getUsername());
                        System.out.println(user.getUsername() + " broadcasted to " + sent + " people");
                        send_to_client(conn, "you have broadcasted to " + sent + " users");

                    } else {
                        // nanti dulu males hehe
                    }
                } break;

                case "login": {

                    if (user != null) {
                        send_to_client(conn,"Disconnected...? you have logged in..?");
                        conn.close();
                        return;
                    }
                    if (!json.has("username") || !json.has("pass")) {
                        send_to_client(conn, "Disconnected...? Invalid packet..?");
                         conn.close();
                        return;
                    }
                    String username = json.get("username").getAsString();
                    String password = json.get("pass").getAsString();

                    if (username.isEmpty() || password.isEmpty() || username == null || password == null) {
                        send_to_client(conn, "Disconnected...! you cant have empty username or password...!");
                        conn.close();
                        return;
                    }
                    //checkPassword
                   

                    User newUser = new User(conn, username);
                    newUser.setUsername(username);
                    newUser.set_password(password);
                    Boolean checkPass = db.checkPassword(newUser);
                    if (!checkPass){
                        send_to_client(conn, "Disconnected...! Wrong username or password...!");
                        conn.close();
                        return;
                    }
                    send_to_client(conn, "Welcome back " + username);
                    users.put(conn, newUser);

                    send_to_client(conn,"Halo " + username + " selamat datang di server " + getServer_name());
                    send_to_client(conn, "Coded by galvin dan kawan kawan <3");
                    send_broadcast_all(username + " telah bergabung ke chat!");

                } break;
                

                case "create_account":{
                    if (user != null) {
                       send_to_client(conn,"Disconnected...? you have logged in..?");
                        conn.close();
                        return;
                    }
                    if (!json.has("username") || !json.has("pass")) {
                        send_to_client(conn, "Disconnected...? Invalid packet..?");
                         conn.close();
                        return;
                    }
                    String username = json.get("username").getAsString();
                    System.out.println("Creating " + username);
                    String password = json.get("pass").getAsString();

                    if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                        send_to_client(conn, "Disconnected...! you cant have empty username or password...!");
                        conn.close();
                        return;
                    }
                   // User newUser = new User(conn, username);
                    username = username.toLowerCase().trim();
                    

                    Boolean isExist = db.checkExist(username.toLowerCase());
                    System.out.println("Result: " + isExist);
                    if (isExist){
                        send_to_client(conn, "Disconnected...! username (" + username + ") has already exist");
                        conn.close();
                        return;
                    } 
                    User newUser = new User(conn, username);
                    newUser.setUsername(username);
                    newUser.set_password(password);
                    Boolean create = db.add(newUser);

                    if (!create){
                         send_to_client(conn, "Disconnected...! Failed to create account, internal server error");
                        conn.close();
                    }

                    users.put(conn, newUser);
                    
                    send_to_client(conn, "Successfully created account with username: " + username);
                    send_to_client(conn,"Halo " + username + " selamat datang di server " + getServer_name());
                    send_to_client(conn, "Coded by galvin dan kawan kawan <3");
                    send_broadcast_all(username + " baru saja membuat akun dan telah bergabung ke chat!");
                }
            }

        } catch(Exception e) {
            send_to_client(conn, "Disconnected...! internal server error");
            conn.close();
            e.printStackTrace();
            return;
        }
    }

}

