package services.action;

import models.Contact;
import services.Action;
import services.Database;
import services.MailServer;

import java.util.ArrayList;
import java.util.List;

public class DeleteContact implements Action {
    private String userEmail;
    private String contactId;

    public DeleteContact(String userEmail, String contactId) {
        this.userEmail = userEmail;
        this.contactId = contactId;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        List<Contact> contacts = database.getContactByUserEmail().getOrDefault(userEmail, new ArrayList<>());
        contacts.removeIf(contact -> contact.getId().equalsIgnoreCase(contactId));
    }
}
