package frontend;

import de.host.model.User;
import de.host.stories.CrmStory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static de.host.Config.env;

/**
 * Created by yevheniia on 04.08.17
 */
@RunWith(Parameterized.class)
public final class ValidateCreateUserForm extends CrmStory {
    @Parameterized.Parameter
    public User user;

    @Parameterized.Parameter(1)
    public String description;

    @Parameterized.Parameter(2)
    public Map<String, String> expectedValidationMessages;

    @Parameterized.Parameters(name = "{1}")
    public static Collection data() {
        return new ArrayList<Object[]>() {{
            add(new Object[]{
                    new User(null, null, null, null),
                    "Missing all fields", new HashMap<String, String>() {{
                put("user.name.error", "Name is required");
                put("user.email.error", "Email is required");
                put("user.password.error", "Password is required");
            }}});
            add(new Object[]{
                    new User(null, null, "testPass", "testPass"),
                    "Missing name and email", new HashMap<String, String>() {{
                put("user.name.error", "Name is required");
                put("user.email.error", "Email is required");
            }}});
            add(new Object[]{
                    new User(null, "validation_test@test.com", "testPass", "testPass"),
                    "Missing name", new HashMap<String, String>() {{
                put("user.name.error", "Name is required");
            }}});
            add(new Object[]{new User("testName1", null, "testPass", "testPass"),
                    "Missing email", new HashMap<String, String>() {{
                put("user.email.error", "Email is required");
            }}});
            add(new Object[]{new User("testName2", "validation_1@test.com", null, "testPass"),
                    "Missing password with non missing confirmation", new HashMap<String, String>() {{
                put("user.password.error", "Password is required");
            }}});
            add(new Object[]{new User("testName3", "validation_2@test.com", null, null),
                    "Missing password", new HashMap<String, String>() {{
                put("user.password.error", "Password is required");
            }}});
            add(new Object[]{new User("testName4", "validation_3@test.com", "testPass", null),
                    "Missing password confirmation", new HashMap<String, String>() {{
                put("user.confirmationPassword.error", "Passwords are not the same");
            }}});
        }};
    }

    @Test
    public void validate() {
        landingPage.open(env);
        landingPage.formFillIn(user);
        landingPage.submitNewUser();
        Assert.assertEquals("Unexpected validation messages",
                expectedValidationMessages, landingPage.getValidationMessages());
    }
}
