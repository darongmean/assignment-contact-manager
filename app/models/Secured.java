package models;

import controllers.routes;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Optional;

public class Secured extends Security.Authenticator {
    @Override
    public Optional<String> getUsername(Http.Request req) {
        return req.session().getOptional("username");
    }

    @Override
    public Result onUnauthorized(Http.Request req) {
        return redirect(routes.ContactListController.getIndex());
    }
}
