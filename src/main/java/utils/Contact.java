package utils;

public class Contact {
    private String firstName;
    private String lastName;
    private String link;

    public Contact(String firstName, String lastName, String link) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.link = link;
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
}
