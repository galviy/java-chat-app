import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


import User_handle.User;

import java.net.URI;
import java.util.Scanner;

public class Client extends WebSocketClient {


    private User user;

    public Client(URI serverUri) {
        super(serverUri);
    }
     public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server");
    }

    @Override
    public void onMessage(String message) {
        if (user != null) {
            user.onMessage(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        user.onClose(code, reason,remote);
       
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("[ERROR] " + ex.getMessage());

    }

    public void opsi_Handler(String opsi, Scanner scanner) {

        switch (opsi) {

            case "1": {
                
                System.out.print("Input username: ");
                String username = scanner.nextLine().trim();

                System.out.print("Input password: ");
                String password = scanner.nextLine().trim();
                if (user != null) {
                    user.sendLogin(username, password);
                } else {
                    System.out.println("?? Error ?? User object null");

                }

            }
            break;

            case "2": {
                System.out.print("\nCreate Account!");
                System.out.print("Input username: ");
                String username = scanner.nextLine().trim();

                System.out.print("Input password: ");
                String password = scanner.nextLine().trim();
                if (user != null) {
                    user.sendCreateAccount(username, password);
                } else {
                    System.out.println("?? Error ?? User object null");

                }
            }
            break;
        }
    }


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan IP server (default: localhost): ");
        String host = scanner.nextLine().trim();
        if (host.isEmpty()) {
            host = "localhost";
        }

        System.out.print("Masukkan port (default: 8080): ");
        String portStr = scanner.nextLine().trim();
        int port = Integer.parseInt(portStr);

        if (portStr.isEmpty() || host.isEmpty()){
            port = 8080;
            host = "127.0.0.1";
        }

        URI uri = new URI("ws://" + host + ":" + port);
        Client client = new Client(uri);

        System.out.println("Connecting to " + host + ":" + port);
        client.connectBlocking();

        User user = new User(
                host,
                String.valueOf(port),
                client
        );

        client.setUser(user);
        System.out.println("1. Login");
        System.out.println("2. Create account");
        
        System.out.print("Pilih opsi: ");
        String opsi = scanner.nextLine().trim();
        client.opsi_Handler(opsi,scanner);

 
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            if (input.equalsIgnoreCase("/quit")) {
                System.out.println("[INFO] Keluar...");
                client.close();
                break;
            } else if (input.startsWith("/all ")) {
                String msg = input.substring(5).trim();
                if (!msg.isEmpty()) {
                    user.sendChat(msg, "all");
                } else {
                    System.out.println("[!] Pesan tidak boleh kosong.");
                }
            } else {
                System.out.println("[!] Perintah tidak dikenal. Gunakan /all <pesan> atau /quit");
            }
        }

        scanner.close();
    }
}