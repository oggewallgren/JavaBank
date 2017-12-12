package matwal6;
/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */

//thanks and inspiration from http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class WriteXMLFile {

    //constructor
    public WriteXMLFile() {
    }

    /*
    Creating xml file for current accounts. Saves in "fileAccount.xml"
     */
    public boolean createAccountXml(ArrayList<Account> accounts) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // root elements
            Element rootElement = doc.createElement("accounts");
            doc.appendChild(rootElement);

            Element account;
            Element accountId; Element lastAssignedAccountId; Element balance;
            Element pNo; Element accountType;

            int count = 1;

            for (Account a : accounts) {

                // account elements
                account = doc.createElement("account");
                rootElement.appendChild(account);
                // set attribute to customer element
                account.setAttribute("id", "" + count++);

                // accountId elements
                accountId = doc.createElement("accountId");
                accountId.appendChild(doc.createTextNode(""+a.getAccountID()));
                account.appendChild(accountId);

                // balance elements
                balance = doc.createElement("balance");
                balance.appendChild(doc.createTextNode(""+a.getBalance()));
                account.appendChild(balance);

                // pNo elements
                pNo = doc.createElement("pNo");
                pNo.appendChild(doc.createTextNode(a.getpNo()));
                account.appendChild(pNo);

                // accountType elements
                accountType = doc.createElement("accountType");
                accountType.appendChild(doc.createTextNode(a.getAccountType()));
                account.appendChild(accountType);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //formatting style of the file
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(".//src/matwal6/fileAccount.xml"));

            // Output to console for testing
            //StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        return true;
    }

    /*
    Creating xml file for current transactions. Saves in "fileTrans.xml"
     */
    public boolean createTransactionXml(ArrayList<Transaction> transactions) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // root elements
            Element rootElement = doc.createElement("transactions");
            doc.appendChild(rootElement);

            Element transaction;
            Element pNo; Element accountId; Element timeStamp;
            Element amount; Element balance;

            int count = 1;

            for (Transaction t: transactions) {

                // transaction elements
                transaction = doc.createElement("transaction");
                rootElement.appendChild(transaction);
                // set attribute to customer element
                transaction.setAttribute("id", "" + count++);

                // pNo elements
                pNo = doc.createElement("pNo");
                pNo.appendChild(doc.createTextNode(t.getpNo()));
                transaction.appendChild(pNo);

                // accountId elements
                accountId = doc.createElement("accountId");
                accountId.appendChild(doc.createTextNode(""+t.getAccountId()));
                transaction.appendChild(accountId);

                // timeStamp elements
                timeStamp = doc.createElement("timeStamp");
                timeStamp.appendChild(doc.createTextNode(t.getTimeStamp()));
                transaction.appendChild(timeStamp);

                // amount elements
                amount = doc.createElement("amount");
                amount.appendChild(doc.createTextNode(""+t.getAmount()));
                transaction.appendChild(amount);

                // balance elements
                balance = doc.createElement("balance");
                balance.appendChild(doc.createTextNode(""+t.getBalance()));
                transaction.appendChild(balance);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //formatting style of the file
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(".//src/matwal6/fileTrans.xml"));

            // Output to console for testing
            //StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        return true;
    }

    /*
    Creating xml file for current customers. Saves in "fileCustomers.xml"
     */
    public boolean createCustomerXml(ArrayList<Customer> customers) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // root elements
            Element rootElement = doc.createElement("customers");
            doc.appendChild(rootElement);

            Element customer;
            Element name; Element surname; Element pNo;
            int count = 1;

            for (Customer c: customers) {

                // customer elements
                customer = doc.createElement("customer");
                rootElement.appendChild(customer);
                // set attribute to customer element
                customer.setAttribute("id", "" + count++);

                // name elements
                name = doc.createElement("name");
                name.appendChild(doc.createTextNode(c.getName()));
                customer.appendChild(name);

                // surname elements
                surname = doc.createElement("surname");
                surname.appendChild(doc.createTextNode(c.getSurname()));
                customer.appendChild(surname);

                // pNo elements
                pNo = doc.createElement("pNo");
                pNo.appendChild(doc.createTextNode(c.getpNo()));
                customer.appendChild(pNo);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //formatting style of the file
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(".//src/matwal6/fileCustomers.xml"));

            // Output to console for testing
            //StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        return true;
    }

}

