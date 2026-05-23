package utilities_handler;

import user_handler.User;

import java.sql.*;


public class mysqlAction {
    Connection koneksi = null;

    public mysqlAction() {
        koneksi = MysqlUtility.getConnection();
         System.out.println("[Server-Logs] MYSQL Service is now available");
    }
    public Boolean add(User usr) {
        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, usr.getUsername());
            ps.setString(2, usr.getPassword());
            ps.executeUpdate();
            System.out.println("[Server-Logs] Created account: " + usr.getUsername());
            return true;
        } catch (SQLException e) {
            System.out.println("Gagal insert: " + e.getMessage());
            return false;
        }
    }
    public Boolean checkExist(String username) {
        try {
            String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, username);

            System.out.println("Checking username => [" + username + "]");
            ResultSet rs = ps.executeQuery();
            boolean result = rs.next();

        //System.out.println("SQL Result => " + result);
            return result;

        } catch (SQLException e) {
            System.out.println("Gagal cek user: " + e.getMessage());
        }
        return false;
    }

    public Boolean checkPassword(User usr){
        try {
            String sql = "SELECT 1 FROM users WHERE username = ? and password = ? LIMIT 1";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, usr.getUsername());
            ps.setString(2, usr.getPassword());
            System.out.println("Checking password => [" + usr.getUsername() + "]");
            ResultSet rs = ps.executeQuery();
            boolean result = rs.next();
            return result;

        } catch(SQLException e){
            System.out.println("Gagal cek user: " + e.getMessage());
            return false;
        }


    }
    
}



/*
INSERT INTO users(username, password)
VALUES ('galvin', '12345');
*/