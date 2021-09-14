package services.action;

import services.Action;
import services.Database;
import services.MailServer;

public class SendEmail implements Action {
    private String userEmail;
    private String message;

    public SendEmail(String userEmail, String message) {
        this.userEmail = userEmail;
        this.message = message;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        mailServer.sendEmail(userEmail, message);
    }
}
