package app.dao.handbooks.repair;

import app.dao.ConnectionBuilder;
import app.models.Repair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RepairDao {

    private static final Logger log = LogManager.getLogger(RepairDao.class);

    private static final String tableName = "repair";
    private Repair repair;

    public RepairDao(Repair repair) {
        this.repair = repair;
    }

    public void save() {
        log.trace("");

        int acceptor_id = repair.getAcceptorId();
        int master_id = repair.getMasterId();
        String master_comments;
        String diagnostic_result;
        String repair_result;
        int status_id = repair.getStatusId();
        String date_of_accept = LocalDateTime.now().toString();
        String date_of_give_out ;

        String sql = "insert into " + tableName + "(acceptor_id, master_id, status_id, date_of_accept)" +
                " values(?,?,?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
             PreparedStatement stmt = co.prepareStatement(sql)) {
            stmt.setInt(1, acceptor_id);
            stmt.setInt(2, master_id);
            stmt.setInt(3, status_id);
            stmt.setString(4, date_of_accept);
            stmt.execute();
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
}
