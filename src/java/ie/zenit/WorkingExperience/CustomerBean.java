package ie.zenit.WorkingExperience;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;

@Named("customerBean")  

public class CustomerBean implements Serializable {

    private String mobile;
    private String nameOnCard;
    private int cardNumber;
    private String expiryMonth;
    private int expiryYear;
    private short CCV;
    private String customerName;
    private String customerSurname;
    private String customerStreet;
    private String customerTown;
    private String customerCounty;
    private byte amountTopUp;
    private byte topUpDate;
    // private variables, only visible in the method, to be sure, we will not leak any data
    private final SessionStore session = new SessionStore(); /* session variable, this variable is specified session variable to store the status of the session, 
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

    public int getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(int name) {
        this.cardNumber = name;
    }

    public String getExpiryMonth() {
        return this.expiryMonth;
    }

    public void setExpiryMonth(String name) {
        this.expiryMonth = name;
    }

    public int getExpiryYear() {
        return this.expiryYear;
    }

    public void setExpiryYear(int name) {
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
    
}
