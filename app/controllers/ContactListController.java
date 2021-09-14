package controllers;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
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

    public Result getMe() {
        return ok(views.html.me.render(
                database.whoAmI(),
                database.findContactByUserEmail("user1@example.com")));
    }

    public Result getIndex() {
        return ok(views.html.index.render());
    }

    public Result postContactList(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        Action action = notifyBirthday.update(
                database.whoAmI(),
                database.findContactByUserEmail("user1@example.com"),
                LocalDateTime.now(),
                requestData.get("hourBeforeSendBirthdayEmail"));
        action.execute(database, mailServer);
        return redirect(routes.ContactListController.getMe());
    }

    public Result postLogin() {
        return redirect(routes.ContactListController.getMe());
    }

}
