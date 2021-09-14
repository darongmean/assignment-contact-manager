package services.action;

import services.Action;
import services.Database;
import services.MailServer;

public class NoAction implements Action {
    @Override
    public void execute(Database database, MailServer mailServer) {
        // Do nothing
    }
}
