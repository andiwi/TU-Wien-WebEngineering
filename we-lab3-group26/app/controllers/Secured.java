package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.cache.Cache;

import models.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        String ret = Cache.get("user") == null ? null : "";
        return ret;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.notlogedin());
    }
}