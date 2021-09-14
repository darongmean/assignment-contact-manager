package models;

public class User {
    private final String email;
    private final String password;
    private int hourBeforeSendBirthdayEmail;

    public User(String email, String password, int hourBeforeSendBirthdayEmail) {
        this.email = email;
        this.password = password;
        this.hourBeforeSendBirthdayEmail = hourBeforeSendBirthdayEmail;
    }

    public String getEmail() {
        return email;
    }

    public int getHourBeforeSendBirthdayEmail() {
        return hourBeforeSendBirthdayEmail;
    }

    public void setHourBeforeSendBirthdayEmail(int hourBeforeSendBirthdayEmail) {
        this.hourBeforeSendBirthdayEmail = hourBeforeSendBirthdayEmail;
    }

    public String getPassword() {
        return password;
    }
}
