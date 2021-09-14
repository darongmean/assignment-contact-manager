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

    public Result getMe() {
        return ok(views.html.me.render());
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
