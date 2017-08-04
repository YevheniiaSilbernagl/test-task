package api;

import de.host.model.User;
import de.host.stories.CrmApi;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static de.host.Config.env;

/**
 * Created by yevheniia on 04.08.17
 */
public class DeleteAllUsers {
    private static CrmApi api = new CrmApi(env);

    @Before
    public void deleteAll() {
        api.deleteAllUsers();
        Arrays.asList(User.random(), User.random()).forEach(user -> {
            api.createUser(user);
            Assert.assertTrue("User " + user + " was not created", api.findUser(user.getEmail()).isPresent());
        });
    }

    @Test
    public void delete() {
        api.deleteAllUsers();
        Assert.assertTrue(api.getUsers().isEmpty());
    }
}
