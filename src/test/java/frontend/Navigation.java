package frontend;

import de.host.model.User;
import de.host.stories.CrmStory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static de.host.Config.env;

/**
 * Created by yevheniia on 04.08.17
 */
public class Navigation extends CrmStory {
    @Before
    public void deleteAll() {
        api.deleteAllUsers();
        Arrays.asList(User.random(), User.random()).forEach(user -> {
            api.createUser(user);
            Assert.assertTrue("User " + user + " was not created", api.findUser(user.getEmail()).isPresent());
        });
    }

    @Test
    public void navigate() {
        landingPage.open(env);
        landingPage.openAllUsers();
        allUsersPage.createNewUser();
        landingPage.formFillIn(User.random());
        landingPage.openAllUsers();
        Assert.assertTrue("Data loss dialogue is not shown",
                landingPage.confirmationDialogueShown());
    }
}
