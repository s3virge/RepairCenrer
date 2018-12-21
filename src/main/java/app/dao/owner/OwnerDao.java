package app.dao.owner;


import app.dao.ConnectionBuilder;
import app.models.Owner;
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

    private static final String tableName = "owner";
    private Owner owner;

    public OwnerDao(Owner owner) {
        this.owner = owner;
    }

    /**
     * save to database information about owner
     */
    public void save() {
        log.trace("");

        String surname = owner.getSurname();
        int surname_id  = SurnameDao.getId(surname);

        if (surname_id == 0) {
            surname_id = SurnameDao.save(surname);
        }

        String name = owner.getName();
        int name_id     = NameDao.getId(name);

        if (name_id == 0) {
            name_id = NameDao.save(name);
        }

        String patronymic = owner.getPatronymic();
        int patronymic_id = PatronymicDao.getId(patronymic);

        if (patronymic_id == 0) {
            patronymic_id = PatronymicDao.save(patronymic);
        }

        String phoneNumber = owner.getPhoneNumber();

        String sql = "insert into " + tableName + "(surname_id, name_id, patronymic_id, phone_number) values(?,?,?,?)";

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
