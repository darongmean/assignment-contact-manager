package services;

import models.Contact;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EditContact implements Action {
    private String userEmail;
    private String contactId;
    private String name;
    private LocalDate birthday;

    public EditContact(String userEmail, String contactId, String name, LocalDate birthday) {
        this.userEmail = userEmail;
        this.contactId = contactId;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        List<Contact> contacts = database.getContactByUserEmail().getOrDefault(userEmail, new ArrayList<>());
        for (Contact contact : contacts) {
            if (contact.getId().equalsIgnoreCase(contactId)) {
                contact.setName(name);
                contact.setBirthday(birthday);
            }
        }
    }
}
