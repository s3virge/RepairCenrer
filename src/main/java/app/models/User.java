package app.models;

import javafx.beans.property.SimpleStringProperty;

public class User {

    private int id;
    private final SimpleStringProperty login;
    private final SimpleStringProperty password;
    private final SimpleStringProperty group;

    private final SimpleStringProperty surname;
    private final SimpleStringProperty name;
    private final SimpleStringProperty patronymic;

    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public User(String login, String password, String group,
                String surname, String name, String patronymic, String email, String phoneNumber) {
        this.id = 0;
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.group = new SimpleStringProperty(group);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
    }

    public User() {
        this.id = 0;
        this.login = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.group = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.patronymic = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getGroup() {
        return group.get();
    }

    public SimpleStringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public SimpleStringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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
