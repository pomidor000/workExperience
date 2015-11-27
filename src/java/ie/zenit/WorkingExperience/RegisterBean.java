package ie.zenit.WorkingExperience;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author DLights
 */
@ManagedBean
@RequestScoped
public class RegisterBean {

    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return "";
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterBean() {
        try {
            // it will show of the comments in glassfish server 
            Logger.getLogger(RegisterBean.class.getName()).log(Level.INFO, "Registering of that crap");

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }

    public void save() throws SQLException {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (digest == null) {
            return; // we can ahndle an exception properly in here
        }
        byte[] hash = null;
        try {
            hash = digest.digest(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hash == null) {
            return; // we can ahndle an exception properly in here
        }
        String stringHash = String.format("%064x", new java.math.BigInteger(1, hash));

        String URL = "jdbc:mysql://localhost:3306/working_experience?user=root&password=password";
        Connection connection = DriverManager.getConnection(URL, "root", "password");

        try {
            PreparedStatement saveRecord = connection.prepareStatement("INSERT INTO agents(email, hash) VALUES (?, ?);");

            saveRecord.setString(1, this.email);
            saveRecord.setString(2, stringHash);
            saveRecord.execute();

        } finally {
            connection.close();
        }
    }

    public String getGreetings() {
        String greeting = "";

        if (this.email == null) {
            greeting = "";
        } else {
            greeting = "greetings " + this.email;
        }

        return greeting;
    }

}
