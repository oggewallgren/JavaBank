package matwal6;
/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JFileChooser;


/**
 * GUI to use in bank example.
 * On start it add 2 customers x 2 account to work with.
 * User can add, delete change name of customers.
 * For customer add or delete savings and credit account.
 * For account deposit and withdraw.
 * Interest is calculated when account or customer deletes.
 * For account print transactionlist.
 */
public class GuiBank extends JFrame  implements ActionListener {
    private JTextArea infoTextArea;

    private JTextField surnameField;
    private JTextField nameField;
    private JTextField pNoField;
    private JTextField amountField;

    private JList transactionList;
    private JList customerList;
    private JList accountList;

    private static final int FRAMEWIDTH = 1000;

    //create new BankLogic and name it mybank
    public static BankLogic mybank = new BankLogic();


    public static void main(String[] args) {
        GuiBank bankFrame = new GuiBank();
        bankFrame.setVisible(true);
    }


    /**
     * Constructor.
     */
    public GuiBank() {

        //construct menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createFileMenu());

        //initiate instance variables
        initiateInstanceVariables();

        //build GUI frame
        buildFrame();

        //clear and update list of customers
        clear();
        viewCustomers();

    }

    /**
     * Creates menu and adds menu items to menu.
     * @return  the file menu to frame.
     */
    public JMenu createFileMenu(){

        JMenu menu = new JMenu("File");
        menu.add(createFileImportItem());
        menu.add(createFileExportItem());
        menu.add(createFileExitItem());
        return menu;

    }

