package matwal6;
/**
 * Inlämningsuppgift 4 - Fiktiv Bank med GUI samt Import och Export av data
 *
 * @author Mats Wallgren, matwal-6
 */


public class Customer {

    private String surname;
    private String name;
    private String pNo;

    //constructor
    public Customer(String name, String surname, String pNo) {
        this.surname = surname;
        this.name = name;
        this.pNo = pNo;
    }

    //getters and setters
    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getpNo() {
        return pNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


}
