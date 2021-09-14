package services;

import models.User;
import services.action.AddContact;
import services.action.DeleteContact;
import services.action.NoAction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ManageContact {
    public Action addContact(User me, String name, String birthday) {
        try {
            return new AddContact(me.getEmail(), name, LocalDate.parse(birthday));
        } catch (DateTimeParseException ex) {
            return new NoAction();
        }
    }

    public Action editContact(User me, String contactId, String name, String birthday) {
        try {
            return new EditContact(me.getEmail(), contactId, name, LocalDate.parse(birthday));
        } catch (DateTimeParseException ex) {
            return new NoAction();
        }
    }

    public Action deleteContact(User me, String contactId) {
        return new DeleteContact(me.getEmail(), contactId);
    }
}
