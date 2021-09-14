package services.action;

import services.Action;
import services.Database;
import services.MailServer;

public class SaveUserPreferenceAndSendEmail implements Action {
    private String userEmail;
    private int newHourBeforeSendBirthdayEmail;
    private String message;

    public SaveUserPreferenceAndSendEmail(String userEmail, int newHourBeforeSendBirthdayEmail, String message) {
        this.userEmail = userEmail;
        this.newHourBeforeSendBirthdayEmail = newHourBeforeSendBirthdayEmail;
        this.message = message;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        database.updateUserPreference(userEmail, newHourBeforeSendBirthdayEmail);
        mailServer.sendEmail(userEmail, message);
    }
}
