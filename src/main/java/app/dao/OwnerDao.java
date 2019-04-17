package app.dao;


import app.dao.handbooks.owner.NameDao;
import app.dao.handbooks.owner.PatronymicDao;
import app.dao.handbooks.owner.SurnameDao;
import app.models.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.MessageFormat;

import static app.dao.DataBase.logger;

public class OwnerDao {

    private static final Logger log = LogManager.getLogger(OwnerDao.class);

    private static final String tableName = "owner";
    private Owner owner;

    public OwnerDao(Owner owner) {
        this.owner = owner;
    }

    /**
     * insert to database information about owner
     * @return id of created owner
     */
    public int insert() {
        log.trace("");

        int id = 0;

        String surname = owner.getSurname();
        SurnameDao surnameDao = new SurnameDao();

        int surname_id = surnameDao.selectId(surname);

        if (surname_id == 0) {
            surname_id = surnameDao.insert(surname);
        }

        String name = owner.getName();
        NameDao nameDao = new NameDao();

        int name_id = nameDao.selectId(name);

        if (name_id == 0) {
            name_id = nameDao.insert(name);
        }

        String patronymic = owner.getPatronymic();
        PatronymicDao patronymicDao = new PatronymicDao();

        int patronymic_id = patronymicDao.selectId(patronymic);

        if (patronymic_id == 0) {
            patronymic_id = patronymicDao.insert(patronymic);
        }

        String phoneNumber = owner.getPhoneNumber();

        String sql = "insert into " + tableName + "(surname_id, name_id, patronymic_id, phone_number) values(?,?,?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
             PreparedStatement stmt = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, surname_id);
            stmt.setInt(2, name_id);
            stmt.setInt(3, patronymic_id);
            stmt.setString(4, phoneNumber);
            stmt.execute();

            //Retrieves any auto-generated keys created as a result of executing this Statement object.
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                id = rs.getInt(1);
            }
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }

        return id;
    }

    /**
     * @return owner id. If owner does not exist return 0
     * id of the owner in database by phone number or 0 if owner does not exist
     */
    public int selectId() {
        logger.trace("");

        int surname_id = new SurnameDao().selectId(owner.getSurname());
        int name_id     = new NameDao().selectId(owner.getName());
        int patronymic_id = new PatronymicDao().selectId(owner.getPatronymic());
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

    /**
     * @return Owner object
     */
    static public Owner selectById(int id) {

//        final String query = "select surnames.value, names.value, patronymics.value, phone_number, email  " +
//        "from " + tableName + " " +
//        "inner join surnames on owner.surname_id = surnames.id  " +
//        "inner join names on owner.name_id = names.id " +
//        "inner join patronymics on owner.patronymic_id = patronymics.id " +
//        "where owner.id = '" + id + "'";

        final String query = MessageFormat.format("select surnames.value, names.value, patronymics.value, phone_number, email " +
                "from {0} " +
                        "inner join surnames on {0}.surname_id = surnames.id " +
                        "inner join names on {0}.name_id = names.id " +
                        "inner join patronymics on {0}.patronymic_id = patronymics.id " +
                        "where {0}.id = {1}", tableName, id);

        Owner owner = new Owner();

        try (Connection con = ConnectionBuilder.getConnection();
             Statement st = con.createStatement()) {

            ResultSet resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                owner.setSurname(resultSet.getString("surnames.value"));
                owner.setName(resultSet.getString("names.value"));
                owner.setPatronymic(resultSet.getString("patronymics.value"));
                owner.setPhoneNumber(resultSet.getString("phone_number"));
                owner.setEmail(resultSet.getString("email"));
            }
        }
        catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return owner;
    }
}
