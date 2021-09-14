package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import services.MailServer;

import javax.inject.Inject;

public class ContactListController extends Controller {
    private final MailServer mailServer;

    @Inject
    public ContactListController(MailServer mailServer) {
        this.mailServer = mailServer;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result login() {
        return redirect(routes.ContactListController.me());
    }

    public Result contactList() {
        mailServer.sendEmail();
        return redirect(routes.ContactListController.me());
    }

    public Result me() {
        return ok(views.html.me.render());
    }

}
