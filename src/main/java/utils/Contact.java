package utils;

public class Contact {
    private String firstName;
    private String lastName;
    private String link;
    private String company;

    public Contact(String firstName, String lastName, String link, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.link = link;
        this.company=company;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLink() {
        return link;
    }
    public String getCompany(){
        return company;
    }
}
