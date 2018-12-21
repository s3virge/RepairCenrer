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

    public Owner(String surname, String name, String patronymic, String phoneNumber) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }

    public Owner(){
        this.surname = "";
        this.name = "";
        this.patronymic = "";
        this.phoneNumber = "";
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

    public void print() {
        System.out.printf("%-15s - %s\n", "surname", surname);
        System.out.printf("%-15s - %s\n", "name", name);
        System.out.printf("%-15s - %s\n", "patronymic", patronymic);
        System.out.printf("%-15s - %s\n", "phoneNumber", phoneNumber);
    }
}
