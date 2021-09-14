package controllers;

import models.Contact;
import models.Secured;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.*;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class ContactListController extends Controller {
    private final MailServer mailServer;
    private final Database database;
    private final FormFactory formFactory;
    private final NotifyBirthday notifyBirthday;
    private final ManageContact manageContact;


    @Inject
    public ContactListController(MailServer mailServer, Database database, FormFactory formFactory, NotifyBirthday notifyBirthday, ManageContact manageContact) {
        this.mailServer = mailServer;
        this.database = database;
        this.formFactory = formFactory;
        this.notifyBirthday = notifyBirthday;
        this.manageContact = manageContact;
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
    public Result postBirthday(Http.Request request) {
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

    @Security.Authenticated(Secured.class)
    public Result getContact(Http.Request request) {
        return ok(views.html.contact.render());
    }

    @Security.Authenticated(Secured.class)
    public Result getContactEdit(Http.Request request, String contactId) {
        User user = getAuthenticatedUser(request);
        Contact contact = database.getContact(user.getEmail(), contactId);
        if (contact == null) {
            return redirect(routes.ContactListController.getMe());
        }
        return ok(views.html.contactEdit.render(contact));
    }

    private User getAuthenticatedUser(Http.Request request) {
        return database.getUserByEmail(request.session().get("username").get());
    }

    @Security.Authenticated(Secured.class)
    public Result postContact(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        User user = getAuthenticatedUser(request);
        Action action = manageContact.addContact(user,
                requestData.get("name"),
                requestData.get("birthday"));
        action.execute(database, mailServer);
        return redirect(routes.ContactListController.getMe());
    }

    @Security.Authenticated(Secured.class)
    public Result postContactEdit(Http.Request request, String contactId) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        User user = getAuthenticatedUser(request);
        Action action = manageContact.editContact(user,
                contactId,
                requestData.get("name"),
                requestData.get("birthday"));
        action.execute(database, mailServer);
        return redirect(routes.ContactListController.getMe());
    }

    @Security.Authenticated(Secured.class)
    public Result postContactDelete(Http.Request request, String contactId) {
        User user = getAuthenticatedUser(request);
        Action action = manageContact.deleteContact(user, contactId);
        action.execute(database, mailServer);
        return redirect(routes.ContactListController.getMe());
    }

    public Result postLogin(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        User user = database.getUserByEmail(requestData.get("email"));
        if (null == user || isIncorrectPassword(requestData, user)) {
            return redirect(routes.ContactListController.getMe());
        }
        return redirect(routes.ContactListController.getMe())
                .withNewSession()
                .addingToSession(request, "username", requestData.get("email"));
    }

    private boolean isIncorrectPassword(DynamicForm requestData, User user) {
        return !user.getPassword().equalsIgnoreCase(requestData.get("password"));
    }

    public Result postLogout() {
        return redirect(routes.ContactListController.getIndex()).withNewSession();
    }

}
