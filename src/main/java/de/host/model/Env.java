package de.host.model;

/**
 * Created by yevheniia on 04.08.17
 */
public enum Env {
    TEST("http://85.93.17.135:9000");
    private String url;

    Env(String url) {
        this.url = url;
    }

    public String resolve(String resource) {
        return url + resource;
    }

    public String landingPage() {
        return resolve("/user/new");
    }

    public String allUsers() {
        return resolve("/users/all");
    }

    public String allUsersJson() {
        return resolve("/user/all/json");//should be consistent users
    }

    public String deleteAll() {
        return resolve("/user/all");//should be consistent users
    }

    public String saveUser() {
        return resolve("/user/save");
    }
}
