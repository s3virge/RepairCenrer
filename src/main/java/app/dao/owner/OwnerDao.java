package app.dao.owner;


import app.dao.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class OwnerDao {

    private static final Logger log = LogManager.getLogger(OwnerDao.class);

    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;

    public OwnerDao(String surname, String name, String patronymic, String phoneNumber) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }

    public OwnerDao() {
        this.surname = "";
        this.name = "";
        this.patronymic = "";
        this.phoneNumber = "";
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
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
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

        String sql = "insert into owner(surname_id, name_id, patronymic_id, phone_number) values(?,?,?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
            PreparedStatement stmt = co.prepareStatement(sql)) {
            stmt.setInt(1, surname_id);
            stmt.setInt(2, name_id);
            stmt.setInt(3, patronymic_id);
            stmt.setString(4, phoneNumber);
            stmt.execute();
        }
        catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
