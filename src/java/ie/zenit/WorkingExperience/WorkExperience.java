package ie.zenit.WorkingExperience;
// package statement must be first, if we specify it
import java.io.Serializable;
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
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DLights
 */
@ManagedBean(name = "registerBean")
@SessionScoped

public class WorkExperience implements Serializable {

    private String email;
    private String password;
    private final SessionStore session = new SessionStore(); // private variables, only visible in the method, to be sure, we will not leak any data

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return "";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WorkExperience() { // constructor for this class is necessary, it is checking the connection with Mysql and html
        try {
            // it will show of the comments in glassfish server 
            Logger.getLogger(WorkExperience.class.getName()).log(Level.INFO, "Registering ...");
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            Logger.getLogger(WorkExperience.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() throws SQLException {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(WorkExperience.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (digest == null) {
            return; // we can handle an exception properly in here
        }
        byte[] hash = null; 
        try {
            hash = digest.digest(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WorkExperience.class.getName()).log(Level.SEVERE, null, ex);
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
            session.store("loginData", this.email);
        } finally {
            connection.close();
        }
    }

    public boolean getLoginStatus() {
        Object myLoginData = session.retrieve("loginData");
        if (myLoginData != null) {
            String myEmail = (String) myLoginData;
            System.out.println("Hello " + myEmail + ",/nYou are logged in");
            return true;
        } else {
            return false;
        }
    }

    public String getGreetings() {
        String greeting = "";

        return greeting;
    }
}
