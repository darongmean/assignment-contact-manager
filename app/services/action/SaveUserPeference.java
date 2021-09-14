package services.action;

import models.User;
import services.Action;
import services.Database;
import services.MailServer;

public class SaveUserPeference implements Action {
    private String userEmail;
    private int newHourBeforeSendBirthdayEmail;

    public SaveUserPeference(String userEmail, int newHourBeforeSendBirthdayEmail) {
        this.userEmail = userEmail;

        this.newHourBeforeSendBirthdayEmail = newHourBeforeSendBirthdayEmail;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        User user = database.getUserByUserEmail().get(userEmail);
        user.setHourBeforeSendBirthdayEmail(newHourBeforeSendBirthdayEmail);
        database.getUserByUserEmail().put(userEmail, user);
    }
}
