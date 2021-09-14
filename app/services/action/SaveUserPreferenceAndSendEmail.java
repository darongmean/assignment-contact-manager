package services.action;

import models.User;
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
        User user = database.getUserByUserEmail().get(userEmail);
        user.setHourBeforeSendBirthdayEmail(newHourBeforeSendBirthdayEmail);
        database.getUserByUserEmail().put(userEmail, user);
        mailServer.sendEmail(userEmail, message);
    }
}
