package models;

import java.time.LocalDate;
import java.util.UUID;

public class Contact {
    String id;
    String name;
    LocalDate birthday;

    public Contact(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
