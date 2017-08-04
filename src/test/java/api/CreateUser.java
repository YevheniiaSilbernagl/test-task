package api;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;
import de.host.model.User;
import de.host.stories.CrmApi;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.jayway.restassured.RestAssured.given;
import static de.host.Config.env;

/**
 * Created by yevheniia on 04.08.17
 */
@RunWith(Parameterized.class)
public final class CreateUser {
    private static CrmApi api = new CrmApi(env);

    @Parameterized.Parameter
    public ResponseSpecification specification;

    @Parameterized.Parameter(1)
    public User sourceUser;

    @Parameterized.Parameter(2)
    public Optional<User> expectedUser;


    private static Object[] convert(User user, Integer statusCode) {
        return new Object[]{given().params(user.toRequestParams())
                .expect().statusCode(statusCode),
                user, Optional.of(user)
                .filter(u -> u.getEmail() != null)
                .filter(u -> u.getName() != null)
                .filter(u -> u.getPassword() != null)
                .filter(u -> u.getConfirmationPassword() != null)
        };
    }

    @BeforeClass
    public static void clearUsers() {
        api.deleteAllUsers();
    }

    @Parameterized.Parameters(name = "{1}")
    public static Collection data() {
        return new ArrayList<Object[]>() {{
            User validUser = new User("ValidName5", "valid-email5@test.com", "validPass", "validPass");
            add(convert(new User("ValidName", "valid-email@test.com", "validPass", "validPass"), 302));
            add(convert(new User("NameWith`SpecialCharacter", "new-valid-email@test.com", "validPass", "validPass"), 302));
            add(convert(new User(null, "new-valid-email1@test.com", "validPass", "validPass"), 400));
            add(convert(new User("ValidName1", null, "validPass", "validPass"), 400));
            add(convert(new User("ValidName2", "new-valid-email2@test.com", null, "validPass"), 400));
            add(convert(new User("ValidName3", "new-valid-email3@test.com", "validPass", null), 400));
            add(convert(new User("ValidName4", "new-valid-email4@test.com", null, null), 400));
            add(convert(new User("ValidName6", "new-valid-email6@test.com", "validPass", "invalidConfirmation"), 400));
            add(new Object[]{given().expect().statusCode(400), new User(), Optional.empty()});
            add(new Object[]{given().params(validUser.toRequestParams()).param("unknownParameter", "something").expect().statusCode(302), validUser, Optional.of(validUser)});
        }};
    }

    @Test
    public void test() {
        Response response = specification.post(env.saveUser());
        expectedUser.map(user -> {
            Assert.assertEquals(env.allUsers(), response.getHeader("Location"));
            Assert.assertEquals(user, api.findUser(user.getEmail()).orElseThrow(() ->
                    new AssertionError("User " + user + " was not created")));
            return true;
        });
    }
}
