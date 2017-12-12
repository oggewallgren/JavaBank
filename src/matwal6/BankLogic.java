package matwal6;

/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

import java.util.ArrayList;
import java.util.Objects;

/**
 * Banklogic håller de metoder som behövs för att köra.
 * I huvudsak detsamma enligt övningsuppgift. Någon enstaka har tillkommit och metoderna är i övrigt kompletterade.
 */

public class BankLogic {

    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Account> acc = new ArrayList<>();
    ArrayList<Transaction> trans = new ArrayList<>();

    public BankLogic() {
    }

    public boolean checkExistPNo(String pNo) {
        //return true if pNo exist
        if (customers.size() > 0) {
            for (Customer customer : customers) {
                String n = customer.getpNo();
                if (pNo.equals(n)) return true;
            }
        }
        return false;

    }

    public boolean changeCustomerName(String name, String surname, String pNo) {
        if (customers.size() > 0) {
            for (int i = 0; i < customers.size(); i++) {
                if (Objects.equals(customers.get(i).getpNo(), pNo)) {
                    customers.get(i).setName(name);
                    customers.get(i).setSurname(surname);
                    return true;
                }
            }
        }
        /**
         Byter namn på vald kund, inparametern pNo anger vilken kund som ska få nytt namn.
         Returnerar true om namnet ändrades annars returnerar false (alltså om kunden inte fanns).
         */
        return false;
    }


    //create and add methods customer account transactions
    public boolean createCustomer(String name, String surname, String pNo) {

        //add customer if pNo don't exist
        if (!checkExistPNo(pNo)) {
            Customer aCustomer = new Customer(name, surname, pNo);
            customers.add(aCustomer);
            return true;
        } else return false;

        /**
         Skapar upp en ny kund utifrån de inparametrar som skickas in till metoden.
         Personnummer måste vara unikt, så om en kund redan finns med det personnumret så ska en ny kund inte skapas.
         Returnerar true om kund skapades annars returneras false
         */
    }

    public void createAccount(int accountID, double balance, String pNo, String accountType) {

        //add Account if pNo exits
        if (checkExistPNo(pNo)) {
            //AccountImport account = new AccountImport();
            CreditAccount account = new CreditAccount(accountID, balance, pNo, accountType);
            acc.add(account);
        }
    }

    public int createCreditAccount(String pNo) {

        //add savingsAccount if pNo exits
        if (checkExistPNo(pNo)) {
            CreditAccount creditAccount = new CreditAccount(pNo);
            acc.add(creditAccount);
            return creditAccount.getLastAssignedAccountId();
        } else return -1;

        /**
         Skapar ett konto till kund med personnummer pNo
         Kontonummer ska vara unika för hela banken, inte bara för en enskild kund
         ----- (se Big Java Late Objects på s. 400 för tips på lösning).
         Returnerar kontonumret som det skapade kontot fick
         Alternativt returneras –1 om inget konto skapades
         */
    }

    public int createSavingsAccount(String pNo) {

        //add savingsAccount if pNo exits
        if (checkExistPNo(pNo)) {
            SavingsAccount savingsAccount = new SavingsAccount(pNo);
            acc.add(savingsAccount);
            return savingsAccount.getLastAssignedAccountId();
        } else return -1;

        /**
         Skapar ett konto till kund med personnummer pNo
         Kontonummer ska vara unika för hela banken, inte bara för en enskild kund
         ----- (se Big Java Late Objects på s. 400 för tips på lösning).
         Returnerar kontonumret som det skapade kontot fick
         Alternativt returneras –1 om inget konto skapades
         */
    }

    public boolean deposit(String pNo, int accountId, double amount) {
        if (!checkExistPNo(pNo)) return false;
        for (int i = 0; i < acc.size(); i++) {
            if (accountId == acc.get(i).getAccountID() && pNo.equals(acc.get(i).getPNo())) {
                acc.get(i).deposit(amount);
                addTransaction(pNo, accountId, amount, acc.get(i).getBalance());

                return true;
            }
        }
        return false;
        /**
         Gör en insättning på konto med kontonnummer accountId som tillhör kunden pNo.
         Returnerar true om det gick bra annars false
         */
    }