    /**
     * Creates menu item Exit and actions done when clicked.
     * Closes program.
     * @return  menu item.
     */
    public JMenuItem createFileExitItem(){
        JMenuItem item = new JMenuItem("Exit");

        class MenuItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                System.exit(0);
            }
        }

        ActionListener listener = new MenuItemListener();
        item.addActionListener(listener);
        return item;
    }

    /**
     * Creates menu item Import and actions done when clicked.
     * Import file. User specify file to import.
     * @return  menu item.
     */
    public JMenuItem createFileImportItem(){
        JMenuItem item = new JMenuItem("Import");

        class MenuItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event){

                //create new BankLogic and name it mybank
                //and delete old mybank if any
                mybank = new BankLogic();

                //import xml-files in this package

                ReadXMLFile rx = new ReadXMLFile();
                if(rx.readCustomerFile()){
                    clear();
                    viewCustomers();

                    //import message
                    int c = mybank.customers.size();
                    int a = mybank.acc.size();
                    int t = mybank.trans.size();
                    infoTextArea.setText("Importerat från fil: " + c + " kunder,  " + a + " konton och " + t + " transaktioner.");
                }
            }
        }

        ActionListener listener = new MenuItemListener();
        item.addActionListener(listener);
        return item;
    }

    /**
     * Creates menu item Export and actions done when clicked.
     * Export file.
     * @return  menu item.
     */
    public JMenuItem createFileExportItem(){
        JMenuItem item = new JMenuItem("Export");

        class MenuItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event){

                //check if any customer to export
                if (mybank.customers.size()==0){
                    infoTextArea.setText("Finns ingen data att exportera.");
                    return;
                }

                //export data to xml files
                WriteXMLFile x = new WriteXMLFile();

                if( x.createCustomerXml(mybank.customers) &
                    x.createAccountXml(mybank.getAllAccounts()) &
                    x.createTransactionXml(mybank.getAllTransactions())){

                    clear();
                    viewCustomers();

                    //export message
                    int c = mybank.customers.size();
                    int a = mybank.acc.size();
                    int t = mybank.trans.size();
                    infoTextArea.setText("Exporterat till fil: " + c + " kunder,  " + a + " konton och " + t + " transaktioner.");

                }

            }
        }

        ActionListener listener = new MenuItemListener();
        item.addActionListener(listener);
        return item;
    }


    /**
     * Initiates variables used in the GUI.
     */
    private void initiateInstanceVariables() {

        //initiate all items used on the form
        infoTextArea = new JTextArea(2,70); //param rows,cols
        infoTextArea.setLineWrap(true);
        infoTextArea.setEditable(false);
        infoTextArea.setBorder(BorderFactory.createTitledBorder("Info:"));
        infoTextArea.setText("Välkommen till Java banken...");

        surnameField = new JTextField();
        surnameField.setBorder(BorderFactory.createTitledBorder("Förnamn"));

        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createTitledBorder("Efternamn"));

        pNoField = new JTextField();
        pNoField.setBorder(BorderFactory.createTitledBorder("Person ID: "));

        amountField = new JTextField();
        amountField.setBorder(BorderFactory.createTitledBorder("Ange belopp: "));

        //accountTable = new JTable(tableData, columns);        //omitted for the moment

        customerList = new JList();
        customerList.setBorder(BorderFactory.createTitledBorder("Kunder"));

        accountList = new JList();
        accountList.setBorder(BorderFactory.createTitledBorder("Kundens konton"));
        accountList.setSize(30,34);

        transactionList = new JList();
        transactionList.setBorder(BorderFactory.createTitledBorder("Registrerade transaktioner"));
    }

    /**
     * Builds the frame GUI.
     */
    private void buildFrame() {

        //Building the main frame
        setTitle("Java banken");
        setSize(FRAMEWIDTH,450);
        //setLayout(new GridLayout(1,2, 5, 5));     //rows, columns, (hgap, vgap)
        //setLayout(new FlowLayout(4,2,3));         //align, hgap, vgap
        setLayout(new BorderLayout(5,5));           //hgap, vgap
        setLocationRelativeTo(null);               //set window center

        //Builds the buttons.
        //testbutton
        JButton testButton = new JButton("Test");
        testButton.addActionListener(this);

        //general buttons
        JButton clearButton = new JButton("Rensa");
        clearButton.addActionListener(this);

        //customer buttons
        JButton newCustomerButton = new JButton("Ny kund");
        newCustomerButton.addActionListener(this);

        JButton selectCustomerButton = new JButton("Välj kund");
        selectCustomerButton.addActionListener(this);

        JButton changeNameCustomerButton = new JButton("Ändra namn");
        changeNameCustomerButton.addActionListener(this);

        JButton deleteCustomerButton = new JButton("Ta bort kund");
        deleteCustomerButton.addActionListener(this);

        //account buttons
        JButton newSavingAccountButton = new JButton("Nytt sparkonto");
        newSavingAccountButton.addActionListener(this);

        JButton newCreditAccountButton = new JButton("Ny kredit");
        newCreditAccountButton.addActionListener(this);

        JButton deleteAccountButton = new JButton("Ta bort konto");
        deleteAccountButton.addActionListener(this);

        JButton viewTransactionsListButton = new JButton("Visa transaktioner");
        viewTransactionsListButton.addActionListener(this);

        JButton depositButton = new JButton("Insättning");
        depositButton.addActionListener(this);

        JButton withdrawButton = new JButton("Uttag");
        withdrawButton.addActionListener(this);

        JButton viewAccountsButton = new JButton("Visa kundens konton");
        viewAccountsButton.addActionListener(this);

        //build startPanel
        JPanel startPanel = new JPanel(new FlowLayout());
        startPanel.add(infoTextArea);
        startPanel.add(clearButton);
        //startPanel.add(testButton);

        //build grid1Panel - 1st left
        JPanel grid1Panel = new JPanel(new GridLayout(8,1,5,5));
        grid1Panel.add(surnameField);
        grid1Panel.add(nameField);
        grid1Panel.add(pNoField);
        grid1Panel.add(newCustomerButton);
        grid1Panel.add(selectCustomerButton);
        grid1Panel.add(changeNameCustomerButton);
        grid1Panel.add(deleteCustomerButton);
        grid1Panel.add(viewAccountsButton);

        //build grid3Panel - 3nd left
        JPanel grid3Panel = new JPanel(new GridLayout(9,1,5,5));
        grid3Panel.add(newSavingAccountButton);
        grid3Panel.add(newCreditAccountButton);
        grid3Panel.add(deleteAccountButton);
        grid3Panel.add(amountField);
        grid3Panel.add(depositButton);
        grid3Panel.add(withdrawButton);
        grid3Panel.add(viewTransactionsListButton);


        //build grid5Panel - 2nd left
        JPanel grid5Panel = new JPanel(new GridLayout(2,1,5,5));
        //grid5Panel.add(accountTable.getTableHeader(),BorderLayout.PAGE_START);
        //grid5Panel.add(accountTable, BorderLayout.CENTER);
        grid5Panel.add(customerList);
        grid5Panel.add(accountList);


        //build transPanel - to the right
        JPanel transPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        transPanel.add(transactionList);

        //build gridPanel - put grids together
        JPanel gridPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        gridPanel.add(grid1Panel);
        gridPanel.add(grid5Panel);
        gridPanel.add(grid3Panel);
        gridPanel.add(transPanel);

        //add panels to frame
        add(startPanel, BorderLayout.PAGE_START);
        add(gridPanel, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Defines button listener and methods to run.
     * @param event text on button.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String buttonText = event.getActionCommand();
        if(buttonText.equals("Ny kund")){
            addCustomer();

        } else if(buttonText.equals("Välj kund")){
            selectCustomer();

        } else if(buttonText.equals("Ändra namn")){
            changeCustomerName();

        } else if(buttonText.equals("Rensa")){
            clear();

        } else if(buttonText.equals("Visa transaktioner")) {
            viewTansactionList();

        } else if(buttonText.equals("Visa kundens konton")) {
            viewAccountList();

        } else if(buttonText.equals("Ta bort kund")){
            deleteCustomer();

        } else if(buttonText.equals("Nytt sparkonto")) {
            newSavingAccount();

        } else if(buttonText.equals("Ny kredit")) {
            newCreditAccount();

        } else if(buttonText.equals("Ta bort konto")) {
            deleteAccount();

        } else if(buttonText.equals("Insättning")) {
            deposit();

        } else if(buttonText.equals("Uttag")) {
            withdraw();

        }
    }

    /**
     * Withdraw from account.
     * Check if valid fields are filled in.
     * Update info text area with actions performed.
     */
    private void withdraw(){
        //handling something not selected
        if (customerList.getSelectedValue() == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        } else if (accountList.getSelectedValue() == null){
            infoTextArea.setText("Ingen konto är valt");
            return;
        }

        //handling that text cannot be converted to double format
        try{
            Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            infoTextArea.setText("Belopp saknas/felaktigt");
            return;
        }

        String personID = mybank.getCustomerID(customerList.getSelectedIndex());
        int accountId = (int)accountList.getSelectedValue();
        double amount;

        amount = Double.parseDouble(amountField.getText());

        for (int i = 0; i < mybank.acc.size(); i++) {
            if (accountId == mybank.acc.get(i).getAccountID()) {
                if(mybank.withdraw(personID, accountId, amount)){
                    mybank.addTransaction(personID, accountId, -amount, mybank.acc.get(i).getBalance());
                    infoTextArea.setText("Uttag: " + amount + " gjord på konto: " + accountId);
                } else{
                    infoTextArea.setText("Uttag med belopp: " + amount + " kunde inte göras på konto: " + accountId);
                }
            }
        }
    }

    /**
     * Deposit to account.
     * Check if valid fields are filled in.
     * Update info text area with actions performed.
     */
    private void deposit(){
        //handling something not selected
        if (customerList.getSelectedValue() == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        } else if (accountList.getSelectedValue() == null){
            infoTextArea.setText("Ingen konto är valt");
            return;
        }

        //handling that text cannot be converted to double format
        try{
            Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            infoTextArea.setText("Belopp saknas/felaktigt");
            return;
        }

        String personID = mybank.getCustomerID(customerList.getSelectedIndex());
        int accountId = (int)accountList.getSelectedValue();
        double amount;

        amount = Double.parseDouble(amountField.getText());

        for (int i = 0; i < mybank.acc.size(); i++) {
            if (accountId == mybank.acc.get(i).getAccountID()) {
                mybank.acc.get(i).deposit(amount);
                mybank.addTransaction(personID, accountId, amount, mybank.acc.get(i).getBalance());
                infoTextArea.setText("Insättning: " + amount + " gjord på konto: " + accountId);
            }
        }
    }

    /**
     * Delete account.
     * Check if valid fields are filled in.
     * Update info text area with actions performed.
     */
    private void deleteAccount(){
        if (accountList.getSelectedValue() == null){
            infoTextArea.setText("Ingen konto finns eller är ej valt");
            return;
        }

        int accountId = (int)accountList.getSelectedValue();
        String personID = mybank.getCustomerID(customerList.getSelectedIndex());

        String str = mybank.closeAccount(accountId);
        accountList.setListData(mybank.getAccounts(personID).toArray());
        infoTextArea.setText(str);


    }

    /**
     * Creates credit account.
     * Check if valid fields are filled in.
     * Update info text area with actions performed.
     */
    private void newCreditAccount(){
        String personID = mybank.getCustomerID(customerList.getSelectedIndex());

        if(personID == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        }

        int ac = mybank.createCreditAccount(personID);
        infoTextArea.setText("Nytt kreditkonto öppnat med id: " +ac);
        accountList.setListData(mybank.getAccounts(personID).toArray());

    }

    /**
     * Creates savings account.
     * Check if valid fields are filled in.
     * Update info text area with actions performed.
     */
    private void newSavingAccount(){
        String personID = mybank.getCustomerID(customerList.getSelectedIndex());

        if(personID == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        }

        int ac = mybank.createSavingsAccount(personID);
        infoTextArea.setText("Nytt sparkonto öppnat med id: " +ac);
        accountList.setListData(mybank.getAccounts(personID).toArray());

    }

    /**
     * Set customer name in text fields to the selected customer in list.
     * Check if valid fields are filled in.
     */
    private void selectCustomer(){
        String personID = mybank.getCustomerID(customerList.getSelectedIndex());

        if(personID == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        }

        surnameField.setText(mybank.getSurname(personID));
        nameField.setText(mybank.getName(personID));
        pNoField.setText(personID);
    }

    /**
     * Change customer name according to text name fields.
     * Check if valid fields are filled in.
     * Update info text area with updated name or relevant info.
     */
    private void changeCustomerName(){
        String personID = mybank.getCustomerID(customerList.getSelectedIndex());

        if(personID == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        }

        //get customer data from input form
        String s1 = surnameField.getText();
        String s2 = nameField.getText();
        String s3 = pNoField.getText();

        if(mybank.changeCustomerName(s2, s1, s3)){
            viewCustomers();
            clear();
            infoTextArea.setText("Namn ändrat till: " + s1 + " " + s2);
        } else {
            infoTextArea.setText("Namn är inte ändrat");
        }
    }

    /**
     * Delete customer.
     * Check if valid fields are filled in.
     * Update info text area with actions performed info about closed accounts.
     */
    private void deleteCustomer(){
        String personID = mybank.getCustomerID(customerList.getSelectedIndex());

        if(personID == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        }

        Object obj = mybank.deleteCustomer(personID);

        //return info about deleted customer
        if(obj != null){
            clear();
            infoTextArea.setText("Borttagen kund: " + obj.toString());
        }else{
            infoTextArea.setText("Inga kunder att visa: " + obj.toString());
        }

        //update customer list with remaining customers
        if(mybank.customers.size() > 0){
            customerList.setListData(mybank.getAllCustomers().toArray());
        } else{
            customerList.setListData(new String [0]);
        }

    }

    /**
     * Updates customer list.
     */
    private void viewCustomers(){
        if (mybank.customers.size() == 0) return;
        customerList.setListData(mybank.getAllCustomers().toArray());
    }

    /**
     * Updates the account list.
     * Check if valid fields are filled in.
     */
    private void viewAccountList(){

        String personID = mybank.getCustomerID(customerList.getSelectedIndex());

        if(personID == null){
            infoTextArea.setText("Ingen kund är vald");
            return;
        }

        accountList.setListData(mybank.getAccounts(personID).toArray());

    }

    /**
     * Add customer.
     * Check if valid fields are filled in.
     * Update info text area with actions performed.
     */
    private void addCustomer(){

        //handling empty mandatory fields
        if (surnameField.getText().length() == 0){
            infoTextArea.setText("Inget förnamn är angivet");
            return;
        }else if (nameField.getText().length() == 0) {
            infoTextArea.setText("Inget efternamn är angivet");
            return;
        }else if (pNoField.getText().length() == 0) {
            infoTextArea.setText("Inget Person ID är angivet");
            return;
        }

        //get customer data from input form
        String s1 = surnameField.getText();
        String s2 = nameField.getText();
        String s3 = pNoField.getText();

        //add customer
        if(mybank.createCustomer(s2, s1, s3)) {
            viewCustomers();
            clear();
            infoTextArea.setText(s1 + " " + s2 + " har lagts till som kund.");
            //System.out.println(s1 + " " + s2 + " har lagts till som kund.");
        } else {
            infoTextArea.setText("Kontrollera uppgifterna, kunde ej lägga till kund.");
        }

    }

    /**
     * Clear all text fields, account and transaction list.
     */
    private void clear(){
        infoTextArea.setText("");
        surnameField.setText("");
        nameField.setText("");
        pNoField.setText("");
        amountField.setText("");
        accountList.setListData(new String [0]);
        transactionList.setListData(new String [0]);
    }

    /**
     * Updates transaction list.
     * Check if valid fields are filled in.
     */
    public void viewTansactionList(){
        if (accountList.getSelectedValue() == null){
            infoTextArea.setText("Ingen konto finns eller är ej valt");
            return;
        }

        int accountId = (int)accountList.getSelectedValue();

        if (accountId > 0){
            transactionList.setListData(mybank.getTransactions(accountId).toArray());
        } else infoTextArea.setText("Ingen konto finns eller är ej valt");

    }

}
