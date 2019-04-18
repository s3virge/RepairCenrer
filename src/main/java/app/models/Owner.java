package app.models;

/**
 * Owner - device owner information
 */
public class Owner {

    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private String email;

    public Owner() {
    }

    public Owner(int id, String surname, String name, String patronymic, String phoneNumber, String email) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        String str = surname + " " + name + " " +  patronymic + " " +  phoneNumber + " " +  email;
        return str;
    }
}
