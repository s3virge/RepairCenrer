package app.dao.owner;

import app.dao.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NameDao {
    private static final Logger logger = LogManager.getLogger(NameDao.class);

    private static final String INSERT_NAME = "insert into names (value) value (?)";
    private static final String SELECT_NAME = "select id from names where value = ?";

    /**
     * return id for name in database or 0 if name does not exist
     * @param name
     * @return name id. If name does not exist return 0
     */
    public static int getId(String name) {
        logger.trace("");

        int id = 0;

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_NAME)){
            stmt.setString(1, name);
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
     * save name to database
     * @param name
     */
    public static int save(String name) {
        logger.trace("");

        int result;

        try (Connection con = ConnectionBuilder.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_NAME)) {

            con.setAutoCommit(false);

            try {
                stmt.setString(1, name);
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

        return result = getId(name);
    }

//    public static void main(String[] args) {
//
//        String val = "DUpl";
//        SurnameDao sn = new SurnameDao();
//
//        Long result = sn.save(val);
//        System.out.println(result);
//
//        System.out.println(val + " id = " + sn.getId(val));
//
//        val = "Petia";
//        System.out.println(val + " id = " + sn.getId(val));
//    }
}
