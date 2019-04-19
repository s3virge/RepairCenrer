package app.dao.handbooks.repair;

import app.dao.ConnectionBuilder;
import app.models.Repair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RepairDao {

    private static final Logger log = LogManager.getLogger(RepairDao.class);

    private static final String tableName = "repair";
    private Repair repair;

    public RepairDao(Repair repair) {
        this.repair = repair;
    }

    /**
     * insert information about repair to database
     * @return id of the inserted row
     */
    public int insert() {
        log.trace("");

        int id = 0;
        int acceptor_id = repair.getAcceptorId();
        int master_id = repair.getMasterId();
        String master_comments;
        String diagnostic_result;
        String repair_result;
        int status_id = repair.getStatusId();

        //Get current date time
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatDate = now.format(formatter);

        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatTime = now.format(formatter);

        String date_of_receipt = formatDate;
        String time_of_receipt = formatTime;

//        log.debug("date of receipt: {} time of receipt: {}", date_of_receipt, time_of_receipt);

        String sql = "insert into " + tableName + "(acceptor_id, master_id, status_id, " +
                "date_of_receipt, time_of_receipt)" +
                " values(?,?,?," +
                "?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
             PreparedStatement stmt = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, acceptor_id);
            stmt.setInt(2, master_id);
            stmt.setInt(3, status_id);
            stmt.setString(4, date_of_receipt);
            stmt.setString(5, time_of_receipt);
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

    public static void updateMasterComments(int repairId, String masterComments) {
               /*
		UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;
		*/

        final String update_master_comments = String.format("update %s " +
                        "set master_comments = '%s'" +
                        "where id = %d",
                tableName, masterComments, repairId);

        try (Connection con = ConnectionBuilder.getConnection();
             Statement st = con.createStatement()) {
            st.execute(update_master_comments);
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void updateDiagnosticResult(int repairId, String diagnosticResult) {
        log.trace("");

        final String update_diagnostic_result = String.format("update %s " +
                        "set diagnostic_result = '%s'" +
                        "where id = %d",
                tableName, diagnosticResult, repairId);

        try (Connection con = ConnectionBuilder.getConnection();
             Statement st = con.createStatement()) {
            st.execute(update_diagnostic_result);
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    static public void updateRepairResult(int repairId, String repairResult) {
        log.trace("");

        final String update_repair_result = String.format("update %s " +
                        "set repair_result = '%s'" +
                        "where id = %d",
                tableName, repairResult, repairId);

        try (Connection con = ConnectionBuilder.getConnection();
             Statement st = con.createStatement()) {
            st.execute(update_repair_result);
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * update device status (состояние)*/
    public static void updateDeviceStatus(int repairId, String repairStatus) {
        log.trace("");

        final String update_status = String.format("update %s " +
                        "set status_id = '%d' " +
                        "where id = %d",
                tableName, new StatusDao().selectId(repairStatus), repairId);

        try (Connection con = ConnectionBuilder.getConnection();
             Statement st = con.createStatement()) {
            st.execute(update_status);
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    };
}
