package app.dao.handbooks.repair;

import app.dao.CommonDao;

public class StatusDao extends CommonDao {
    private static final String tableName = "status";

    public StatusDao() {
        super(tableName);
    }
}
