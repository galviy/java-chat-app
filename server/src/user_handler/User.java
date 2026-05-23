
package user_handler;

import org.java_websocket.WebSocket;


public class User  {

    private String ip;
    private String username;
    private String password;
    private WebSocket conn;

    public User(WebSocket conn,String ip_address){
        this.conn = conn;
        this.username = "Guest"; 
        this.ip = ip_address;
        
    }
    public void set_username(String input){
        this.username = input;
    }
    public void set_password(String input){
        this.password = input;
    }

    public WebSocket getConn() {
        return this.conn;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getIp(){
        return this.ip;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void send(String message) {
        conn.send(message);
    }

}