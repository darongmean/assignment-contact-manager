package models;

import java.time.LocalDate;

public class Contact {
    String userEmail;
    String name;
    LocalDate birthday;

    public Contact(String userEmail, String name, LocalDate birthday) {
        this.userEmail = userEmail;
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