    public boolean withdraw(String pNo, int accountId, double amount) {
        if (!checkExistPNo(pNo)) return false;

        for (Account a : acc) {
            if (a.getAccountID() == accountId && a.getPNo().equals(pNo) && a.getLimit() >= amount) {
                double transAmount = a.withdraw(amount);
                if (transAmount > 0) {
                    addTransaction(pNo, accountId, -transAmount, a.getBalance());
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
        /**
         Gör ett uttag på konto med kontonnummer accountId som tillhör kunden pNo.
         Uttaget genomförs endast om saldot täcker uttaget (saldot får inte bli mindre än 0)
         Returnerar true om det gick bra annars false
         */
    }

    public void addTransaction(String pNo, int accountId, double amount, double balance) {
        Transaction t = new Transaction(pNo, accountId, amount, balance);
        trans.add(t);
    }

    public void addTransaction(String pNo, int accountId, String timeStamp, double amount, double balance) {
        Transaction t = new Transaction(pNo, accountId, timeStamp, amount, balance);
        trans.add(t);
    }


    //delete methods
    public ArrayList<String> deleteCustomer(String pNo) {

        ArrayList<String> dc = new ArrayList<>();

        if (checkExistPNo(pNo)) {
            dc = getCustomerPlus(pNo);

            for (int i = 0; i < acc.size(); i++) {
                if (pNo.equals(acc.get(i).getPNo())) acc.remove(i);
            }
            for (int i = 0; i < customers.size(); i++) {
                if (pNo.equals(customers.get(i).getpNo())) customers.remove(i);
            }
            return dc;
        } else return null;
        /**
         Tar bort kund ur banken, alla kundens eventuella konton tas också bort och resultatet returneras.
         Returnerar null om ingen kund togs bort
         Listan som returneras ska innehålla information om kund på första platsen i
         ArrayListen (förnamn efternamn personnummer) sedan följer de konton som togs
         bort (kontonr saldo kontotyp räntesats ränta). Ränta beräknas som
         saldo*räntesats/100 (ränta behöver enbart beräknas vid borttagning av konton
         då banken i denna version inte stödjer årsskiften).
         Det som returneras ska se ut som följer:
         [Lotta Larsson 7505121231, 1004 0.0 Sparkonto 1.0 0.0, 1005 700.0 Sparkonto 1.0 7.0]
         */
    }

    public String closeAccount(int accountId) {
        String result;

        for (int i = 0; i < acc.size(); i++) {
            if (accountId == acc.get(i).getAccountID()) {
                double bal = acc.get(i).getBalance();
                double ir = acc.get(i).calcInterestRate();
                acc.remove(i);
                result = "Konto: " + accountId + " saldo: " + bal + " upplupen ränta: " + ir + " borttaget.";
                return result;
            }
        }
        return null;
        /**
         Avslutar ett konto med kontonnummer accountId. När
         man avslutar ett konto skall räntan beräknas som saldo*ränta/100.
         OBS! Enda gången ränta läggs på är när kontot tas bort eftersom årsskiften inte hanteras i denna version av systemet.
         Presentation av kontot ska returneras inklusive räntan man får på pengarna (kontonummer saldo, kontotyp räntesats, ränta)
         Returnerar null om inget konto togs bort

         */


    }

    public String closeAccount(String pNo, int accountId) {
        if (!checkExistPNo(pNo)) return null;

        for (int i = 0; i < acc.size(); i++) {
            if (accountId == acc.get(i).getAccountID() && pNo.equals(acc.get(i).getPNo())) {
                double ir = acc.get(i).calcInterestRate();
                String result = acc.get(i).toString() + " " + ir;
                acc.remove(i);
                return result;
            }
        }
        return null;
        /**
         Avslutar ett konto med kontonnummer accountId som tillhör kunden pNo. När
         man avslutar ett konto skall räntan beräknas som saldo*ränta/100.
         OBS! Enda gången ränta läggs på är när kontot tas bort eftersom årsskiften inte hanteras i denna version av systemet.
         Presentation av kontot ska returneras inklusive räntan man får på pengarna (kontonummer saldo, kontotyp räntesats, ränta)
         Returnerar null om inget konto togs bort
         */
    }


    //get methods
    public ArrayList<Customer> getArrayOfAllCustomers(){
        return customers;
    }

    public String getCustomerID(int order){
        if(order >= 0){
            return customers.get(order).getpNo();
        } else return null;
        //returnerar person ID för aktuell kund - ny metod för uppgift 6
    }

    public ArrayList<String> getAllCustomers() {
        ArrayList<String> arrayList = new ArrayList<>();

        if (customers.size() > 0) {
            for (int i = 0; i < customers.size(); i++) {
                String aElement = customers.get(i).getName() + " " + customers.get(i).getSurname()
                        + " " + customers.get(i).getpNo();
                arrayList.add(aElement);
            }
            return arrayList;
        } else {
            return null;
        }

        /**
         * Returnerar en ArrayList<String> som innehåller en presentation av bankens alla kunder på följande sätt:
         [Karl Karlsson 8505221898, Pelle Persson 6911258876, Lotta Larsson 7505121231]
         */
    }

    public ArrayList<String> getCustomer(String pNo) {
        ArrayList<String> gc = new ArrayList<>();

        if (!checkExistPNo(pNo)) return null;

        //add name and pNo
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getpNo().equals(pNo)) {
                gc.add(customers.get(i).getName() + " " + customers.get(i).getSurname() + " " + customers.get(i).getpNo());
            }
        }

        //get account info: accountid, balance, accounttype, and interest rate
        for (int i = 0; i < acc.size(); i++) {
            if (acc.get(i).getPNo().equals(pNo)) {
                gc.add(acc.get(i).getAccountID() + " " +
                        acc.get(i).getBalance() + " " +
                        acc.get(i).getAccountType() + " " +
                        acc.get(i).getInterestRate());
            }
        }
        return gc;

        /**
         Returnerar en ArrayList<String> som innehåller informationen om kunden inklusive dennes konton.
         Returnerar null om kunden inte fanns
         Första platsen i listan är reserverad för kundens namn och personnummer sedan följer informationen om kundens konton (kontonr saldo kontotyp räntesats)
         Exempel på hur det som returneras ska se ut:
         [Lotta Larsson 7505121231, 1004 0.0 Sparkonto 1.0, 1005 0.0 Sparkonto 1.0]
         */
    }

    public ArrayList<String> getCustomerPlus(String pNo) {
        ArrayList<String> gcp = new ArrayList<>();

        if (!checkExistPNo(pNo)) return null;

        //add name and pNo
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getpNo().equals(pNo)) {
                gcp.add(customers.get(i).getName() + " " + customers.get(i).getSurname() + " person ID: " + customers.get(i).getpNo());
            }
        }

