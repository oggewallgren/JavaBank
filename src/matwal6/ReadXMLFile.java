package matwal6;
/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import static matwal6.GuiBank.mybank;


public class ReadXMLFile {

    public void test(){
        mybank.createCustomer("Mats", "surname", "998877");
    }

    public boolean readCustomerFile() {


        //import of customers------------------
        try {

            File fXmlFile = new File(".//src/matwal6/fileCustomers.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //Puts all Text nodes in the full depth
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("customer");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String surname = eElement.getElementsByTagName("surname").item(0).getTextContent();
                    String pNo = eElement.getElementsByTagName("pNo").item(0).getTextContent();

                    //add customers to mybank
                    mybank.createCustomer(name, surname, pNo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //import of accounts-----------------------------
        try {

            File fXmlFile = new File(".//src/matwal6/fileAccount.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //Puts all Text nodes in the full depth
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("account");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String accountId = eElement.getElementsByTagName("accountId").item(0).getTextContent();
                    String sbalance = eElement.getElementsByTagName("balance").item(0).getTextContent();
                    String pNo = eElement.getElementsByTagName("pNo").item(0).getTextContent();
                    String accountType = eElement.getElementsByTagName("accountType").item(0).getTextContent();

                    //convert strings to int and double
                    int iaccountId = Integer.parseInt(accountId);
                    double balance = Double.parseDouble(sbalance);

                    //add account to mybank
                    mybank.createAccount(iaccountId,  balance,  pNo,  accountType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //import of transactions------------------
        try {

            File fXmlFile = new File(".//src/matwal6/fileTrans.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //Puts all Text nodes in the full depth
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("transaction");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String pNo = eElement.getElementsByTagName("pNo").item(0).getTextContent();
                    String saccountId = eElement.getElementsByTagName("accountId").item(0).getTextContent();
                    String timeStamp = eElement.getElementsByTagName("timeStamp").item(0).getTextContent();
                    String samount = eElement.getElementsByTagName("amount").item(0).getTextContent();
                    String sbalance = eElement.getElementsByTagName("balance").item(0).getTextContent();

                    //convert strings to int and double
                    int accountId = Integer.parseInt(saccountId);
                    double amount = Double.parseDouble(samount);
                    double balance = Double.parseDouble(sbalance);

                    //add transactions to mybank
                    Transaction t = new Transaction(pNo, accountId, timeStamp, amount, balance);
                    mybank.trans.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Transactions after xml-import" + mybank.getAllTransactions());


        return true;
    }

}
