package app.dao.device;

import app.dao.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelDao {
    private static final Logger logger = LogManager.getLogger(ModelDao.class);

    private static final String table = "model";
    private static final String INSERT = "insert into " + table + " (value) value (?)";
    private static final String SELECT = "select id from " + table + " where value = ?";

    /**
     * return id for model in database or 0 if model does not exist
     * @param model
     * @return model id. If model does not exist return 0
     */
    public static int getId(String model) {
        logger.trace("");

        int id = 0;

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT)){
            stmt.setString(1, model);
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
     * save model to database
     * @param model
     */
    public static int save(String model) {
        logger.trace("");

        int result;

        try (Connection con = ConnectionBuilder.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT)) {

            con.setAutoCommit(false);

            try {
                stmt.setString(1, model);
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

        return result = getId(model);
    }
}
