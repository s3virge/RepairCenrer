package app.dao.handbooks.repair;

import app.dao.ConnectionBuilder;
import app.dao.handbooks.owner.NameDao;
import app.dao.handbooks.owner.PatronymicDao;
import app.dao.handbooks.owner.SurnameDao;
import app.models.Repair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static app.dao.DataBase.logger;

public class RepairDao {

    private static final Logger log = LogManager.getLogger(RepairDao.class);

    private static final String tableName = "repair";
    private Repair repair;

    public RepairDao(Repair repair) {
        this.repair = repair;
    }

    /**
     * save information about repair to database
     * @return id of the inserted row
     */
    public int save() {
        log.trace("");

        int id = 0;
        int acceptor_id = repair.getAcceptorId();
        int master_id = repair.getMasterId();
        String master_comments;
        String diagnostic_result;
        String repair_result;
        int status_id = repair.getStatusId();

        //todo лучше создать два отдельных столбца для даты и времени
        //тогда можно будет выполнять поиск по дате
        //Get current date time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = now.format(formatter);

        String date_of_accept = formatDateTime;

        String date_of_give_out ;

        String sql = "insert into " + tableName + "(acceptor_id, master_id, status_id, date_of_accept)" +
                " values(?,?,?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
             PreparedStatement stmt = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, acceptor_id);
            stmt.setInt(2, master_id);
            stmt.setInt(3, status_id);
            stmt.setString(4, date_of_accept);
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
}
