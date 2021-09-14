package models;

public class User {
    String email;
    int hourBeforeSendBirthdayEmail;

    public User(String email, int hourBeforeSendBirthdayEmail) {
        this.email = email;
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
}
