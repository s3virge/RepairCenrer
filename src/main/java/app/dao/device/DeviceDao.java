package app.dao.device;

import app.dao.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Data Access Object for device
public class DeviceDao {

    //todo rewrite to using Device class
    private static final Logger log = LogManager.getLogger(DeviceDao.class);

    private static final String tableName = "device";
    private int type_id;
    private int brand_id;
    private int model_id;

    private String devType;
    private String devBrand;
    private String devModel;
    private String serialNumber;

    public void setType(String devType) {
        this.devType = devType;
    }

    public void setBrand(String devBrand) {
        this.devBrand = devBrand;
    }

    public void setModel(String devModel) {
        this.devModel = devModel;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DeviceDao(String devType, String devBrand, String devModel, String serialNumber) {
        this.devType = devType;
        this.devBrand = devBrand;
        this.devModel = devModel;
        this.serialNumber = serialNumber;
    }

    public DeviceDao() {
        devType = "";
        devBrand = "";
        devModel = "";
        serialNumber = "";
    }

    /**
     * save to database information about owner
     */
    public void save() {
        log.trace("");

        int type_id  = TypeDao.getId(devType);

        if (type_id == 0) {
            type_id = TypeDao.save(devType);
        }

        int brand_id     = BrandDao.getId(devBrand);

        if (brand_id == 0) {
            brand_id = BrandDao.save(devBrand);
        }

        int model_id = ModelDao.getId(devModel);

        if (model_id == 0) {
            model_id = ModelDao.save(devModel);
        }

        String sql = "insert into " + tableName + " owner(type_id, brand_id, model_id, serial_number) values(?,?,?,?)";

        try (Connection co = ConnectionBuilder.getConnection();
             PreparedStatement stmt = co.prepareStatement(sql)) {
            stmt.setInt(1, type_id);
            stmt.setInt(2, brand_id);
            stmt.setInt(3, model_id);
            stmt.setString(4, serialNumber);
            stmt.execute();
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }


}