        //get account info: accountid, balance, accounttype, and interest rate
        for (int i = 0; i < acc.size(); i++) {
            if (acc.get(i).getPNo().equals(pNo)) {
                gcp.add(" "                 + acc.get(i).getAccountType() +
                        ": "                + acc.get(i).getAccountID() +
                        " med saldo: "      + acc.get(i).getBalance() +
                        " räntesats: "      + acc.get(i).getInterestRate() +
                        " ränta: "          + acc.get(i).calcInterestRate());
            }
        }
        return gcp;

        /**
         Returnerar en ArrayList<String> som innehåller informationen om kunden inklusive dennes konton.
         Och även räntan på konto
         Returnerar null om kunden inte fanns

         */
    }

    public String getSurname(String pNo) {
        if (!checkExistPNo(pNo)) return null;

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getpNo() == pNo) {
                return customers.get(i).getSurname();
            }
        }
        return null;
        /**
         Returnerar en String med kundens förnamn
         Tillagd metod i uppgift 6
         */

    }

    public String getName(String pNo) {
        if (!checkExistPNo(pNo)) return null;

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getpNo() == pNo) {
                return customers.get(i).getName();
            }
        }
        return null;
        /**
         Returnerar en String med kundens efternamn
         Tillagd metod i uppgift 6
         */

    }

    public ArrayList<Account> getAllAccounts() {
        return acc;
    }

    public String getAccount(String pNo, int accountId) {
        if (!checkExistPNo(pNo)) return null;

        for (int i = 0; i < acc.size(); i++) {
            if (accountId == acc.get(i).getAccountID() && pNo.equals(acc.get(i).getPNo())) {
                return acc.get(i).toString();
            }
        }
        return null;
        /**
         Returnerar en String som innehåller presentation av kontot med kontonnummer
         accountId som tillhör kunden pNo (kontonummer saldo, kontotyp räntesats).
         Returnerar null om konto inte finns eller om det inte tillhör kunden
         */

    }

    public ArrayList<Integer> getAccounts(String pNo) {
        ArrayList<Integer> ac = new ArrayList<>();

        if (!checkExistPNo(pNo)) return null;

        for (int i = 0; i < acc.size(); i++) {
            if (acc.get(i).getPNo().equals(pNo)){
                ac.add(acc.get(i).getAccountID());
            }
        }
        return ac;
    }

    public ArrayList<Transaction> getAllTransactions() {
        return trans;
    }

    public ArrayList<String> getTransactions(int accountId) {
        if (trans.isEmpty()) return null;

        ArrayList<String> transList = new ArrayList<>();

        for (Transaction t : trans) {
            if (t.getAccountId() == accountId) {
                transList.add(t.toString());
            }
        }
        return transList;

        /**
         * Hämtar en lista som innehåller presentation av konto samt alla transaktioner som gjorts
         * på kontot, ex: [2017-01-30 09:17:06 -500.0 -500.0, 2017-01-30 09:17:11 -4000.0 -4500.0]
         * Endast kontonummer som parameter
         */
    }

    public ArrayList<String> getTransactions(String pNr, int accountId) {
        if (trans.isEmpty()) return null;

        ArrayList<String> transList = new ArrayList<>();

        for (Transaction t : trans) {
            if (Objects.equals(t.getpNo(), pNr) && t.getAccountId() == accountId) {
                transList.add(t.toString());
            }
        }
        return transList;

        /**
         * Hämtar en lista som innehåller presentation av konto samt alla transaktioner som gjorts
         * på kontot, ex: [2017-01-30 09:17:06 -500.0 -500.0, 2017-01-30 09:17:11 -4000.0 -4500.0]
         */
    }


}
