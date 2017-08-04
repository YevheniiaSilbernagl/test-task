package de.host.stories;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.host.model.Env;
import de.host.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by yevheniia on 04.08.17
 */
public class CrmApi {
    private Env env;

    public CrmApi(Env env) {
        this.env = env;
    }

    public void deleteAllUsers() {
        given().expect().statusCode(200).delete(env.deleteAll());
    }

    public Optional<User> findUser(String email) {
        return getUsers().stream().filter(u -> u.getEmail().equals(email)).findAny();
    }

    private static User convert(JsonObject json) {
        return new User(
                json.get("name").getAsString(),
                json.get("email").getAsString(),
                json.get("password").getAsString())
                .withId(json.get("id").getAsInt());
    }

    public List<User> getUsers() {
        String allUsers = given().expect().statusCode(200).get(env.allUsersJson()).getBody().asString();
        return Lists.newArrayList(new JsonParser().parse(allUsers).getAsJsonArray().iterator()).stream()
                .map(JsonElement::getAsJsonObject).map(CrmApi::convert).filter(u -> u.getName() != null)
                .collect(Collectors.toList());
    }

    public void createUser(User user) {
        given().params(user.toRequestParams()).expect().statusCode(302).post(env.saveUser());
    }
}
