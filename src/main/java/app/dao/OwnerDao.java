package app.dao;


import app.dao.handbooks.owner.NameDao;
import app.dao.handbooks.owner.PatronymicDao;
import app.dao.handbooks.owner.SurnameDao;
import app.models.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static app.dao.DataBase.logger;

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
        SurnameDao surnameDao = new SurnameDao();

        int surname_id = surnameDao.getId(surname);

        if (surname_id == 0) {
            surname_id = surnameDao.save(surname);
        }

        String name = owner.getName();
        NameDao nameDao = new NameDao();

        int name_id = nameDao.getId(name);

        if (name_id == 0) {
            name_id = nameDao.save(name);
        }

        String patronymic = owner.getPatronymic();
        PatronymicDao patronymicDao = new PatronymicDao();

        int patronymic_id = patronymicDao.getId(patronymic);

        if (patronymic_id == 0) {
            patronymic_id = patronymicDao.save(patronymic);
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
            log.error(ex.getMessage());
        }

        owner.setId(getId());
    }

    /**
     * @return owner id. If owner does not exist return 0
     * id of the owner in database by phone number or 0 if owner does not exist
     */
    public int getId() {
        logger.trace("");

        int surname_id = new SurnameDao().getId(owner.getSurname());
        int name_id     = new NameDao().getId(owner.getName());
        int patronymic_id = new PatronymicDao().getId(owner.getPatronymic());
        String phone_number = owner.getPhoneNumber();

        String sql = "select id from " + tableName + " where surname_id = ? " +
                "and name_id = ? " +
                "and patronymic_id = ? " +
                "and phone_number = ?";

        int id = 0;

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, surname_id);
            stmt.setInt(2, name_id);
            stmt.setInt(3, patronymic_id);
            stmt.setString(4, phone_number);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return id;
    }
}
