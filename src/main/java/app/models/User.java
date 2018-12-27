package app.models;

public class User {

    private int id;
    private String login;
    private String password;
    private String group;

    private String surname;
    private String name;
    private String patronymic;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return строку с названием группы пользователя
     */
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return
     * Возвращает true если пользователь пустой иначе false
     */
    public boolean isEmpty(){
        if (id == 0 && login == null && password == null){
            return true;
        }
        return false;
    }
}
