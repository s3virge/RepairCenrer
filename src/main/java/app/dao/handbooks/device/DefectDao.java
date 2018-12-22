package app.dao.handbooks.device;

import app.dao.CommonDao;

public class DefectDao extends CommonDao {

    private static final String tableName = "defect";

    public DefectDao() {
        super(tableName);
    }
}
