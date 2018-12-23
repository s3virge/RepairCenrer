package app.dao;

import app.dao.handbooks.device.*;
import app.models.Device;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Data Access Object for device
public class DeviceDao {

    private static final Logger log = LogManager.getLogger(DeviceDao.class);

    private static final String tableName = "device";
    private Device device;

    public DeviceDao(Device device) {
        this.device = device;
    }

    /**
     * save to database information about owner
     */
    public void save() {
        log.trace("");

        int type_id     = getTypeId();
        int brand_id    = getBrandId();
        int model_id    = getModelId();
        String serialNumber = device.getSerialNumber();
        int defect_id   = getDefectId();
        int owner_id    = device.getOwnerId();
        int repair_id   = device.getRepairId();
        int completeness_id = getComgletenessId();
        int appearance_id   = getAppearanceId();

        //todo add остальые values
        String sql = "insert into " + tableName +
                "(type_id, brand_id, model_id, serial_number, defect_id," +
                " owner_id, repair_id, completeness_id, appearance_id) " +
                "values(?,?,?,?,?,?,?,?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
             PreparedStatement stmt = co.prepareStatement(sql)) {
            stmt.setInt(1, type_id);
            stmt.setInt(2, brand_id);
            stmt.setInt(3, model_id);
            stmt.setString(4, serialNumber);
            stmt.setInt(5, defect_id);
            stmt.setInt(6, owner_id);
            stmt.setInt(7, repair_id);
            stmt.setInt(8, completeness_id);
            stmt.setInt(9, appearance_id);

            stmt.execute();
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    private int getModelId() {
        String devModel = device.getModel();
        ModelDao modelDao = new ModelDao();
        int model_id = modelDao.getId(devModel);

        if (model_id == 0) {
            model_id = modelDao.save(devModel);
        }
        return model_id;
    }

    private int getBrandId() {
        String devBrand = device.getBrand();
        BrandDao brandDao = new BrandDao();
        int brand_id     = brandDao.getId(devBrand);

        if (brand_id == 0) {
            brand_id = brandDao.save(devBrand);
        }
        return brand_id;
    }

    private int getTypeId() {
        String devType = device.getType();
        TypeDao typeDao = new TypeDao();
        int type_id  = typeDao.getId(devType);

        if (type_id == 0) {
            type_id = typeDao.save(devType);
        }
        return type_id;
    }

    private int getDefectId() {
        String defect = device.getDefect();
        DefectDao defectDao = new DefectDao();
        int defect_id  = defectDao.getId(defect);

        if (defect_id == 0) {
            defect_id = defectDao.save(defect);
        }
        return defect_id;
    }

    private int getComgletenessId() {
        String completeness = device.getCompleteness();
        CompletenessDao completenessDao = new CompletenessDao();
        int completness_id  = completenessDao.getId(completeness);

        if (completness_id == 0) {
            completness_id = completenessDao.save(completeness);
        }
        return completness_id;
    }

    private int getAppearanceId() {
        String appearance = device.getAppearance();
        AppearanceDao appearanceDao = new AppearanceDao();
        int appearance_id  = appearanceDao.getId(appearance);

        if (appearance_id == 0) {
            appearance_id = appearanceDao.save(appearance);
        }
        return appearance_id;
    }

}
