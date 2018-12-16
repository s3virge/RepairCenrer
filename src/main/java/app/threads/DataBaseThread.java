package app.threads;

import app.dao.DataBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseThread implements Runnable {

    public static final Logger logger = LogManager.getLogger(DataBaseThread.class);

    private Thread thread;

    /**
     * check if database exist
     * if database does not exist
     * then create here
     * @param name
     */
    public DataBaseThread(String name) {
        thread = new Thread(this, name);
        thread.start();
    }

    @Override
    public void run() {
        DataBase db = new DataBase();

        if (!db.isExists()) {
            db.create();
        }
    }
}
