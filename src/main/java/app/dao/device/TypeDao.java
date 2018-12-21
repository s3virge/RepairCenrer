package app.dao.device;

import app.dao.ConnectionBuilder;
import app.dao.owner.NameDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeDao {
    private static final Logger logger = LogManager.getLogger(TypeDao.class);

    private static final String table = "type";
    private static final String INSERT_TYPE = "insert into " + table + " (value) value (?)";
    private static final String SELECT_TYPE = "select id from " + table + " where value = ?";

    /**
     * return id for deviceType in database or 0 if device type does not exist
     * @param deviceType
     * @return deviceType id. If deviceType does not exist return 0
     */
    public static int getId(String deviceType) {
        logger.trace("");

        int id = 0;

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_TYPE)){
            stmt.setString(1, deviceType);
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
     * save device type to database
     * @param deviceType
     */
    public static int save(String deviceType) {
        logger.trace("");

        int result;

        try (Connection con = ConnectionBuilder.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_TYPE)) {

            con.setAutoCommit(false);

            try {
                stmt.setString(1, deviceType);
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

        return result = getId(deviceType);
    }
}
