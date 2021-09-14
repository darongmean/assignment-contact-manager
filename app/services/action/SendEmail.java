package services.action;

import services.Action;
import services.Database;
import services.MailServer;

public class SendEmail implements Action {
    private String toEmail;
    private String message;

    public SendEmail(String toEmail, String message) {
        this.toEmail = toEmail;
        this.message = message;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        mailServer.sendEmail(toEmail, message);
    }
}
