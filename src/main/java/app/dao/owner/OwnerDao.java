package app.dao.owner;

/**
 *
 */
public class OwnerDao {
    private int id;
    private int surname_id;
    private int name_id;
    private int patronymic_id;

    private String surname;
    private String name;
    private String patronymic;
    private String telephoneNumber;

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephone_number) {
        this.telephoneNumber = telephone_number;
    }

    /**
     * save to database information about owner
     */
    public void save() {

    }
}
