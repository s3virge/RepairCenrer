package app.dao.device;

import app.dao.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandDao {
    private static final Logger logger = LogManager.getLogger(BrandDao.class);

    private static final String table = "brand";
    private static final String INSERT = "insert into " + table + " (value) value (?)";
    private static final String SELECT = "select id from " + table + " where value = ?";

    /**
     * return id for brand in database or 0 if brand does not exist
     * @param brand
     * @return brand id. If brand does not exist return 0
     */
    public static int getId(String brand) {
        logger.trace("");

        int id = 0;

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT)){
            stmt.setString(1, brand);
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
     * save brand to database
     * @param brand
     */
    public static int save(String brand) {
        logger.trace("");

        int result;

        try (Connection con = ConnectionBuilder.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT)) {

            con.setAutoCommit(false);

            try {
                stmt.setString(1, brand);
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

        return result = getId(brand);
    }
}
