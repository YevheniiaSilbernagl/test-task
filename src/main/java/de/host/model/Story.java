package de.host.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Optional;

/**
 * Created by yevheniia on 04.08.17
 */
public class Story {

    static {
        String path = Resources.getAncestorResource("chromedriver_mac").toAbsolutePath().toString();
        Assert.assertTrue("Path to a chromedriver_mac is not correct", new File(path).exists());
        System.setProperty("webdriver.chrome.driver", path);
    }

    protected static WebDriver driver;

    @BeforeClass
    public static void init() {
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void tearDown() {
        Optional.ofNullable(driver).map(dr -> {
            try {
                driver.close();
            } catch (Exception e) {/**/}
            return null;
        });
    }
}
