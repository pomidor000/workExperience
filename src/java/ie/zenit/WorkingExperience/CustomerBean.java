package ie.zenit.WorkingExperience;

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
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named("customerBean")  

public class CustomerBean implements Serializable {

    private String mobile;
    private String nameOnCard;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private short CCV;
    private String customerName;
    private String customerSurname;
    private String customerStreet;
    private String customerTown;
    private String customerCounty;
    private byte amountTopUp;
    private byte topUpDate;
    private String provider;
            //     private variables, only visible in the method,
            //     to be sure, we will not leak any data 
    private final SessionStore session = new SessionStore(); 
    /* session variable, this variable is specified session variable to store the status of the session, 
     session checks if the employeee is logged in even if we work in html pages, and overclicking it, 
     this variable is     importatnt to set the customer logged on for all pages, without interaption*/

    
    // getters and setters for all private variables
    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String name) {
        this.mobile = name;
    }

    public String getNameOnCard() {
        return this.nameOnCard;
    }

    public void setNameOnCard(String name) {
        this.nameOnCard = name;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String name) {
        this.cardNumber = name;
    }

    public String getExpiryMonth() {
        return this.expiryMonth;
    }

    public void setExpiryMonth(String name) {
        this.expiryMonth = name;
    }

    public String getExpiryYear() {
        return this.expiryYear;
    }

    public void setExpiryYear(String name) {
        this.expiryYear = name;
    }

    public short getCCV() {
        return this.CCV;
    }

    public void setCCV(short name) {
        this.CCV = name;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public String getCustomerSurname() {
        return this.customerSurname;
    }

    public void setCustomerSurname(String name) {
        this.customerSurname = name;
    }

    public String getCustomerStreet() {
        return this.customerStreet;
    }

    public void setCustomerStreet(String name) {
        this.customerStreet = name;
    }

    public String getCustomerTown() {
        return this.customerTown;
    }

    public void setCustomerTown(String name) {
        this.customerTown = name;
    }

    public String getCustomerCounty() {
        return this.customerCounty;
    }

    public void setCustomerCounty(String name) {
        this.customerCounty = name;
    }

    public byte getAmountTopUp() {
        return this.amountTopUp;
    }

    public void setAmountTopUp(byte name) {
        this.amountTopUp = name;
    }

    public byte getTopUpDate() {
        return this.topUpDate;
    }

    public void setTopUpDate(byte name) {
        this.topUpDate = name;
    }

    public CustomerBean() { // constructor for this class is necessary, it is checking the connection with Mysql and html
        try {
            // it will show of the comments in glassfish server 
            Logger.getLogger(CustomerBean.class.getName()).log(Level.INFO, "Registering ...");

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getLoginStatus() { /* this is method for retrieving the status of the login, we can use it in html file to chcek it if the employee is logged in */

        Object myLoginData = session.retrieve("userName");
        if (myLoginData != null) {
            String myEmail = (String) myLoginData;
            System.out.println("Hello " + myEmail + ",/nYou are logged in");
            return true;
        } else {
            return false;
        }
    }
    public Object Save() throws SQLException {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256"); // it is type of coding, which we use for password, to hide it and decrypted.
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (digest == null) {
            return "not signed in"; // we can handle an exception properly in here
        }
        byte[] hash = null;
        
        if (hash == null) {
            return "not signed in"; // we can ahndle an exception properly in here
        }
        String stringHash = String.format("%064x", new java.math.BigInteger(1, hash));

        String URL = "jdbc:mysql://localhost:3306/working_experience?user=root&password=password";
        Connection connection = DriverManager.getConnection(URL, "root", "password"); 
        // try of mysql connection, with username and password, individual for every mysql settings

        try {
            PreparedStatement saveRecord = connection.prepareStatement("INSERT INTO customersdata(mobile_phone, nameOnCard, card_nr, "
                    + " expiry_month, expiry_year, CCV, name_on_card, customer_surname, customer_street, custmer_town, customer_county, "
                    + "top_up_amount, top_up_date) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,?, ?, ?, ?);");

            saveRecord.setString(1, this.mobile);
            saveRecord.setString(2, this.nameOnCard);
            saveRecord.setString(3, this.cardNumber);
            saveRecord.setString(4, this.expiryMonth);
            saveRecord.setString(5, this.expiryYear);
            saveRecord.setShort(6, this.CCV);
            saveRecord.setString(7, this.customerName);
            saveRecord.setString(8, this.customerSurname);
            saveRecord.setString(9, this.customerStreet);
            saveRecord.setString(10, this.customerTown);
            saveRecord.setString(11, this.customerCounty);
            saveRecord.setByte(12, this.amountTopUp);
            saveRecord.setByte(13, this.topUpDate);
            saveRecord.setString(14, this.provider);
            saveRecord.execute();
            session.store("mobile_phone", this.mobile);
            session.store("name_on_card", this.nameOnCard);
            session.store("card_nr", this.cardNumber);
            session.store("expiry_month", this.expiryMonth);
            session.store("expiry_year", this.expiryYear);
            session.store("CCV", this.CCV);
            session.store("customer_name", this.customerName);
            session.store("customer_surname", this.customerSurname);
            session.store("customer_street", this.customerStreet);
            session.store("customer_town", this.customerTown);
            session.store("customer_county", this.customerCounty);
            session.store("top_up_amount", this.amountTopUp);
            session.store("top_up_date", this.topUpDate);
            session.store("provider", this.provider);
            
            
        } catch (SQLException ex) {
            return "register";
        } finally {
            connection.close();
        }
        return "database saved";
    
    }
}
