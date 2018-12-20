package app.dao.owner;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class OwnerDao {

    private static final Logger log = LogManager.getLogger(OwnerDao.class);

    private String surname;
    private String name;
    private String patronymic;
    private String telephoneNumber;

    public OwnerDao(String surname, String name, String patronymic, String telephoneNumber) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.telephoneNumber = telephoneNumber;
    }

    public OwnerDao() {
        this.surname = "";
        this.name = "";
        this.patronymic = "";
        this.telephoneNumber = "";
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return telephoneNumber;
    }

    public void setPhoneNumber(String telephone_number) {
        this.telephoneNumber = telephone_number;
    }

    /**
     * save to database information about owner
     */
    public void save() {
        log.trace("");
        int surname_id  = SurnameDao.getId(surname);

        if (surname_id == 0) {
            surname_id = SurnameDao.save(surname);
        }

        int name_id     = NameDao.getId(name);

        if (name_id == 0) {
            name_id = NameDao.save(name);
        }

        int patronymic_id = PatronymicDao.getId(patronymic);

        if (patronymic_id == 0) {
            patronymic_id = PatronymicDao.save(patronymic);
        }

        //todo make record to database
    }
}
