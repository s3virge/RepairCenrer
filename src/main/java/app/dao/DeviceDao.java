package app.dao;

import app.dao.handbooks.device.*;
import app.models.Device;
import app.models.DeviceStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Vector;

//Data Access Object for device
public class DeviceDao {

    private static final Logger log = LogManager.getLogger(DeviceDao.class);

    private static final String tableName = "device";
    private Device device;

    public DeviceDao(Device device) {
        this.device = device;
    }

    /**
     * insert to database information about owner
     */
    public int insert() {
        log.trace("");

        int id = 0;
        int type_id = selectTypeId();
        int brand_id = selectBrandId();
        int model_id = selectModelId();
        String serialNumber = device.getSerialNumber();
        int defect_id = selectDefectId();
        int owner_id = device.getOwnerId();
        int repair_id = device.getRepairId();
        int completeness_id = selectCompletenessId();
        int appearance_id = selectAppearanceId();
        String note = device.getNote();

        String sql = "insert into " + tableName +
                "(type_id, brand_id, model_id, serial_number, defect_id," +
                " owner_id, repair_id, completeness_id, appearance_id, note) " +
                "values(?,?,?,?,?,?,?,?,?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
             PreparedStatement stmt = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, type_id);
            stmt.setInt(2, brand_id);
            stmt.setInt(3, model_id);
            stmt.setString(4, serialNumber);
            stmt.setInt(5, defect_id);
            stmt.setInt(6, owner_id);
            stmt.setInt(7, repair_id);
            stmt.setInt(8, completeness_id);
            stmt.setInt(9, appearance_id);
            stmt.setString(10, note);

            stmt.execute();

            //Retrieves any auto-generated keys created as a result of executing this Statement object.
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }

        return id;
    }

    private int selectModelId() {
        String devModel = device.getModel();
        ModelDao modelDao = new ModelDao();
        int model_id = modelDao.selectId(devModel);

        if (model_id == 0) {
            model_id = modelDao.insert(devModel);
        }
        return model_id;
    }

    private int selectBrandId() {
        String devBrand = device.getBrand();
        BrandDao brandDao = new BrandDao();
        int brand_id = brandDao.selectId(devBrand);

        if (brand_id == 0) {
            brand_id = brandDao.insert(devBrand);
        }
        return brand_id;
    }

    private int selectTypeId() {
        String devType = device.getType();
        TypeDao typeDao = new TypeDao();
        int type_id = typeDao.selectId(devType);

        if (type_id == 0) {
            type_id = typeDao.insert(devType);
        }
        return type_id;
    }

    private int selectDefectId() {
        String defect = device.getDefect();
        DefectDao defectDao = new DefectDao();
        int defect_id = defectDao.selectId(defect);

        if (defect_id == 0) {
            defect_id = defectDao.insert(defect);
        }
        return defect_id;
    }

    private int selectCompletenessId() {
        String completeness = device.getCompleteness();
        CompletenessDao completenessDao = new CompletenessDao();
        int completness_id = completenessDao.selectId(completeness);

        if (completness_id == 0) {
            completness_id = completenessDao.insert(completeness);
        }
        return completness_id;
    }

    private int selectAppearanceId() {
        String appearance = device.getAppearance();
        AppearanceDao appearanceDao = new AppearanceDao();
        int appearance_id = appearanceDao.selectId(appearance);

        if (appearance_id == 0) {
            appearance_id = appearanceDao.insert(appearance);
        }
        return appearance_id;
    }

    public static int selectMaxId() {
        int maxId = 0;

        String sql = "Select max(id) as id from " + tableName;

        try (Connection conn = ConnectionBuilder.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            rs.next();

            maxId = rs.getInt("id");
        }
        catch (SQLException e) {
            log.error(e.getMessage());
        }
        return maxId;
    }

    /**
     * @return list of devises with given status
     */
    public static Vector<Device> selectByStatus(String status) {
        log.trace("");

        //todo create select query to select all devices with device status принято
        final String SELECT_DEVICES = "select device.id, t.value, b.value, m.value, device.serial_number, " +
                "d.value, device.owner_id, device.repair_id, c.value, a.value, device.note " +
                "from device " +
                "inner join type t on device.type_id = t.id " +
                "inner join brand b on device.brand_id = b.id " +
                "inner join model m on device.model_id = m.id " +
                "inner join defect d on device.defect_id = d.id " +
                "inner join completeness c on device.completeness_id = c.id " +
                "inner join appearance a on device.appearance_id = a.id";

        Vector<Device> listOfDevices = new Vector<>();

        try (Connection co = ConnectionBuilder.getConnection();
             Statement st = co.createStatement()) {
            ResultSet result = st.executeQuery(SELECT_DEVICES);
            while (result.next()) {
                Device device = new Device();

                device.setId(result.getInt("id"));
                device.setType(result.getString("value"));
                device.setBrand(result.getString("value"));
                device.setModel(result.getString("value"));
                device.setSerialNumber(result.getString("serial_number"));
                device.setDefect(result.getString("value"));
                device.setOwnerId(result.getInt("owner_id"));
                device.setRepairId(result.getInt("repair_id"));
                device.setCompleteness(result.getString("value"));
                device.setAppearance(result.getString("value"));
                device.setNote(result.getString("note"));

                listOfDevices.add(device);
            }
        }
        catch (SQLException sex) {
            log.error(sex.getMessage());
        }

        return listOfDevices;
    }
}
