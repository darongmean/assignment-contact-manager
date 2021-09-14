package services.action;

import services.Action;
import services.Database;
import services.MailServer;

public class DeleteContact implements Action {
    private String userEmail;
    private String contactId;

    public DeleteContact(String userEmail, String contactId) {
        this.userEmail = userEmail;
        this.contactId = contactId;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        database.deleteContact(userEmail, contactId);
    }
}
