package packet_handler;
import com.google.gson.Gson;

public class CreateAccount {
    public String username;
    public String pass;
    public String type;


    public CreateAccount(String usn,String pw) {
        this.username = usn;
        this.pass = pw;
        this.type = "create_account";
    }

    public String build_create_account() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
