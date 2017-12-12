package matwal6;

/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

public class CreditAccount extends Account {

    private static final double MIN_BALANCE = -5000;

    private double interestRate;
    private double negativeInterestRate = 7.0;
    private double positiveInterestRate = 0.5;


    //constructor
    public CreditAccount(String pNo) {
        super(pNo);
        super.accountType = "Kreditkonto";
        this.interestRate = getInterestRate();

        int accountIdToSet = getLastAssignedAccountId()+1;
        setAccountID(accountIdToSet);
        setLastAssignedAccountId(accountIdToSet);


    }

    public CreditAccount(int accountID, double balance, String pNo, String accountType) {
        super(accountID, balance, pNo, accountType);
    }

    //methods
    @Override
    public double withdraw(double amount) {
        if (getLimit() > amount) {
            this.setBalance(this.getBalance() - amount);
            return amount;
        } else {
            return 0;
        }
    }

    public double calcInterestRate() {
        return getBalance() * interestRate / 100;
    }

    public String toStringSpec() {
        return accountType + " " + interestRate;
    }

    public double getLimit() {
        return getBalance() - MIN_BALANCE;
    }

    //getters and setters
    public double getInterestRate() {
        if (this.getBalance() >= 0) {
            interestRate = positiveInterestRate;
        } else {
            interestRate = negativeInterestRate;
        }
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }



}
