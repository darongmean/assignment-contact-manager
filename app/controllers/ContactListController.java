package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ContactListController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result login() {
        return redirect(routes.ContactListController.me());
    }

    public Result me() {
        return ok(views.html.me.render());
    }

}
