package app.dao.handbooks.owner;

import app.dao.CommonDao;

public class NameDao extends CommonDao {
    private static final String tableName = "names";

    public NameDao() {
        super(tableName);
    }
}
