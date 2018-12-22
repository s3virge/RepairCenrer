package app.dao.handbooks.owner;


import app.dao.CommonDao;

public class PatronymicDao extends CommonDao {
    private static final String tableName = "patronymics";

    public PatronymicDao() {
        super(tableName);
    }
}
