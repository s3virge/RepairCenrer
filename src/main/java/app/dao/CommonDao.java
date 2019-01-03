package app.dao;

import app.utils.AutoSuggestTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public abstract class CommonDao {

    private static final Logger logger = LogManager.getLogger(CommonDao.class);

    private String tableName;
    private String INSERT;
    private String SELECT;

    public CommonDao(String tableName) {
        this.tableName = tableName;
        INSERT = "insert into " + tableName + "(value) value(?)";
        SELECT = "select id from " + tableName + " where value = ?";
    }

    /**
     * return id for name in database or 0 if name does not exist
     * @param selectValue
     * @return selectValue id. If name does not exist return 0
     */
    public int selectId(String selectValue) {
//        logger.trace("");

        int id = 0;

        try (Connection conn = ConnectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT)){

            stmt.setString(1, selectValue);

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
     * insert name to database
     * @param insertValue
     */
    public int insert(String insertValue) {
//        logger.trace("");

        try (Connection con = ConnectionBuilder.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT)) {

            con.setAutoCommit(false);

            try {
                stmt.setString(1, insertValue);
                stmt.executeUpdate();
                con.commit();
            }
            catch (SQLException ex) {
                con.rollback();
//                throw ex;
            }
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return selectId(insertValue);
    }

    /**
     * get suggestions for auto suggest text fields
     * @param asTextField - AutoSuggestTextField object
     */
    public void selectEntries(AutoSuggestTextField asTextField) {

        ArrayList<String> alEntries = new ArrayList<>();

        try (Connection conn = ConnectionBuilder.getConnection();
            Statement stat = conn.createStatement()) {

            ResultSet resultSet = stat.executeQuery("SELECT * FROM " + tableName);

            while (resultSet.next()) {
                alEntries.add(resultSet.getString("value"));
            }
        }
        catch (SQLException sqlEx) {
            logger.error(sqlEx.getMessage());
        }

        //заполнить выпадающий список подсказок
        asTextField.getEntries().addAll(alEntries);
    }
}
