/*Web socket library */
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import server_handler.*;
import utilities_handler.*;



public class Server extends WebSocketServer {
    //static MysqlMahasiswaService service = new MysqlMahasiswaService();


    private handler server_handle;


     public Server(int port, handler server_handle) {
        super(new InetSocketAddress(port));
        this.server_handle = server_handle;
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        server_handle.onOpen(conn, handshake);

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        server_handle.onClose(conn,code,reason, remote);
      
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        server_handle.onMessage(conn,message);
    }
  

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        //System.out.println("Server nyala");
    }

    public static void main(String[] args) {
        MysqlUtility.getConnection();
        handler run = new handler("Tubes gacor");
        Server server = new Server(8080, run);
        server.start();

        System.out.println("WebSocket server jalan di port 8080");
    }

    
}
