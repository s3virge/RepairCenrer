package app.dao.handbooks.device;

import app.dao.CommonDao;

public class CompletenessDao extends CommonDao {
    private static final String tableName = "completeness";

    public CompletenessDao() {
        super(tableName);
    }
}
