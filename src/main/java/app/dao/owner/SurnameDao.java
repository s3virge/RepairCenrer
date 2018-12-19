package app.dao.owner;

import app.dao.ConnectionBuilder;
import app.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SurnameDao {

    private static final Logger logger = LogManager.getLogger(SurnameDao.class);

    private int id;
    private String surname;

    //todo сформировать запросы к базе данных
    private static final String INSERT_SURNAME = "insert into surname(value) value (?)";
    private static final String SELECT_SURNAME = "select * from surname";

    /**
     * return id for surname in database
     * @param surname
     * @return id
     */
    public int getId(String surname) {
//        List<Street> result = new LinkedList<>();
//
//        try (Connection con = getConnection();
//             PreparedStatement stmt = con.prepareStatement(GET_STREET)) {
//
//            stmt.setString(1, "%" + pattern + "%");
//            ResultSet rs = stmt.executeQuery();

//            while (rs.next()) {
//                Street str = new Street(rs.getLong("street_code"),
//                        rs.getString("street_name"));
//                result.add(str);
//            }
//        }
//        catch (SQLException ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new DaoException(ex);
//        }

        return id;
    }

    /**
     * save surname to database
     * @param surname
     */
    public Long saveSurname(String surname) {

        Long result = -1L;

        try (Connection con = ConnectionBuilder.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_SURNAME)) {

            con.setAutoCommit(false);

            try {
                stmt.setString(1, surname);
                stmt.executeUpdate();
                con.commit();
            }
            catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return result;
    }

//    public static void main(String[] args) {
//        SurnameDao sn = new SurnameDao();
//        Long result = sn.saveSurname("Putya");
//        System.out.println(result);
//    }
}