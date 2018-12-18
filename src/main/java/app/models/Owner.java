package app.models;

/**
 * Owner - device owner information
 */
public class Owner {
    private String surname;
    private String name;
    private String patronymic;
    private String telephone_number;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public void print() {
        System.out.printf("%-15s - %s\n", "surname", surname);
        System.out.printf("%-15s - %s\n", "name", name);
        System.out.printf("%-15s - %s\n", "patronymic", patronymic);
        System.out.printf("%-15s - %s\n", "telephone_number", telephone_number);
    }
}
