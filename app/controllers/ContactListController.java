package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import services.Database;
import services.MailServer;

import javax.inject.Inject;

public class ContactListController extends Controller {
    private final MailServer mailServer;
    private final Database database;

    @Inject
    public ContactListController(MailServer mailServer, Database database) {
        this.mailServer = mailServer;
        this.database = database;
    }

    public Result getMe() {
        return ok(views.html.me.render(database.findContactByUserEmail("user1@example.com")));
    }

    public Result getIndex() {
        return ok(views.html.index.render());
    }

    public Result postContactList() {
        mailServer.sendEmail();
        return redirect(routes.ContactListController.getMe());
    }

    public Result postLogin() {
        return redirect(routes.ContactListController.getMe());
    }

}
