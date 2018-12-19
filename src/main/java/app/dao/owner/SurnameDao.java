package app.dao.owner;

public class SurnameDao {
    private int id;
    private String surname;

    //todo сформировать запросы к базе данных
    private static final String INSERT_SURNAME = "insert * from surname";
    private static final String SELECT_SURNAME = "select * from surname";

    /**
     * return id for surname in database
     * @param surname
     * @return id
     */
    public int getId(String surname) {
        return id;
    }

    /**
     * save surname to database
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
