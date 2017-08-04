package frontend;

import de.host.model.User;
import de.host.stories.CrmStory;
import org.junit.Assert;
import org.junit.Test;

import static de.host.Config.env;

/**
 * Created by yevheniia on 04.08.17
 */
public class RegisterNewUser extends CrmStory {
    @Test
    public void validate() {
        User user = User.random();
        landingPage.open(env);
        landingPage.formFillIn(user);
        landingPage.submitNewUser();
        Assert.assertTrue("There is no user " + user + " on a page " +
                allUsersPage.currentUrl(), allUsersPage.contains(user));
    }
}
