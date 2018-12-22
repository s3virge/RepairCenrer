package app.dao.handbooks.owner;

import app.dao.CommonDao;

public class SurnameDao extends CommonDao {
    private static final String tableName = "surnames";

    public SurnameDao() {
        super(tableName);
    }
}