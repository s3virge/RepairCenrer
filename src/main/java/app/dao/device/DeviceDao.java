package app.dao.device;

import app.dao.ConnectionBuilder;
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

        String devType = device.getType();
        int type_id  = TypeDao.getId(devType);

        if (type_id == 0) {
            type_id = TypeDao.save(devType);
        }

        String devBrand = device.getBrand();
        int brand_id     = BrandDao.getId(devBrand);

        if (brand_id == 0) {
            brand_id = BrandDao.save(devBrand);
        }

        String devModel = device.getModel();
        int model_id = ModelDao.getId(devModel);

        if (model_id == 0) {
            model_id = ModelDao.save(devModel);
        }

        String serialNumber = device.getSerialNumber();

        //todo add остальые values
        String sql = "insert into " + tableName + " (type_id, brand_id, model_id, serial_number) values(?,?,?,?)";
it
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
