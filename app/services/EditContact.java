package services;

import java.time.LocalDate;

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
        database.editContact(userEmail, contactId, name, birthday);
    }
}
