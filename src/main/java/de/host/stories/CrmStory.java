package de.host.stories;

import de.host.model.Story;
import de.host.pages.AllUsersPage;
import de.host.pages.LandingPage;
import org.junit.BeforeClass;
import org.openqa.selenium.support.PageFactory;

import static de.host.Config.env;

/**
 * Created by yevheniia on 04.08.17
 */
public class CrmStory extends Story {
    protected static CrmApi api = new CrmApi(env);
    protected LandingPage landingPage;
    protected AllUsersPage allUsersPage;

    public CrmStory() {
        super();
        this.landingPage = PageFactory.initElements(driver, LandingPage.class);
        this.allUsersPage = PageFactory.initElements(driver, AllUsersPage.class);
    }

    @BeforeClass
    public static void clearUsers() {
        api.deleteAllUsers();
        /*
         Not a good practice, because if you run classes in parallel
         this will make tests failing.
         The best is usually to delete them before build
        */
    }

}
