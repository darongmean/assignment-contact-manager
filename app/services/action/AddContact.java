package services.action;

import models.Contact;
import services.Action;
import services.Database;
import services.MailServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddContact implements Action {
    private String userEmail;
    private String name;
    private LocalDate birthday;

    public AddContact(String userEmail, String name, LocalDate birthday) {

        this.userEmail = userEmail;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        List<Contact> contacts = database.getContactByUserEmail().getOrDefault(userEmail, new ArrayList<>());
        contacts.add(new Contact(name, birthday));
        database.getContactByUserEmail().put(userEmail, contacts);
    }
}
