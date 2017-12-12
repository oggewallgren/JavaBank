package matwal6;

public class SavingsAccount extends Account {

    //number of free withdraws, fee after that (amount * fee)
    private static final int FREE_WITHDRAW = 1;
    private static final double WITHDRAWFEE = 0.02;

    //min balance for this account type
    private static final double MIN_BALANCE = 0;

    private int withDrawCount;
    private double interestRate;

    //constructor
    public SavingsAccount(String pNo) {
        super(pNo);
        super.accountType = "Sparkonto";
        this.interestRate = 1.0;
        this.withDrawCount = 0;

        int accountIdToSet = getLastAssignedAccountId()+1;
        setAccountID(accountIdToSet);
        setLastAssignedAccountId(accountIdToSet);

    }

    //methods

    //do withdraw, check limit, add fee if more times than limit
    @Override
    public double withdraw(double amount) {
        if (withDrawCount++ >= FREE_WITHDRAW) amount = amount * (1 + WITHDRAWFEE);
        if (getLimit() >= amount) {
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
