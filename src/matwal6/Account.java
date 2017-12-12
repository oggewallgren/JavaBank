package matwal6;
/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

import java.util.Scanner;
import java.util.ArrayList;


public abstract class Account {

    private int accountID;
    private static int lastAssignedAccountId = 1000;
    private double balance;
    protected String pNo;
    protected String accountType;

    public Account(String pNo){
        this.pNo = pNo;
    }

    public Account(int accountID, double balance, String pNo, String accountType) {
        this.accountID = accountID;
        this.balance = balance;
        this.pNo = pNo;
        this.accountType = accountType;
        if(accountID > lastAssignedAccountId) lastAssignedAccountId = accountID;
    }

    //methods
    public void deposit(double amount) {
        balance = balance + amount;
    }

    public abstract double withdraw(double amount);


    //getters and setters
    public int getLastAssignedAccountId() {
        return lastAssignedAccountId;
    }

    public static void setLastAssignedAccountId(int lastAssignedAccountId) {
        Account.lastAssignedAccountId = lastAssignedAccountId;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPNo() {
        return pNo;
    }

    public String getpNo() {
        return pNo;
    }

    //abstract method defines in subclass
    public abstract double getInterestRate();

    public abstract void setInterestRate(double interestRate);

    public abstract String getAccountType();

    public abstract void setAccountType(String accountType);

    public abstract String toStringSpec();

    public abstract double calcInterestRate();

    public abstract double getLimit();

    //toString methods
    public String toString() {
        return accountID + " " + balance + " " + toStringSpec();
    }

}
