package app.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CommonDao {

    private static final Logger logger = LogManager.getLogger(CommonDao.class);

    private String tableName;
    private String INSERT = "insert into ?(value) value(?)";
    private String SELECT = "select id from ? where value = ?";

    public CommonDao(String tableName) {
        this.tableName = tableName;
    }

    /**
     * return id for name in database or 0 if name does not exist
     * @param selectValue
     * @return selectValue id. If name does not exist return 0
     */
    public int getId(String selectValue) {
        logger.trace("");

        int id = 0;

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT)){

            stmt.setString(1, tableName);
            stmt.setString(2, selectValue);

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
     * @param insertValue
     */
    public int save(String insertValue) {
        logger.trace("");

        try (Connection con = ConnectionBuilder.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT)) {

            con.setAutoCommit(false);

            try {
                stmt.setString(1, tableName);
                stmt.setString(2, insertValue);
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

        return getId(insertValue);
    }
}
