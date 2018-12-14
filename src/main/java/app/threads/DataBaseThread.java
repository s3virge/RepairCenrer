package app.threads;

import app.dao.DataBase;


public class DataBaseThread implements Runnable {
    private Thread thread;

    /**
     * check is database exist
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
        db.isExist();
    }
}
