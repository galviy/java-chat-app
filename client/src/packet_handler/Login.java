package packet_handler;
import com.google.gson.Gson;

public class Login {
    public String username;
    public String pass;
    public String type;


    public Login(String usn,String pw) {
        this.username = usn;
        this.pass = pw;
        this.type = "login";
    }

    public String build_login() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
