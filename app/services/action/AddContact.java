package services.action;

import services.Action;
import services.Database;
import services.MailServer;

import java.time.LocalDate;

public class AddContact implements Action {
    private String userEmail;
    private String name;
    private LocalDate birthday;

    public AddContact(String userEmail, String name, LocalDate birthday) {

        this.userEmail = userEmail;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public void execute(Database database, MailServer mailServer) {
        database.addContact(userEmail, name, birthday);
    }
}
