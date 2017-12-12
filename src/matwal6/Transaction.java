package matwal6;
/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;


//this class keep track of transactions in each account

public class Transaction {

    private String pNo;
    private int accountId;
    private String timeStamp;
    private double amount;
    private double balance;


    public Transaction(String pNo, int accountId, double amount, double balance) {
        this.pNo = pNo;
        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;

        this.timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public Transaction(String pNo, int accountId, String timeStamp, double amount, double balance) {
        this.pNo = pNo;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.balance = balance;
    }

    public String toString() {
        return timeStamp + " " + amount + " " + balance;
    }

    public String getpNo() {
        return pNo;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

}
