package matwal6;
/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

import java.util.Scanner;
import java.util.ArrayList;

/**
 * BankAdmin - innehåller main. Startar och hanterar huvudmenyn för bankapplikationen
 * @author matswallgren
 *
 */
public class BankAdmin {

    private BankLogic mbank = new BankLogic();
    public static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        BankAdmin bankMenu = new BankAdmin();
        bankMenu.menuChoice();
    }



    //get input and output from the menu.
    public void menuChoice() {
        int choice = 0;

        do {
            menu();
            choice = keyboard.nextInt();
            keyboard.nextLine();

            switch (choice) {

                //print list of customers
                case 10:
                    System.out.println("List of customers: ");
                    System.out.println(mbank.getAllCustomers());
                    break;

                //add customer
                case 11:
                    System.out.println("Name: ");
                    String name = keyboard.nextLine();
                    System.out.println("Family name: ");
                    String surname = keyboard.nextLine();
                    System.out.println("Personal ID: ");
                    String pNo = keyboard.nextLine();
                    //keyboard.hasNextLine(); 			//rensar bort returtecken ur inmatninsbufferten
                    mbank.createCustomer(name, surname, pNo);
                    break;

                //change name of customer
                case 12:
                    System.out.println("Change name of customer: ");
                    System.out.println("Enter Personal ID: ");
                    String pNo3 = keyboard.nextLine();
                    System.out.println("New name: ");
                    String name3 = keyboard.nextLine();

                    System.out.println("New family name: ");
                    String surname3 = keyboard.nextLine();

                    mbank.changeCustomerName(name3, surname3, pNo3);
                    break;

                //delete customer
                case 13:
                    System.out.println("Personal ID for custoer do delete: ");
                    String pNo4 = keyboard.nextLine();

                    System.out.println("Customer deleted: " + mbank.deleteCustomer(pNo4));
                    break;

                //add account
                case 21:
                    System.out.println("Add savings acount for a customer ");
                    System.out.println("Enter Personal ID: ");
                    String pNo5 = keyboard.nextLine();
                    System.out.println("New account ID: " + mbank.createSavingsAccount(pNo5));
                    break;

                //add credit accout
                case 22:
                    System.out.println("Add credit acount for a customer ");
                    System.out.println("Enter Personal ID: ");
                    String pNo55 = keyboard.nextLine();
                    System.out.println("New account ID: " + mbank.createCreditAccount(pNo55));
                    break;

                //view customer info
                case 23:
                    System.out.println("View customer info");
                    System.out.println("Enter Personal ID: ");
                    String pNo6 = keyboard.nextLine();
                    System.out.println("Customer info: " + mbank.getCustomer(pNo6));
                    break;

                //deposit
                case 24:
                    System.out.println("Deposit to account");
                    System.out.println("Enter Personal ID: ");
                    String pNo7 = keyboard.nextLine();
                    System.out.println("Enter account ID: ");
                    int ac7 = keyboard.nextInt();
                    System.out.println("Enter amount: ");
                    double amount7 = keyboard.nextDouble();
                    System.out.println("Transaction: " + mbank.deposit(pNo7, ac7, amount7));
                    break;

                //withdraw
                case 25:
                    System.out.println("Withdraw from account");
                    System.out.println("Enter Personal ID: ");
                    String pNo8 = keyboard.nextLine();
                    System.out.println("Enter account ID: ");
                    int ac8 = keyboard.nextInt();
                    System.out.println("Enter amount: ");
                    double amount8 = keyboard.nextDouble();
                    System.out.println("Transaction: " + mbank.withdraw(pNo8, ac8, amount8));
                    break;

                //print transactions
                case 26:
                    System.out.println("Print transactions from account");
                    System.out.println("Enter Personal ID: ");
                    String pNo88 = keyboard.nextLine();
                    System.out.println("Enter account ID: ");
                    int ac88 = keyboard.nextInt();
                    System.out.println("Transaction: " + mbank.getTransactions(pNo88, ac88));
                    break;

                //close account
                case 30:
                    System.out.println("Close account");
                    System.out.println("Enter Personal ID: ");
                    String pNo9 = keyboard.nextLine();
                    System.out.println("Enter account ID: ");
                    int ac9 = keyboard.nextInt();
                    System.out.println("Account closed: " + mbank.closeAccount(pNo9, ac9));
                    break;

            }


        } while (choice != 99);
    }

    //print menu on screen
    public static void menu() {
        System.out.println("\nSTART BANK MENU \n");
        System.out.println("10. Print list of all customers");
        System.out.println("11. Add customer");
        System.out.println("12. Change name of customer");
        System.out.println("13. Delete customer \n");
        System.out.println("Customer: ");
        System.out.println("21. Add savings account for customer");
        System.out.println("22. Add credit account for customer");
        System.out.println("23. View customer info including all accounts");
        System.out.println("24. Deposit");
        System.out.println("25. Withdraw");
        System.out.println("26. View transaction for account");
        System.out.println("30. Close account\n");
        System.out.println("99. Exit\n");
        System.out.println("Your choice: ");
    }

}
