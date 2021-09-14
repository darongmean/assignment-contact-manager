package controllers;

import models.Secured;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.Action;
import services.Database;
import services.MailServer;
import services.NotifyBirthday;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class ContactListController extends Controller {
    private final MailServer mailServer;
    private final Database database;
    private final FormFactory formFactory;
    private final NotifyBirthday notifyBirthday;


    @Inject
    public ContactListController(MailServer mailServer, Database database, FormFactory formFactory, NotifyBirthday notifyBirthday) {
        this.mailServer = mailServer;
        this.database = database;
        this.formFactory = formFactory;
        this.notifyBirthday = notifyBirthday;
    }

    @Security.Authenticated(Secured.class)
    public Result getMe(Http.Request request) {
        String authenticatedEmail = request.session().get("username").get();
        return ok(views.html.me.render(
                database.getUserByEmail(authenticatedEmail),
                database.findContactByUserEmail(authenticatedEmail)));
    }

    public Result getIndex() {
        return ok(views.html.index.render());
    }

    @Security.Authenticated(Secured.class)
    public Result postContactList(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        String authenticatedEmail = request.session().get("username").get();
        Action action = notifyBirthday.update(
                database.getUserByEmail(authenticatedEmail),
                database.findContactByUserEmail(authenticatedEmail),
                LocalDateTime.now(),
                requestData.get("hourBeforeSendBirthdayEmail"));
        action.execute(database, mailServer);
        return redirect(routes.ContactListController.getMe());
    }

    public Result postLogin(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        if (null == database.getUserByEmail(requestData.get("email"))) {
            return redirect(routes.ContactListController.getMe());
        }
        return redirect(routes.ContactListController.getMe())
                .withNewSession()
                .addingToSession(request, "username", requestData.get("email"));
    }

    public Result postLogout() {
        return redirect(routes.ContactListController.getIndex()).withNewSession();
    }

}
